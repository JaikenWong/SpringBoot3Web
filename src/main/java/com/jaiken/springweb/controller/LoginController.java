package com.jaiken.springweb.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaiken.springweb.annotation.OperationLog;
import com.jaiken.springweb.dto.LoginRequest;
import com.jaiken.springweb.dto.Result;
import com.jaiken.springweb.dto.UserDto;
import com.jaiken.springweb.util.TokenUtil;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/login")
    @OperationLog(module = "用户管理", operation = "用户登录", description = "用户登录系统")
    public Result login(@RequestBody LoginRequest loginRequest) {
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            // 执行认证
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 认证成功后生成JWT token
            UserDto principal = (UserDto)authentication.getPrincipal();
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", principal.getId());
            payload.put("username", principal.getUsername());
            payload.put("login_time", LocalDateTime.now());
            String token = tokenUtil.createToken(payload);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userInfo", principal);
            // 返回成功响应
            return Result.succ("登录成功", response);
        } catch (Exception e) {
            log.info("Authentication failed for user: {}", loginRequest.getUsername(), e);
            // 认证失败
            return Result.fail(e.getLocalizedMessage());
        }
    }

    @PostMapping("/logout")
    @OperationLog(module = "用户管理", operation = "用户登出", description = "用户退出系统")
    public Result logout(HttpServletRequest request) {
        // 使用工具类从标准 Authorization 头部获取 token
        tokenUtil.removeToken(tokenUtil.getToken(request));
        // 清除安全上下文
        SecurityContextHolder.clearContext();
        return Result.succ("登出成功");
    }

    @PostMapping("/refresh")
    @OperationLog(module = "用户管理", operation = "Token续期", description = "用户Token续期")
    public Result refresh(HttpServletRequest request) {
        tokenUtil.removeToken(tokenUtil.getToken(request));
        return Result.succ("token续期成功");
    }
}