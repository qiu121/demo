# CREATE DATABASE library CHARACTER SET utf8mb4;
# DROP DATABASE library;
CREATE TABLE users
(
    user_id      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID，主键',
    username     VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名，唯一',
    password     VARCHAR(255) NOT NULL COMMENT '用户密码，加密存储',
    email        VARCHAR(100) UNIQUE COMMENT '邮箱，唯一',
    phone        VARCHAR(20) UNIQUE COMMENT '手机号，唯一',
    create_time  DATETIME               DEFAULT CURRENT_TIMESTAMP COMMENT '账户创建时间',
    updated_time DATETIME               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '账户更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表，存储普通用户和管理员信息';


CREATE TABLE books
(
    book_id      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '图书ID，主键',
    title        VARCHAR(255) NOT NULL COMMENT '书名',
    author       VARCHAR(100) NOT NULL COMMENT '作者',
    publisher    VARCHAR(100) COMMENT '出版社',
    publish_year INT COMMENT '出版年份',
    category     VARCHAR(50) COMMENT '图书分类，如小说、技术、历史等',
    isbn         VARCHAR(20) UNIQUE COMMENT 'ISBN编号，唯一',
    stock        INT          NOT NULL DEFAULT 0 COMMENT '库存数量',
    available    INT          NOT NULL DEFAULT 0 COMMENT '当前可借数量',
    create_time  DATETIME              DEFAULT CURRENT_TIMESTAMP COMMENT '图书入库时间',
    update_time  DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '图书信息更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='图书表，存储书籍信息';


CREATE TABLE borrow_records
(
    record_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '借阅记录ID，主键',
    user_id     INT  NOT NULL COMMENT '借书用户ID，外键关联users表',
    book_id     INT  NOT NULL COMMENT '借阅的图书ID，外键关联books表',
    borrow_date DATE NOT NULL COMMENT '借书日期',
    due_date    DATE NOT NULL COMMENT '应还日期',
    return_date DATE                                     DEFAULT NULL COMMENT '实际归还日期',
    status      ENUM ('borrowed', 'returned', 'overdue') DEFAULT 'borrowed' COMMENT '借阅状态',
    fine        DECIMAL(10, 2)                           DEFAULT 0 COMMENT '逾期罚款金额',
    create_time DATETIME                                 DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    update_time DATETIME                                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='借阅记录表，存储用户的借书和归还记录';


CREATE TABLE fines
(
    fine_id     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '罚款ID，主键',
    user_id     INT            NOT NULL COMMENT '用户ID，外键关联users表',
    record_id   INT            NOT NULL COMMENT '对应的借阅记录ID，外键关联borrow_records表',
    fine_amount DECIMAL(10, 2) NOT NULL COMMENT '罚款金额',
    paid        BOOLEAN  DEFAULT FALSE COMMENT '是否已支付罚款',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '罚款生成时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '罚款更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='逾期罚款表，存储用户的逾期罚款记录';


CREATE TABLE system_logs
(
    log_id     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID，主键',
    user_id    INT          NOT NULL COMMENT '操作用户ID，外键关联users表',
    action     VARCHAR(255) NOT NULL COMMENT '执行的操作，例如“新增图书”',
    log_time   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    ip_address VARCHAR(50) COMMENT '操作IP地址',
    details    TEXT COMMENT '操作详细信息'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统日志表，记录管理员操作及重要系统事件';

CREATE TABLE book_reviews
(
    review_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评论ID，主键',
    user_id     INT NOT NULL COMMENT '评论用户ID，外键关联users表',
    book_id     INT NOT NULL COMMENT '评论的图书ID，外键关联books表',
    rating      INT CHECK (rating BETWEEN 1 AND 5) COMMENT '评分，1-5星',
    review_text TEXT COMMENT '用户的评论内容',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评论创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '评论更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='图书评论表，存储用户对图书的评分与评论';

CREATE TABLE role
(
    `role_id`      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID，主键',
    `role`         ENUM ('user', 'admin')                                 DEFAULT 'user' COMMENT '用户角色，user为普通用户，admin为管理员',
    `role_name`    VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '普通用户',
    `create_time`  DATETIME                                               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

CREATE TABLE `permission`
(
    `permission_id`   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `permission_code` VARCHAR(255) NOT NULL COMMENT '权限码',
    `permission_name` VARCHAR(255) NOT NULL COMMENT '功能名称',
    `remark`          VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
    `create_time`     DATETIME          DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`    DATETIME          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT '权限表';


CREATE TABLE `user_role`
(
    `user_role_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
    `user_id`      BIGINT NOT NULL COMMENT '用户ID',
    `role_id`      BIGINT NULL DEFAULT NULL COMMENT '角色ID',
    `create_time`  DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '用户-角色关联表';


CREATE TABLE `role_permission`
(
    `role_permission_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色权限ID',
    `role_id`            BIGINT NOT NULL COMMENT '角色ID',
    `permission_id`      BIGINT NOT NULL COMMENT '权限ID',
    `create_time`        DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`role_permission_id`) USING BTREE,
    INDEX `idx_role_id` (`role_id`) USING BTREE,
    INDEX `idx_permission_id` (`permission_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4 COMMENT = '角色-权限关联表';
