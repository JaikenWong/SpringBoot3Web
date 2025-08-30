package com.jaiken.springweb.controller;

import java.time.LocalDateTime;

import com.jaiken.springweb.annotation.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaiken.springweb.dto.Result;
import com.jaiken.springweb.entity.User;
import com.jaiken.springweb.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户请求处理
 *
 * @author JaikenWong
 * @since 2025-08-30 10:50
 **/
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/save")
    @OperationLog(module = "用户管理", operation = "创建用户", description = "创建用户")
    public Result testUser(@Validated @RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        // 清空返回体中的密码
        user.setPassword(null);
        return Result.succ("创建成功", user);
    }
}
