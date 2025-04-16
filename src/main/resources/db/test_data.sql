-- 插入用户数据
INSERT INTO `user` (`username`, `password`, `nickname`, `phone`, `email`, `avatar`, `gender`, `birthday`, `status`, `role`, `last_login_time`, `create_time`, `update_time`)
VALUES
('admin', MD5('admin123'), '管理员', '12345678901', 'admin@example.com', '', 1, '1990-01-01', 1, 1, NOW(), NOW(), NOW()),
('user1', MD5('password1'), '用户1', '12345678902', 'user1@example.com', '', 1, '1991-01-01', 1, 2, NOW(), NOW(), NOW()),
('user2', MD5('password2'), '用户2', '12345678903', 'user2@example.com', '', 2, '1992-01-01', 1, 2, NOW(), NOW(), NOW()),
('user3', MD5('password3'), '用户3', '12345678904', 'user3@example.com', '', 1, '1993-01-01', 1, 2, NOW(), NOW(), NOW()),
('user4', MD5('password4'), '用户4', '12345678905', 'user4@example.com', '', 2, '1994-01-01', 1, 2, NOW(), NOW(), NOW()),
('user5', MD5('password5'), '用户5', '12345678906', 'user5@example.com', '', 1, '1995-01-01', 1, 2, NOW(), NOW(), NOW()),
('user6', MD5('password6'), '用户6', '12345678907', 'user6@example.com', '', 2, '1996-01-01', 1, 2, NOW(), NOW(), NOW()),
('user7', MD5('password7'), '用户7', '12345678908', 'user7@example.com', '', 1, '1997-01-01', 1, 2, NOW(), NOW(), NOW()),
('user8', MD5('password8'), '用户8', '12345678909', 'user8@example.com', '', 2, '1998-01-01', 1, 2, NOW(), NOW(), NOW()),
('user9', MD5('password9'), '用户9', '12345678910', 'user9@example.com', '', 1, '1999-01-01', 1, 2, NOW(), NOW(), NOW());

-- 插入商品分类数据
INSERT INTO `category` (`parent_id`, `name`, `level`, `sort`, `icon`, `status`, `create_time`, `update_time`)
VALUES
(0, '电子产品', 1, 1, '', 1, NOW(), NOW()),
(1, '手机', 2, 1, '', 1, NOW(), NOW()),
(1, '笔记本电脑', 2, 2, '', 1, NOW(), NOW()),
(1, '平板电脑', 2, 3, '', 1, NOW(), NOW()),
(1, '智能手表', 2, 4, '', 1, NOW(), NOW()),
(1, '相机', 2, 5, '', 1, NOW(), NOW()),
(1, '耳机', 2, 6, '', 1, NOW(), NOW()),
(1, '音响', 2, 7, '', 1, NOW(), NOW()),
(1, '智能家居', 2, 8, '', 1, NOW(), NOW()),
(1, '无人机', 2, 9, '', 1, NOW(), NOW()),
(1, '游戏机', 2, 10, '', 1, NOW(), NOW()),
(1, '投影仪', 2, 11, '', 1, NOW(), NOW()),
(1, '智能眼镜', 2, 12, '', 1, NOW(), NOW()),
(1, '智能音箱', 2, 13, '', 1, NOW(), NOW()),
(1, 'VR设备', 2, 14, '', 1, NOW(), NOW());

