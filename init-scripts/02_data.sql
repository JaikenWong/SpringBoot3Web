-- 插入角色数据
INSERT INTO roles (name, description) VALUES 
('ROLE_ADMIN', 'Administrator role with full access'),
('ROLE_USER', 'Standard user role with limited access'),
('ROLE_MANAGER', 'Manager role with medium access');

-- 插入用户数据 (密码均为加密后的"password")
INSERT INTO users (username, email, password) VALUES
('admin', 'admin@example.com', '$2a$10$ScImjwXCVU7A4mHLEcxCAO5xDftsJFMLncO0jmcbGOXniwu5NSVhe'),
('john', 'john@example.com', '$2a$10$ScImjwXCVU7A4mHLEcxCAO5xDftsJFMLncO0jmcbGOXniwu5NSVhe'),
('jane', 'jane@example.com', '$2a$10$ScImjwXCVU7A4mHLEcxCAO5xDftsJFMLncO0jmcbGOXniwu5NSVhe');

-- 分配用户角色
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- admin用户具有ROLE_ADMIN角色
(2, 2), -- john用户具有ROLE_USER角色
(3, 2), -- jane用户具有ROLE_USER角色
(3, 3); -- jane用户还具有ROLE_MANAGER角色