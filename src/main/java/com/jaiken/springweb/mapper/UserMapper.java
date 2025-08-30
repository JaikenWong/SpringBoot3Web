package com.jaiken.springweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaiken.springweb.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}