-- 插入商品数据
INSERT INTO `product` (`category_id`, `name`, `price`, `original_price`, `stock`, `sales`, `image`, `images`, `description`, `detail`, `status`, `create_time`, `update_time`)
VALUES
(2, 'iPhone 13', 6999.00, 7999.00, 100, 50, '', '', '苹果最新款智能手机，搭载A15芯片，支持5G网络。', 'iPhone 13配备6.1英寸超视网膜XDR显示屏，支持HDR显示，拥有更长的电池续航时间。', 1, NOW(), NOW()),
(2, 'Samsung Galaxy S21', 5999.00, 6999.00, 80, 30, '', '', '三星旗舰智能手机，搭载Exynos 2100处理器，支持5G网络。', 'Samsung Galaxy S21配备6.2英寸动态AMOLED 2X显示屏，支持120Hz刷新率，拥有强大的拍摄功能。', 1, NOW(), NOW()),
(3, 'MacBook Pro', 12999.00, 13999.00, 50, 20, '', '', '苹果高性能笔记本电脑，搭载M1芯片，适合专业用户。', 'MacBook Pro配备13.3英寸视网膜显示屏，支持P3广色域，拥有长达20小时的电池续航时间。', 1, NOW(), NOW()),
(3, 'Dell XPS 13', 8999.00, 9999.00, 60, 25, '', '', '戴尔高性能笔记本电脑，搭载第11代英特尔酷睿处理器。', 'Dell XPS 13配备13.4英寸FHD+显示屏，支持触控操作，拥有超长的电池续航时间。', 1, NOW(), NOW()),
(4, 'iPad Pro', 7999.00, 8999.00, 70, 40, '', '', '苹果最新款平板电脑，搭载M1芯片，支持Apple Pencil。', 'iPad Pro配备12.9英寸Liquid Retina XDR显示屏，支持ProMotion技术，拥有强大的多任务处理能力。', 1, NOW(), NOW()),
(4, 'Samsung Galaxy Tab', 4999.00, 5999.00, 90, 35, '', '', '三星高性能平板电脑，搭载Exynos 2100处理器。', 'Samsung Galaxy Tab配备11英寸TFT显示屏，支持S Pen，拥有强大的多媒体功能。', 1, NOW(), NOW()),
(5, 'Apple Watch', 2999.00, 3499.00, 120, 60, '', '', '苹果智能手表，支持心率监测和运动追踪。', 'Apple Watch配备Retina显示屏，支持血氧检测和ECG心电图功能，拥有全天候健康监测能力。', 1, NOW(), NOW()),
(5, 'Fitbit Versa', 1999.00, 2499.00, 110, 55, '', '', 'Fitbit智能手表，支持心率监测和睡眠追踪。', 'Fitbit Versa配备AMOLED显示屏，支持GPS定位和音乐播放，拥有长达6天的电池续航时间。', 1, NOW(), NOW()),
(6, 'Canon EOS R5', 25999.00, 26999.00, 30, 10, '', '', '佳能全画幅无反相机，支持8K视频拍摄。', 'Canon EOS R5配备4500万像素全画幅CMOS传感器，支持双像素CMOS AF II自动对焦系统，拥有强大的视频拍摄能力。', 1, NOW(), NOW()),
(6, 'Nikon Z6', 15999.00, 16999.00, 40, 15, '', '', '尼康全画幅无反相机，支持4K视频拍摄。', 'Nikon Z6配备2450万像素全画幅CMOS传感器，支持273点自动对焦系统，拥有出色的低光拍摄能力。', 1, NOW(), NOW()),
(7, 'Sony WH-1000XM4', 2999.00, 3499.00, 150, 70, '', '', '索尼降噪耳机，支持蓝牙连接。', 'Sony WH-1000XM4配备40mm驱动单元，支持LDAC高解析度音频传输，拥有出色的降噪效果。', 1, NOW(), NOW()),
(7, 'Bose QuietComfort 35', 2499.00, 2999.00, 140, 65, '', '', 'Bose降噪耳机，支持蓝牙连接。', 'Bose QuietComfort 35配备TriPort声学结构，支持Bose AR增强现实音频，拥有舒适的佩戴体验。', 1, NOW(), NOW()),
(8, 'JBL Charge 4', 999.00, 1299.00, 200, 90, '', '', 'JBL便携音响，支持蓝牙连接。', 'JBL Charge 4配备20W功率输出，支持IPX7防水，拥有长达20小时的电池续航时间。', 1, NOW(), NOW()),
(8, 'Sony SRS-XB43', 1499.00, 1799.00, 180, 85, '', '', '索尼便携音响，支持蓝牙连接。', 'Sony SRS-XB43配备双被动振膜，支持EXTRA BASS™技术，拥有出色的低音表现。', 1, NOW(), NOW()),
(9, 'Google Nest Hub', 1999.00, 2299.00, 100, 50, '', '', '谷歌智能家居设备，支持语音控制。', 'Google Nest Hub配备7英寸触控屏幕，支持Google Assistant，拥有智能家居控制功能。', 1, NOW(), NOW()),
(9, 'Amazon Echo', 999.00, 1299.00, 120, 60, '', '', '亚马逊智能音箱，支持语音控制。', 'Amazon Echo配备360度环绕声，支持Alexa语音助手，拥有智能家居控制功能。', 1, NOW(), NOW()),
(10, 'DJI Mavic Air 2', 7999.00, 8999.00, 50, 20, '', '', '大疆无人机，支持4K视频拍摄。', 'DJI Mavic Air 2配备1/2英寸CMOS传感器，支持智能跟随3.0，拥有长达34分钟的飞行时间。', 1, NOW(), NOW()),
(10, 'Parrot Anafi', 5999.00, 6999.00, 60, 25, '', '', 'Parrot无人机，支持4K视频拍摄。', 'Parrot Anafi配备21MP摄像头，支持180度旋转拍摄，拥有长达25分钟的飞行时间。', 1, NOW(), NOW()),
(11, 'PlayStation 5', 4999.00, 5999.00, 70, 30, '', '', '索尼游戏机，支持4K游戏体验。', 'PlayStation 5配备AMD Ryzen Zen 2处理器，支持光线追踪技术，拥有超高速SSD存储。', 1, NOW(), NOW()),
(11, 'Xbox Series X', 4999.00, 5999.00, 80, 35, '', '', '微软游戏机，支持4K游戏体验。', 'Xbox Series X配备AMD Zen 2处理器，支持光线追踪技术，拥有1TB SSD存储。', 1, NOW(), NOW());

