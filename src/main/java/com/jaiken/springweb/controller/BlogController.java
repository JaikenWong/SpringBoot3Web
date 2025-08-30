package com.jaiken.springweb.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jaiken.springweb.annotation.OperationLog;
import com.jaiken.springweb.dto.Result;
import com.jaiken.springweb.entity.Blog;
import com.jaiken.springweb.service.BlogService;
import com.jaiken.springweb.util.TokenUtil;

import cn.hutool.core.bean.BeanUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2025-08-30 13:44
 **/
@Slf4j
@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping("/")
    @OperationLog(module = "博客管理", operation = "获取博客列表", description = "获取博客列表")
    public Result blogs(Integer currentPage) {
        if (currentPage == null || currentPage < 1)
            currentPage = 1;
        Page<Blog> page = new Page<>(currentPage, 5);
        IPage<Blog> pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    @GetMapping("/{id}")
    @OperationLog(module = "博客管理", operation = "获取博客详情", description = "获取博客详情")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        return Result.succ(blog);
    }

    @PostMapping("/edit")
    @OperationLog(module = "博客管理", operation = "编辑博客", description = "编辑博客")
    public Result edit(@Validated @RequestBody Blog blog, HttpServletRequest request) {
        Blog temp;
        Long userId = tokenUtil.getUserId(request);
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            if (temp == null || !temp.getUserId().equals(userId)) {
                return Result.fail("不存在该博客");
            }
        } else {
            temp = new Blog();
            temp.setUserId(tokenUtil.getUserId(request));
            temp.setCreated(LocalDateTime.now());
            temp.setStatus((byte)0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ("操作成功");
    }
}
