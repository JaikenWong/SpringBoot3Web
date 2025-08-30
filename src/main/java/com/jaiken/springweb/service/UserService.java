package com.jaiken.springweb.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jaiken.springweb.entity.User;
import com.jaiken.springweb.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author JaikenWong
 * @since 2025-08-30 17:58
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