-- 插入购物车数据
INSERT INTO `cart` (`user_id`, `product_id`, `quantity`, `checked`, `create_time`, `update_time`)
VALUES
(1, 1, 2, 1, NOW(), NOW()),
(1, 3, 1, 1, NOW(), NOW()),
(2, 2, 1, 1, NOW(), NOW()),
(2, 4, 2, 1, NOW(), NOW()),
(3, 5, 1, 1, NOW(), NOW()),
(3, 6, 1, 1, NOW(), NOW()),
(4, 7, 3, 1, NOW(), NOW()),
(4, 8, 1, 1, NOW(), NOW()),
(5, 9, 2, 1, NOW(), NOW()),
(5, 10, 1, 1, NOW(), NOW()),
(6, 11, 1, 1, NOW(), NOW()),
(6, 12, 2, 1, NOW(), NOW()),
(7, 13, 1, 1, NOW(), NOW()),
(7, 14, 1, 1, NOW(), NOW()),
(8, 15, 2, 1, NOW(), NOW()),
(8, 16, 1, 1, NOW(), NOW()),
(9, 17, 1, 1, NOW(), NOW()),
(9, 18, 2, 1, NOW(), NOW()),
(10, 19, 1, 1, NOW(), NOW()),
(10, 20, 1, 1, NOW(), NOW());

