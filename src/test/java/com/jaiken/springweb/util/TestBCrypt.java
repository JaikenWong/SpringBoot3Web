package com.jaiken.springweb.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description
 * @Created WangJingshen
 * @Date 2025/8/25 15:03
 */
public class TestBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String encodedPassword = "$2a$10$ScImjwXCVU7A4mHLEcxCAO5xDftsJFMLncO0jmcbGOXniwu5NSVhe";
        String encodedPassword2 = encoder.encode(rawPassword);
        System.out.println("Encoded Password: " + encodedPassword2);
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("密码匹配结果: " + matches);
    }
}
