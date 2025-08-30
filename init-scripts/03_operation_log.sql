-- 创建操作日志表
CREATE TABLE IF NOT EXISTS operation_log
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    module          VARCHAR(50)   NOT NULL COMMENT '操作模块',
    operation       VARCHAR(50)   NOT NULL COMMENT '操作类型',
    description     VARCHAR(255)  NOT NULL COMMENT '操作说明',
    request_url     VARCHAR(255)  NOT NULL COMMENT '请求URL',
    request_method  VARCHAR(10)   NOT NULL COMMENT '请求方法',
    request_params  TEXT          NULL COMMENT '请求参数',
    request_ip      VARCHAR(50)   NOT NULL COMMENT '请求IP',
    operator        VARCHAR(50)   NOT NULL COMMENT '操作用户',
    operation_time  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    execution_time  BIGINT        NOT NULL COMMENT '执行时长（毫秒）',
    result_status   TINYINT       NOT NULL DEFAULT 1 COMMENT '操作结果（0：失败，1：成功）',
    error_message   TEXT          NULL COMMENT '错误信息'
) COMMENT '操作日志表';