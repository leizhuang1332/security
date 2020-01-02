package com.example.security.security;

import com.alibaba.fastjson.JSON;
import com.example.security.dao.TbTokenDao;
import com.example.security.entity.TbToken;
import com.example.security.util.Result;
import com.example.security.util.SecuritySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MySecurityInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TbTokenDao tbTokenDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");
       String username;
        String token = request.getParameter("token");
        // 用户未登录，则返回false, 拦截该请求
        if (token == null) {
            response.getWriter().write(JSON.toJSONString(new Result(HttpStatus.UNAUTHORIZED.value(), "请前往登录")));
            return false;
        }else{
//             查token表获取token进行比对
            TbToken tbToken = tbTokenDao.selectOne(new TbToken(token));
            if(tbToken == null){
                response.getWriter().write(JSON.toJSONString(new Result(HttpStatus.UNAUTHORIZED.value(), "请前往登录")));
                return false;
            }
            username = tbToken.getUsername();
        }
        // 校验权限
        AtomicBoolean hasAuth = new AtomicBoolean(false);
        String requestURI = request.getRequestURI();
        Map<String, List<String>> rolePermissonMap = SecuritySource.rolePermissonMap;
        rolePermissonMap.forEach((k, v) -> {
            if(requestURI.contains(k)){
                v.forEach(role -> {
                    // 通过token拿到用户名
                    if(SecuritySource.userRoleMap.get(username).contains(role)){
                        hasAuth.set(true);
                        return;
                    }
                });
            }
        });
        if(!hasAuth.get()){
            response.getWriter().write(JSON.toJSONString(new Result(HttpStatus.UNAUTHORIZED.value(), "权限不足请联系管理员")));
            return false;
        }else {
            return true;
        }
    }
}
