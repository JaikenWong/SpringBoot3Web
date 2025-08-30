package com.jaiken.springweb.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jaiken.springweb.entity.Blog;
import com.jaiken.springweb.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
 * @author JaikenWong
 * @since 2025-08-30 13:45
 **/
@Service
public class BlogService extends ServiceImpl<BlogMapper, Blog> {
}
