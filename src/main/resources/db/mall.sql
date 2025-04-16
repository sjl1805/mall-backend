-- 创建数据库
CREATE DATABASE IF NOT EXISTS mall DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE mall;

-- 用户表
CREATE TABLE IF NOT EXISTS `user`
(
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`        VARCHAR(50)  NOT NULL COMMENT '用户名',
    `password`        VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname`        VARCHAR(50) COMMENT '昵称',
    `phone`           VARCHAR(20) COMMENT '手机号',
    `email`           VARCHAR(100) COMMENT '邮箱',
    `avatar`          VARCHAR(255) COMMENT '头像',
    `gender`          TINYINT  DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    `birthday`        DATE COMMENT '生日',
    `status`          TINYINT  DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    `role`            TINYINT  DEFAULT 1 COMMENT '角色：1-管理员，2-用户',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `create_time`     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 商品分类表
CREATE TABLE IF NOT EXISTS `category`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `parent_id`   BIGINT   DEFAULT 0 COMMENT '父分类ID',
    `name`        VARCHAR(50) NOT NULL COMMENT '分类名称',
    `level`       TINYINT  DEFAULT 1 COMMENT '分类层级',
    `sort`        INT      DEFAULT 0 COMMENT '排序',
    `icon`        VARCHAR(255) COMMENT '图标',
    `status`      TINYINT  DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品分类表';

-- 商品表
CREATE TABLE IF NOT EXISTS `product`
(
    `id`             BIGINT         NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `category_id`    BIGINT         NOT NULL COMMENT '分类ID',
    `name`           VARCHAR(100)   NOT NULL COMMENT '商品名称',
    `price`          DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    `original_price` DECIMAL(10, 2) COMMENT '原价',
    `stock`          INT            NOT NULL DEFAULT 0 COMMENT '库存',
    `sales`          INT                     DEFAULT 0 COMMENT '销量',
    `image`          VARCHAR(255) COMMENT '商品主图',
    `images`         TEXT COMMENT '商品图片集',
    `description`    TEXT COMMENT '商品描述',
    `detail`         TEXT COMMENT '商品详情',
    `status`         TINYINT                 DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    `create_time`    DATETIME                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品表';

-- 购物车表
CREATE TABLE IF NOT EXISTS `cart`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
    `user_id`     BIGINT NOT NULL COMMENT '用户ID',
    `product_id`  BIGINT NOT NULL COMMENT '商品ID',
    `quantity`    INT    NOT NULL DEFAULT 1 COMMENT '数量',
    `checked`     TINYINT         DEFAULT 1 COMMENT '是否选中：0-未选中，1-选中',
    `create_time` DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_product` (`user_id`, `product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='购物车表';

-- 订单表
CREATE TABLE IF NOT EXISTS `orders`
(
    `id`               BIGINT         NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `order_no`         VARCHAR(50)    NOT NULL COMMENT '订单编号',
    `user_id`          BIGINT         NOT NULL COMMENT '用户ID',
    `total_amount`     DECIMAL(10, 2) NOT NULL COMMENT '订单总金额',
    `pay_amount`       DECIMAL(10, 2) NOT NULL COMMENT '实付金额',
    `freight_amount`   DECIMAL(10, 2) DEFAULT 0 COMMENT '运费',
    `pay_type`         TINYINT COMMENT '支付方式：1-支付宝，2-微信',
    `status`           TINYINT        DEFAULT 0 COMMENT '订单状态：0-待付款，1-待发货，2-待收货，3-已完成，4-已取消',
    `address_id`       BIGINT         NOT NULL COMMENT '收货地址ID',
    `receiver_name`    VARCHAR(50)    NOT NULL COMMENT '收货人姓名',
    `receiver_phone`   VARCHAR(20)    NOT NULL COMMENT '收货人电话',
    `receiver_address` VARCHAR(255)   NOT NULL COMMENT '收货地址',
    `note`             VARCHAR(500) COMMENT '订单备注',
    `create_time`      DATETIME       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单表';

-- 订单商品表
CREATE TABLE IF NOT EXISTS `order_item`
(
    `id`            BIGINT         NOT NULL AUTO_INCREMENT COMMENT '订单商品ID',
    `order_id`      BIGINT         NOT NULL COMMENT '订单ID',
    `order_no`      VARCHAR(50)    NOT NULL COMMENT '订单编号',
    `product_id`    BIGINT         NOT NULL COMMENT '商品ID',
    `product_name`  VARCHAR(100)   NOT NULL COMMENT '商品名称',
    `product_image` VARCHAR(255) COMMENT '商品图片',
    `price`         DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    `quantity`      INT            NOT NULL COMMENT '购买数量',
    `total_price`   DECIMAL(10, 2) NOT NULL COMMENT '商品总价',
    `create_time`   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_order_no` (`order_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单商品表';

-- 收货地址表
CREATE TABLE IF NOT EXISTS `address`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '地址ID',
    `user_id`        BIGINT       NOT NULL COMMENT '用户ID',
    `receiver_name`  VARCHAR(50)  NOT NULL COMMENT '收货人姓名',
    `receiver_phone` VARCHAR(20)  NOT NULL COMMENT '收货人电话',
    `province`       VARCHAR(50)  NOT NULL COMMENT '省份',
    `city`           VARCHAR(50)  NOT NULL COMMENT '城市',
    `district`       VARCHAR(50)  NOT NULL COMMENT '区县',
    `detail_address` VARCHAR(255) NOT NULL COMMENT '详细地址',
    `is_default`     TINYINT  DEFAULT 0 COMMENT '是否默认：0-否，1-是',
    `create_time`    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='收货地址表';

-- 用户收藏表
CREATE TABLE IF NOT EXISTS `favorite`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id`     BIGINT NOT NULL COMMENT '用户ID',
    `product_id`  BIGINT NOT NULL COMMENT '商品ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_product` (`user_id`, `product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户收藏表';

-- 用户评价表
CREATE TABLE IF NOT EXISTS `review`
(
    `id`          BIGINT  NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `user_id`     BIGINT  NOT NULL COMMENT '用户ID',
    `product_id`  BIGINT  NOT NULL COMMENT '商品ID',
    `order_id`    BIGINT  NOT NULL COMMENT '订单ID',
    `content`     TEXT    NOT NULL COMMENT '评价内容',
    `rating`      TINYINT NOT NULL COMMENT '评分：1-5星',
    `images`      TEXT COMMENT '评价图片',
    `anonymous`   TINYINT  DEFAULT 0 COMMENT '是否匿名：0-否，1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户评价表';

-- 用户行为记录表（用于协同过滤）
CREATE TABLE IF NOT EXISTS `user_behavior`
(
    `id`            BIGINT   NOT NULL AUTO_INCREMENT COMMENT '行为ID',
    `user_id`       BIGINT   NOT NULL COMMENT '用户ID',
    `product_id`    BIGINT   NOT NULL COMMENT '商品ID',
    `behavior_type` TINYINT  NOT NULL COMMENT '行为类型：1-浏览，2-收藏，3-加购，4-购买，5-评价',
    `behavior_time` DATETIME NOT NULL COMMENT '行为时间',
    `create_time`   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_behavior_time` (`behavior_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户行为记录表';

-- 商品相似度表（用于基于物品的协同过滤）
CREATE TABLE IF NOT EXISTS `product_similarity`
(
    `id`                 BIGINT         NOT NULL AUTO_INCREMENT COMMENT '相似度ID',
    `product_id`         BIGINT         NOT NULL COMMENT '商品ID',
    `similar_product_id` BIGINT         NOT NULL COMMENT '相似商品ID',
    `similarity`         DECIMAL(10, 6) NOT NULL COMMENT '相似度分数',
    `create_time`        DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_product_similar` (`product_id`, `similar_product_id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_similar_product_id` (`similar_product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品相似度表';

-- 用户偏好表（用于基于用户的协同过滤）
CREATE TABLE IF NOT EXISTS `user_preference`
(
    `id`               BIGINT         NOT NULL AUTO_INCREMENT COMMENT '偏好ID',
    `user_id`          BIGINT         NOT NULL COMMENT '用户ID',
    `product_id`       BIGINT         NOT NULL COMMENT '商品ID',
    `preference_score` DECIMAL(10, 6) NOT NULL COMMENT '偏好分数',
    `create_time`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_product` (`user_id`, `product_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_product_id` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户偏好表';

-- 推荐结果表
CREATE TABLE IF NOT EXISTS `recommendation`
(
    `id`             BIGINT         NOT NULL AUTO_INCREMENT COMMENT '推荐ID',
    `user_id`        BIGINT         NOT NULL COMMENT '用户ID',
    `product_id`     BIGINT         NOT NULL COMMENT '商品ID',
    `recommend_type` TINYINT        NOT NULL COMMENT '推荐类型：1-基于用户，2-基于物品，3-热门推荐',
    `score`          DECIMAL(10, 6) NOT NULL COMMENT '推荐分数',
    `create_time`    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_product_type` (`user_id`, `product_id`, `recommend_type`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_product_id` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推荐结果表';