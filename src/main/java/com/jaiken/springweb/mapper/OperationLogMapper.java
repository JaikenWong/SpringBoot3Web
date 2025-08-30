package com.jaiken.springweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaiken.springweb.entity.OperationLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志Mapper接口
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLogEntity> {
}