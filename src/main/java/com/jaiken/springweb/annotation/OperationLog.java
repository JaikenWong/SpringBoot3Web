package com.jaiken.springweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 */
@Target(ElementType.METHOD) // 注解作用于方法级别
@Retention(RetentionPolicy.RUNTIME) // 运行时保留注解
public @interface OperationLog {
    
    /**
     * 操作模块
     */
    String module() default "";
    
    /**
     * 操作类型
     */
    String operation() default "";
    
    /**
     * 操作说明
     */
    String description() default "";
}