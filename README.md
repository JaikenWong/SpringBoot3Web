# Spring Web 博客系统

这是一个基于 Spring Boot 的博客管理系统，提供博客的增删改查、用户认证和操作日志记录等功能。

## 技术栈

- **后端框架**: Spring Boot 3.5.5
- **编程语言**: Java 21
- **数据库**: MySQL 8.0+
- **ORM框架**: MyBatis-Plus
- **安全框架**: Spring Security
- **缓存**: Redis
- **工具类库**: Hutool
- **日志框架**: SLF4J + Logback
- **构建工具**: Maven
- **其他**: Lombok, Docker Compose

## 功能特性

- 用户注册、登录、登出
- JWT Token 认证机制
- 博客文章管理（增删改查）
- 分页查询博客列表
- 操作日志记录（AOP实现）
- 统一返回结果格式
- 数据校验
- RESTful API 设计

## 项目结构

```
src/main/java/com/jaiken/springweb/
├── annotation/     # 自定义注解
├── aop/            # 面向切面编程
├── config/         # 配置类
├── controller/     # 控制器层
├── dto/            # 数据传输对象
├── entity/         # 实体类
├── mapper/         # 数据访问层
├── service/        # 业务逻辑层
└── util/           # 工具类
```

## 核心功能接口

### 用户管理接口

- `POST /api/user/save` - 用户注册

### 认证接口

- `POST /api/login` - 用户登录
- `POST /api/logout` - 用户登出
- `POST /api/refresh` - Token续期

### 博客管理接口

- `GET /api/blogs/list` - 获取博客列表（分页）
- `GET /api/blogs/{id}` - 获取博客详情
- `POST /api/blogs/edit` - 编辑博客（新增/修改）

## 快速开始

### 环境要求

- JDK 21
- MySQL 8.0+
- Redis
- Maven 3.6+

### 配置步骤

1. 克隆项目到本地
2. 创建数据库并执行初始化脚本
3. 修改 `src/main/resources/application.yml` 中的数据库和Redis连接配置
4. 使用 Maven 构建项目：
   ```bash
   mvn clean install
   ```
5. 启动应用：
   ```bash
   mvn spring-boot:run
   ```

### Docker Compose 启动（推荐）

项目提供了 Docker Compose 配置文件，可以一键启动 MySQL 和 Redis 服务：

```bash
docker-compose up -d
```

## 数据库设计

项目包含以下数据表：

1. `users` - 用户表
2. `roles` - 角色表
3. `user_roles` - 用户角色关联表
4. `blog` - 博客表

## 配置说明

主要配置文件：`src/main/resources/application.yml`

关键配置项：
- 服务器端口
- 数据库连接信息
- Redis 连接信息
- JWT 密钥和过期时间
- 日志级别

## 开发规范

- 使用 Lombok 简化实体类代码
- 使用 MyBatis-Plus 简化数据访问层开发
- 通过自定义注解+AOP实现操作日志记录
- 统一返回结果格式（Result 对象）
- 使用 Hutool 工具库简化开发

## 注意事项

- 所有接口都需要通过 JWT Token 进行认证（除登录和注册接口）
- 博客编辑接口会自动填充创建时间、用户ID等信息
- 数据库表结构需要与实体类保持一致
- 用户密码采用 BCrypt 加密存储

## 版本信息

- 开发者：JaikenWong
- 创建时间：2025-08-30

## 许可证

[待补充许可证信息]