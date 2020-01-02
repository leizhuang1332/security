package com.example.security.util;

import com.example.security.dao.TbRolePermissionDao;
import com.example.security.entity.TbRole;
import com.example.security.entity.TbRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SecuritySource implements CommandLineRunner {

    @Autowired
    private TbRolePermissionDao tbRolePermissionDao;

    public static Map<String, List<TbRole>> userRoleMap = new HashMap<>();

    public static Map<String, List<String>> rolePermissonMap = new HashMap<>();

    public void initRolePermisson(){
        //权限资源 和 角色对应的表  也就是 角色权限 中间表
        List<TbRolePermission> rolePermissons = tbRolePermissionDao.getRolePermissions();
        for (TbRolePermission rolePermisson : rolePermissons) {
            String url = rolePermisson.getPermissionUrl();
            String roleName = rolePermisson.getRoleEnname();
            if (rolePermissonMap.containsKey(url)) {
                rolePermissonMap.get(url).add(roleName);
            } else {
                List<String> list = new ArrayList<>();
                list.add(roleName);
                rolePermissonMap.put(url, list);
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initRolePermisson();
    }
}
