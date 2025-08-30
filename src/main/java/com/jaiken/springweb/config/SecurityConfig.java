package com.jaiken.springweb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService customerDetailsService, JwtTokenFilter jwtTokenFilter) throws Exception {
        http.authorizeHttpRequests(authz ->
                        authz.requestMatchers("/api/public/**", "/css/**", "/js/**", "/images/**", "/favicon.ico")
                                .permitAll()
                                .requestMatchers("/api/login")  // 登录接口允许所有人访问
                                .permitAll()
                                .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)  // 禁用表单登录
                .httpBasic(AbstractHttpConfigurer::disable) // 禁用HTTP Basic认证
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 使用无状态会话
                .userDetailsService(customerDetailsService)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable); // 禁用CSRF保护
        return http.build();
    }
}