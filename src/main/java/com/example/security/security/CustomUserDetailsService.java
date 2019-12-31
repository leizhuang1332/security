package com.example.security.security;

import com.example.security.dao.TbRoleDao;
import com.example.security.dao.TbUserDao;
import com.example.security.entity.TbRole;
import com.example.security.entity.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private TbUserDao userRepository;
    @Autowired
    private TbRoleDao roleRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // 1. 查询用户
        TbUser userFromDatabase = userRepository.findByUsername(login);
        if (userFromDatabase == null) {
            //log.warn("User: {} not found", login);
            //这里找不到必须抛异常
            throw new UsernameNotFoundException("User " + login + " was not found in db");
        } else {
            List<TbRole> tbRoles = roleRepository.findRoleByUserId(userFromDatabase.getId());
            // 2. 设置角色
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            tbRoles.forEach(tbRole -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbRole.getEnname());
                grantedAuthorities.add(grantedAuthority);
            });

            return new org.springframework.security.core.userdetails.User(login,
                    userFromDatabase.getPassword(), grantedAuthorities);
        }
    }
}