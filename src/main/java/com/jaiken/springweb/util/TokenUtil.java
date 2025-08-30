package com.jaiken.springweb.util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2025-08-30 12:30
 **/
@Slf4j
@Component
public class TokenUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.expiration}")
    private Long expire;

    @Value("${jwt.secret}")
    private String secret;

    public void saveToken(String token, Object userInfo) {
        stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userInfo));
        stringRedisTemplate.expire(token, expire, TimeUnit.SECONDS);
    }

    public boolean validateToken(String token) {
        return stringRedisTemplate.hasKey(token);
    }

    public void removeToken(String token) {
        stringRedisTemplate.delete(token);
    }

    public boolean isTokenExpired(String token) {
        Long expire = stringRedisTemplate.getExpire(token);
        return expire < 0;
    }

    public Long getUserId(HttpServletRequest request) {
        String token = getToken(request);
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        return getUserId(token);
    }

    public Long getUserId(String token) {
        String userInfo = stringRedisTemplate.opsForValue().get(token);
        String userId = JSONUtil.parseObj(userInfo).getStr("id");
        return Long.parseLong(userId);
    }

    public String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return StrUtil.isEmpty(header) ? null : header.replace("Bearer ", "");
    }

    public String createToken(Map<String, Object> payload) {
        String token = JWTUtil.createToken(payload, secret.getBytes());
        saveToken(token, payload);
        return token;
    }
}