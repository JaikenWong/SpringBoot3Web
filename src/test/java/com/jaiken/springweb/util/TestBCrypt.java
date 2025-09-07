package com.jaiken.springweb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description
 * @Created WangJingshen
 * @Date 2025/8/25 15:03
 */
@Slf4j
public class TestBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String encodedPassword = "$2a$10$ScImjwXCVU7A4mHLEcxCAO5xDftsJFMLncO0jmcbGOXniwu5NSVhe";
        String encodedPassword2 = encoder.encode(rawPassword);
        System.out.println("Encoded Password: " + encodedPassword2);
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("密码匹配结果: " + matches);
        List<Map<String,List<String>>> data1 = new ArrayList<>();
        data1.add(Map.of("password", List.of(encodedPassword)));
        List<Map<String,List<PasswordEncoder>>> data2 = new ArrayList<>();
        data2.add(Map.of("password", List.of(encoder)));
    }
}