-- 插入订单数据
INSERT INTO `orders` (`order_no`, `user_id`, `total_amount`, `pay_amount`, `freight_amount`, `pay_type`, `status`, `address_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `note`, `create_time`, `update_time`)
VALUES
('ORD001', 1, 13998.00, 13998.00, 0.00, 1, 3, 1, '张三', '12345678901', '北京市朝阳区某街道1号', '', NOW(), NOW()),
('ORD002', 2, 11998.00, 11998.00, 0.00, 2, 3, 2, '李四', '12345678902', '上海市浦东新区某街道2号', '', NOW(), NOW()),
('ORD003', 3, 15998.00, 15998.00, 0.00, 1, 3, 3, '王五', '12345678903', '广州市天河区某街道3号', '', NOW(), NOW()),
('ORD004', 4, 9998.00, 9998.00, 0.00, 2, 3, 4, '赵六', '12345678904', '深圳市南山区某街道4号', '', NOW(), NOW()),
('ORD005', 5, 7998.00, 7998.00, 0.00, 1, 3, 5, '孙七', '12345678905', '杭州市西湖区某街道5号', '', NOW(), NOW());

-- 插入订单商品数据
INSERT INTO `order_item` (`order_id`, `order_no`, `product_id`, `product_name`, `product_image`, `price`, `quantity`, `total_price`, `create_time`, `update_time`)
VALUES
(1, 'ORD001', 1, 'iPhone 13', '', 6999.00, 2, 13998.00, NOW(), NOW()),
(2, 'ORD002', 2, 'Samsung Galaxy S21', '', 5999.00, 2, 11998.00, NOW(), NOW()),
(3, 'ORD003', 3, 'MacBook Pro', '', 12999.00, 1, 12999.00, NOW(), NOW()),
(3, 'ORD003', 4, 'Dell XPS 13', '', 8999.00, 1, 8999.00, NOW(), NOW()),
(4, 'ORD004', 5, 'Apple Watch', '', 2999.00, 2, 5998.00, NOW(), NOW());

-- 插入收货地址数据
INSERT INTO `address` (`user_id`, `receiver_name`, `receiver_phone`, `province`, `city`, `district`, `detail_address`, `is_default`, `create_time`, `update_time`)
VALUES
(1, '张三', '12345678901', '北京市', '北京市', '朝阳区', '某街道1号', 1, NOW(), NOW()),
(2, '李四', '12345678902', '上海市', '上海市', '浦东新区', '某街道2号', 1, NOW(), NOW()),
(3, '王五', '12345678903', '广东省', '广州市', '天河区', '某街道3号', 1, NOW(), NOW()),
(4, '赵六', '12345678904', '广东省', '深圳市', '南山区', '某街道4号', 1, NOW(), NOW()),
(5, '孙七', '12345678905', '浙江省', '杭州市', '西湖区', '某街道5号', 1, NOW(), NOW());

-- 插入用户收藏数据
INSERT INTO `favorite` (`user_id`, `product_id`, `create_time`, `update_time`)
VALUES
(1, 1, NOW(), NOW()),
(2, 2, NOW(), NOW()),
(3, 3, NOW(), NOW()),
(4, 4, NOW(), NOW()),
(5, 5, NOW(), NOW());

-- 插入用户评价数据
INSERT INTO `review` (`user_id`, `product_id`, `order_id`, `content`, `rating`, `images`, `anonymous`, `create_time`, `update_time`)
VALUES
(1, 1, 1, '非常满意，手机很好用！', 5, '', 0, NOW(), NOW()),
(2, 2, 2, '性价比很高，值得购买。', 4, '', 0, NOW(), NOW()),
(3, 3, 3, '笔记本性能强劲，推荐！', 5, '', 0, NOW(), NOW()),
(4, 4, 4, '手表功能丰富，外观漂亮。', 4, '', 0, NOW(), NOW()),
(5, 5, 5, '相机拍照效果很好。', 5, '', 0, NOW(), NOW());

-- 插入用户行为记录数据
INSERT INTO `user_behavior` (`user_id`, `product_id`, `behavior_type`, `behavior_time`, `create_time`, `update_time`)
VALUES
(1, 1, 1, NOW(), NOW(), NOW()),
(1, 2, 2, NOW(), NOW(), NOW()),
(2, 3, 3, NOW(), NOW(), NOW()),
(2, 4, 4, NOW(), NOW(), NOW()),
(3, 5, 5, NOW(), NOW(), NOW()),
(3, 6, 1, NOW(), NOW(), NOW()),
(4, 7, 2, NOW(), NOW(), NOW()),
(4, 8, 3, NOW(), NOW(), NOW()),
(5, 9, 4, NOW(), NOW(), NOW()),
(5, 10, 5, NOW(), NOW(), NOW());

-- 插入商品相似度数据
INSERT INTO `product_similarity` (`product_id`, `similar_product_id`, `similarity`, `create_time`, `update_time`)
VALUES
(1, 2, 0.9, NOW(), NOW()),
(3, 4, 0.85, NOW(), NOW()),
(5, 6, 0.8, NOW(), NOW()),
(7, 8, 0.75, NOW(), NOW()),
(9, 10, 0.7, NOW(), NOW());

-- 插入用户偏好数据
INSERT INTO `user_preference` (`user_id`, `product_id`, `preference_score`, `create_time`, `update_time`)
VALUES
(1, 1, 0.95, NOW(), NOW()),
(2, 2, 0.9, NOW(), NOW()),
(3, 3, 0.85, NOW(), NOW()),
(4, 4, 0.8, NOW(), NOW()),
(5, 5, 0.75, NOW(), NOW());

-- 插入推荐结果数据
INSERT INTO `recommendation` (`user_id`, `product_id`, `recommend_type`, `score`, `create_time`, `update_time`)
VALUES
(1, 1, 1, 0.95, NOW(), NOW()),
(2, 2, 2, 0.9, NOW(), NOW()),
(3, 3, 3, 0.85, NOW(), NOW()),
(4, 4, 1, 0.8, NOW(), NOW()),
(5, 5, 2, 0.75, NOW(), NOW());

-- 更新商品数据，完善商品描述和商品详情
UPDATE `product` SET `description` = '苹果最新款智能手机，搭载A15芯片，支持5G网络。', `detail` = 'iPhone 13配备6.1英寸超视网膜XDR显示屏，支持HDR显示，拥有更长的电池续航时间。' WHERE `name` = 'iPhone 13';
UPDATE `product` SET `description` = '三星旗舰智能手机，搭载Exynos 2100处理器，支持5G网络。', `detail` = 'Samsung Galaxy S21配备6.2英寸动态AMOLED 2X显示屏，支持120Hz刷新率，拥有强大的拍摄功能。' WHERE `name` = 'Samsung Galaxy S21';
UPDATE `product` SET `description` = '苹果高性能笔记本电脑，搭载M1芯片，适合专业用户。', `detail` = 'MacBook Pro配备13.3英寸视网膜显示屏，支持P3广色域，拥有长达20小时的电池续航时间。' WHERE `name` = 'MacBook Pro';
UPDATE `product` SET `description` = '戴尔高性能笔记本电脑，搭载第11代英特尔酷睿处理器。', `detail` = 'Dell XPS 13配备13.4英寸FHD+显示屏，支持触控操作，拥有超长的电池续航时间。' WHERE `name` = 'Dell XPS 13';
UPDATE `product` SET `description` = '苹果最新款平板电脑，搭载M1芯片，支持Apple Pencil。', `detail` = 'iPad Pro配备12.9英寸Liquid Retina XDR显示屏，支持ProMotion技术，拥有强大的多任务处理能力。' WHERE `name` = 'iPad Pro';
UPDATE `product` SET `description` = '三星高性能平板电脑，搭载Exynos 2100处理器。', `detail` = 'Samsung Galaxy Tab配备11英寸TFT显示屏，支持S Pen，拥有强大的多媒体功能。' WHERE `name` = 'Samsung Galaxy Tab';
UPDATE `product` SET `description` = '苹果智能手表，支持心率监测和运动追踪。', `detail` = 'Apple Watch配备Retina显示屏，支持血氧检测和ECG心电图功能，拥有全天候健康监测能力。' WHERE `name` = 'Apple Watch';
UPDATE `product` SET `description` = 'Fitbit智能手表，支持心率监测和睡眠追踪。', `detail` = 'Fitbit Versa配备AMOLED显示屏，支持GPS定位和音乐播放，拥有长达6天的电池续航时间。' WHERE `name` = 'Fitbit Versa';
UPDATE `product` SET `description` = '佳能全画幅无反相机，支持8K视频拍摄。', `detail` = 'Canon EOS R5配备4500万像素全画幅CMOS传感器，支持双像素CMOS AF II自动对焦系统，拥有强大的视频拍摄能力。' WHERE `name` = 'Canon EOS R5';
UPDATE `product` SET `description` = '尼康全画幅无反相机，支持4K视频拍摄。', `detail` = 'Nikon Z6配备2450万像素全画幅CMOS传感器，支持273点自动对焦系统，拥有出色的低光拍摄能力。' WHERE `name` = 'Nikon Z6';
UPDATE `product` SET `description` = '索尼降噪耳机，支持蓝牙连接。', `detail` = 'Sony WH-1000XM4配备40mm驱动单元，支持LDAC高解析度音频传输，拥有出色的降噪效果。' WHERE `name` = 'Sony WH-1000XM4';
UPDATE `product` SET `description` = 'Bose降噪耳机，支持蓝牙连接。', `detail` = 'Bose QuietComfort 35配备TriPort声学结构，支持Bose AR增强现实音频，拥有舒适的佩戴体验。' WHERE `name` = 'Bose QuietComfort 35';
UPDATE `product` SET `description` = 'JBL便携音响，支持蓝牙连接。', `detail` = 'JBL Charge 4配备20W功率输出，支持IPX7防水，拥有长达20小时的电池续航时间。' WHERE `name` = 'JBL Charge 4';
UPDATE `product` SET `description` = '索尼便携音响，支持蓝牙连接。', `detail` = 'Sony SRS-XB43配备双被动振膜，支持EXTRA BASS™技术，拥有出色的低音表现。' WHERE `name` = 'Sony SRS-XB43';
UPDATE `product` SET `description` = '谷歌智能家居设备，支持语音控制。', `detail` = 'Google Nest Hub配备7英寸触控屏幕，支持Google Assistant，拥有智能家居控制功能。' WHERE `name` = 'Google Nest Hub';
UPDATE `product` SET `description` = '亚马逊智能音箱，支持语音控制。', `detail` = 'Amazon Echo配备360度环绕声，支持Alexa语音助手，拥有智能家居控制功能。' WHERE `name` = 'Amazon Echo';
UPDATE `product` SET `description` = '大疆无人机，支持4K视频拍摄。', `detail` = 'DJI Mavic Air 2配备1/2英寸CMOS传感器，支持智能跟随3.0，拥有长达34分钟的飞行时间。' WHERE `name` = 'DJI Mavic Air 2';
UPDATE `product` SET `description` = 'Parrot无人机，支持4K视频拍摄。', `detail` = 'Parrot Anafi配备21MP摄像头，支持180度旋转拍摄，拥有长达25分钟的飞行时间。' WHERE `name` = 'Parrot Anafi';
UPDATE `product` SET `description` = '索尼游戏机，支持4K游戏体验。', `detail` = 'PlayStation 5配备AMD Ryzen Zen 2处理器，支持光线追踪技术，拥有超高速SSD存储。' WHERE `name` = 'PlayStation 5';
UPDATE `product` SET `description` = '微软游戏机，支持4K游戏体验。', `detail` = 'Xbox Series X配备AMD Zen 2处理器，支持光线追踪技术，拥有1TB SSD存储。' WHERE `name` = 'Xbox Series X';

-- 更新商品分类数据，设置分类图标
UPDATE `category` SET `icon` = '/uploads/2025/04/15/e534b4bfc9fd4bb2804e96b115f2920f.jpg';

-- 更新商品数据，设置商品图片
UPDATE `product` SET `image` = '/uploads/2025/04/15/e534b4bfc9fd4bb2804e96b115f2920f.jpg', `images` = '/uploads/2025/04/15/e534b4bfc9fd4bb2804e96b115f2920f.jpg';

-- 更新用户数据，设置用户头像
UPDATE `user` SET `avatar` = '/uploads/2025/04/15/e534b4bfc9fd4bb2804e96b115f2920f.jpg';

-- 更新用户评价数据，设置评论图片
UPDATE `review` SET `images` = '/uploads/2025/04/15/e534b4bfc9fd4bb2804e96b115f2920f.jpg';
