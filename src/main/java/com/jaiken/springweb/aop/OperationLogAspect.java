package com.jaiken.springweb.aop;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jaiken.springweb.annotation.OperationLog;
import com.jaiken.springweb.entity.OperationLogEntity;
import com.jaiken.springweb.mapper.OperationLogMapper;
import com.jaiken.springweb.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面处理类
 */
@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    
    @Autowired
    private OperationLogMapper operationLogMapper;
    
    /**
     * 环绕通知处理操作日志
     * 
     * @param joinPoint 切点
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("@annotation(com.jaiken.springweb.annotation.OperationLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 创建操作日志对象
        OperationLogEntity operationLog = new OperationLogEntity();
        
        // 获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLogAnnotation = method.getAnnotation(OperationLog.class);
        
        // 设置基本信息
        operationLog.setModule(operationLogAnnotation.module());
        operationLog.setOperation(operationLogAnnotation.operation());
        operationLog.setDescription(operationLogAnnotation.description());
        operationLog.setOperationTime(LocalDateTime.now());
        
        // 获取请求相关信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        operationLog.setRequestUrl(request.getRequestURL().toString());
        operationLog.setRequestMethod(request.getMethod());
        operationLog.setRequestIp(getClientIpAddress(request));

        // 获取请求参数
        String params = Arrays.toString(joinPoint.getArgs());
        if (params.length() > 500) {
            params = params.substring(0, 500) + "...";
        }
        operationLog.setRequestParams(params);

        // 获取操作用户
        operationLog.setOperator(getCurrentUsername());
        
        Object result;
        long startTime = System.currentTimeMillis();
        
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            
            // 记录执行成功
            operationLog.setResultStatus(1);
            return result;
        } catch (Exception e) {
            // 记录执行失败
            operationLog.setResultStatus(0);
            operationLog.setErrorMessage(e.getMessage());
            throw e;
        } finally {
            // 计算执行时间
            long endTime = System.currentTimeMillis();
            operationLog.setExecutionTime(endTime - startTime);
            
            // 保存操作日志
            try {
                operationLogMapper.insert(operationLog);
            } catch (Exception e) {
                log.error("保存操作日志失败", e);
            }
            
            // 打印操作日志到控制台
            log.info("操作日志: 模块={}, 操作={}, 说明={}, 用户={}, 耗时={}ms, 结果={}",
                    operationLog.getModule(),
                    operationLog.getOperation(),
                    operationLog.getDescription(),
                    operationLog.getOperator(),
                    operationLog.getExecutionTime(),
                    operationLog.getResultStatus() == 1 ? "成功" : "失败");
        }
    }
    
    /**
     * 获取客户端IP地址
     * 
     * @param request HttpServletRequest对象
     * @return 客户端IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 获取当前操作用户
     * 
     * @return 当前用户名
     */
    private String getCurrentUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (ObjectUtil.isNotEmpty(authentication) && authentication.isAuthenticated()) {
                return authentication.getName();
            }
        } catch (Exception e) {
            log.error("获取当前用户失败", e);
        }
        return "anonymous";
    }
}