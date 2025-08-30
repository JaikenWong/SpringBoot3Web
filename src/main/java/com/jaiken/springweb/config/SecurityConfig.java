package com.jaiken.springweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz.requestMatchers("/api/public/**", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll().requestMatchers("/api/login", "/login").permitAll() // 允许访问登录接口
                        .anyRequest().authenticated()).formLogin(AbstractHttpConfigurer::disable) // 禁用默认的表单登录
                .httpBasic(AbstractHttpConfigurer::disable) // 禁用HTTP Basic认证
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 使用无状态会话
                .csrf(AbstractHttpConfigurer::disable); // 禁用CSRF保护
        return http.build();
    }
}