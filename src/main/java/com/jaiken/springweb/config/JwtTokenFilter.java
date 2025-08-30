package com.jaiken.springweb.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jaiken.springweb.dto.UserDto;
import com.jaiken.springweb.util.TokenUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2025-08-30 11:28
 **/
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret:0123456789}")
    private String secret;

    @Resource
    private UserDetailsService customUserDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // 从标准 Authorization 头部获取 token
        String token = tokenUtil.getToken(request);

        if (StrUtil.isNotEmpty(token)) {
            // 检查token是否在黑名单中（已登出）
            if (!tokenUtil.validateToken(token)) {
                writeErrorResponse(response, 401, "Token无效");
                return;
            }

            boolean verify = JWTUtil.verify(token, secret.getBytes());
            if (!verify) {
                writeErrorResponse(response, 401, "Token非法");
                return;
            } else {
                try {
                    // 认证成功，设置用户信息
                    UserDto user = JWTUtil.parseToken(token).getPayloads().toBean(UserDto.class);
                    // 模拟获取用户信息，实际情况应该是从数据库查询
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    // 设置用户信息
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception e) {
                    log.error("Token parse error", e);
                    writeErrorResponse(response, 401, "Token非法");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":" + code + ",\"msg\":\"" + message + "\"}");
    }
}