package com.jaiken.springweb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jaiken.springweb.entity.Role;
import com.jaiken.springweb.entity.User;
import com.jaiken.springweb.mapper.RoleMapper;
import com.jaiken.springweb.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        // 查询用户基本信息
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        // 查询用户角色
        List<Role> roles = roleMapper.selectRoleByUserName(username);
        // 转换角色为权限
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> {
                    return new SimpleGrantedAuthority(role.getName());
                })
                .collect(Collectors.toList());
        // 创建UserDetails对象
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}