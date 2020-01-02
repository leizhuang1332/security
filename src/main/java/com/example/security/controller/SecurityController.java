package com.example.security.controller;

import com.example.security.controller.base.BaseController;
import com.example.security.dao.TbRoleDao;
import com.example.security.dao.TbTokenDao;
import com.example.security.dao.TbUserDao;
import com.example.security.entity.TbRole;
import com.example.security.entity.TbToken;
import com.example.security.entity.TbUser;
import com.example.security.util.SecuritySource;
import com.example.security.util.TokenProccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SecurityController extends BaseController {
    @Autowired
    private TbUserDao userRepository;
    @Autowired
    private TbRoleDao roleRepository;
    @Autowired
    private TbTokenDao tbTokenDao;

    @GetMapping("login")
    public Object login(String username, String password) {
        // 1. 查询用户
        TbUser userFromDatabase = userRepository.findByUsername(username);
        if(userFromDatabase == null){
            return failure("账号不存在");
        }else if(!userFromDatabase.getPassword().equals(password)){
            return failure("密码错误");
        }
        List<TbRole> tbRoles = roleRepository.findRoleByUserId(userFromDatabase.getId());
        SecuritySource.userRoleMap.put(username,tbRoles);

        TbToken tbToken = new TbToken();
        tbToken.setUsername(username);
        TbToken tableToken = tbTokenDao.selectOne(tbToken);
        if(tableToken != null){
            return success(tableToken.getToken());
        }
        // 生成token;
        String token = TokenProccessor.getInstance().makeToken();
        // 插入token表
        tbTokenDao.insert(new TbToken(username, token));
        return success(token);
    }

    @GetMapping("logout")
    public Object logout(String token){
        tbTokenDao.update(new TbToken(token, 1));
        return success();
    }
}
