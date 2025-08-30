package com.jaiken.springweb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志实体类
 */
@Data
@TableName("operation_log")
public class OperationLogEntity {
    
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 操作模块
     */
    private String module;
    
    /**
     * 操作类型
     */
    private String operation;
    
    /**
     * 操作说明
     */
    private String description;
    
    /**
     * 请求URL
     */
    private String requestUrl;
    
    /**
     * 请求方法
     */
    private String requestMethod;
    
    /**
     * 请求参数
     */
    private String requestParams;
    
    /**
     * 请求IP
     */
    private String requestIp;
    
    /**
     * 操作用户
     */
    private String operator;
    
    /**
     * 操作时间
     */
    private LocalDateTime operationTime;
    
    /**
     * 执行时长（毫秒）
     */
    private Long executionTime;
    
    /**
     * 操作结果（0：失败，1：成功）
     */
    private Integer resultStatus;
    
    /**
     * 错误信息
     */
    private String errorMessage;
}