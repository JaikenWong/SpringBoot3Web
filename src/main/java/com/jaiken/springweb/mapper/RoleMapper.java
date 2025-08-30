package com.jaiken.springweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaiken.springweb.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
    @Select("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id JOIN users u ON ur.user_id = u.id WHERE u.username = #{username}")
    List<Role> selectRoleByUserName(String username);
}