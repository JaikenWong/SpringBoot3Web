package com.jaiken.springweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaiken.springweb.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Created WangJingshen
 * @Date 2025/8/25 15:30
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
}
