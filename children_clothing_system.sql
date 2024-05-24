/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Host           : localhost:3306
 Source Schema         : children_clothing_system

 Target Server Type    : MySQL
 Target Server Version : 80023 (8.0.23)
 File Encoding         : 65001

 Date: 19/04/2024 10:15:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for x_activity
-- ----------------------------
DROP TABLE IF EXISTS `x_activity`;
CREATE TABLE `x_activity`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动昵称',
  `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '活动折扣',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '满减多少',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_activity
-- ----------------------------
INSERT INTO `x_activity` VALUES (1, '原价', 1.00, 0.00, '原价');
INSERT INTO `x_activity` VALUES (2, '满减', 200.00, 30.00, '满200减20');
INSERT INTO `x_activity` VALUES (3, '打折', 0.80, 0.00, '八折');

-- ----------------------------
-- Table structure for x_address
-- ----------------------------
DROP TABLE IF EXISTS `x_address`;
CREATE TABLE `x_address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `useraddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `x_address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地址信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_address
-- ----------------------------
INSERT INTO `x_address` VALUES (1, 5, '张三丰', '上海市浦东新区888号', '18899997777');
INSERT INTO `x_address` VALUES (4, 5, '张无忌', '安徽省合肥市888号', '18877776666');
INSERT INTO `x_address` VALUES (5, 5, '张大嘴', '北京市海淀区888号', '18866665555');
INSERT INTO `x_address` VALUES (6, 20, '李大嘴', '上海市浦东新区222号', '18866661111');
INSERT INTO `x_address` VALUES (8, 20, '张三丰', '123456', '12345678911');
INSERT INTO `x_address` VALUES (10, 23, 'test', '贵州省遵义市红花岗区', '1008611');
INSERT INTO `x_address` VALUES (11, 26, 'test', 'm78星云', '10086');
INSERT INTO `x_address` VALUES (12, 26, '123', '123', '123');

-- ----------------------------
-- Table structure for x_cart
-- ----------------------------
DROP TABLE IF EXISTS `x_cart`;
CREATE TABLE `x_cart`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品ID',
  `business_id` int NULL DEFAULT NULL COMMENT '店铺ID',
  `num` int NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_cart`(`user_id` ASC) USING BTREE,
  INDEX `user_bussiness`(`business_id` ASC) USING BTREE,
  INDEX `goods_cart`(`goods_id` ASC) USING BTREE,
  CONSTRAINT `user_bussiness` FOREIGN KEY (`business_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_cart` FOREIGN KEY (`user_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_cart_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `x_goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '购物车表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_cart
-- ----------------------------
INSERT INTO `x_cart` VALUES (8, 1, 32, 2, 1);
INSERT INTO `x_cart` VALUES (9, 5, 33, 2, 1);
INSERT INTO `x_cart` VALUES (21, 20, 32, 2, 1);
INSERT INTO `x_cart` VALUES (22, 20, 34, 2, 1);
INSERT INTO `x_cart` VALUES (23, 20, 49, 2, 1);

-- ----------------------------
-- Table structure for x_collect
-- ----------------------------
DROP TABLE IF EXISTS `x_collect`;
CREATE TABLE `x_collect`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品ID',
  `business_id` int NULL DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `x_collect_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收藏信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_collect
-- ----------------------------
INSERT INTO `x_collect` VALUES (4, 5, 5, 2);
INSERT INTO `x_collect` VALUES (5, 5, 6, 2);
INSERT INTO `x_collect` VALUES (7, 9, 5, 2);
INSERT INTO `x_collect` VALUES (8, 5, 10, 2);
INSERT INTO `x_collect` VALUES (9, 5, 9, 2);
INSERT INTO `x_collect` VALUES (21, 20, 6, 2);
INSERT INTO `x_collect` VALUES (22, 23, 32, 2);
INSERT INTO `x_collect` VALUES (24, 26, 32, 2);

-- ----------------------------
-- Table structure for x_comment
-- ----------------------------
DROP TABLE IF EXISTS `x_comment`;
CREATE TABLE `x_comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品ID',
  `business_id` int NULL DEFAULT NULL COMMENT '店铺ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `goods_id`(`goods_id` ASC) USING BTREE,
  INDEX `business_id`(`business_id` ASC) USING BTREE,
  CONSTRAINT `x_comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_comment_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `x_goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_comment_ibfk_3` FOREIGN KEY (`business_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_comment
-- ----------------------------
INSERT INTO `x_comment` VALUES (1, 5, 35, 2, '衣服不错，下次再来！！！', '2023-11-10 17:10:30');
INSERT INTO `x_comment` VALUES (2, 5, 35, 2, '这衣服确实不错，雀食牛逼！！！', '2023-11-10 17:12:55');
INSERT INTO `x_comment` VALUES (3, 9, 35, 2, '听朋友说这家店不错，我也买了，看看效果怎么样，好的话，下次再来！', '2023-11-10 17:35:11');
INSERT INTO `x_comment` VALUES (4, 20, 35, 2, '123456', '2024-04-13 00:42:22');

-- ----------------------------
-- Table structure for x_goods
-- ----------------------------
DROP TABLE IF EXISTS `x_goods`;
CREATE TABLE `x_goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品主图',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '商品介绍',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '计件单位',
  `count` int NULL DEFAULT 0 COMMENT '商品销量',
  `type_id` int NULL DEFAULT NULL COMMENT '分类ID',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  `activity_id` int NULL DEFAULT NULL COMMENT '活动id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `goodsType`(`type_id` ASC) USING BTREE,
  INDEX `activity_id`(`activity_id` ASC) USING BTREE,
  CONSTRAINT `goodsType` FOREIGN KEY (`type_id`) REFERENCES `x_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_goods_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `x_activity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_goods
-- ----------------------------
INSERT INTO `x_goods` VALUES (32, '女童T恤2024夏装新款韩版儿童时髦短袖宝宝夏季纯棉字母印花上衣', 'https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/909f5e4b305b4746a9bbd18dae5f8124女童T恤2024夏装新款韩版儿童时髦短袖1.png', '<p><img src=\"http://localhost:9999/files/1713089551871-1-2.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9999/files/1713089556338-女童T恤2024夏装新款韩版儿童时髦短袖1.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><br/></p><h1>女童T恤2024夏装新款韩版儿童时髦短袖宝宝夏季纯棉字母印花上衣</h1>', 49.90, '件', 0, 16, 2, 1);
INSERT INTO `x_goods` VALUES (33, '女童短袖儿童T恤2024夏装新款休闲宝宝卡通中大童纯棉上衣童装', 'https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/e14052f3932a406d9c99977b931015c8女童短袖儿童T恤2024夏装新款休闲宝宝卡通中大童纯棉上衣童装2.png', '<p><img src=\"http://localhost:9999/files/1713106383812-2-1.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9999/files/1713106389087-2-2.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9999/files/1713106438525-2-3.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9999/files/1713106444750-2-4.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9999/files/1713106450776-女童短袖儿童T恤2024夏装新款休闲宝宝卡通中大童纯棉上衣童装2.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><h1>女童短袖儿童T恤2024夏装新款休闲宝宝卡通中大童纯棉上衣童装</h1>', 49.90, '件', 0, 16, 2, 1);
INSERT INTO `x_goods` VALUES (34, '悦游童装 [滑板猫猫]潮牌男童女童儿童短袖T恤夏季纯棉宽松上衣', 'https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/b01343194d4d421fab0040f36e05beb23.png', '<h1>悦游童装 [滑板猫猫]潮牌男童女童儿童短袖T恤夏季纯棉宽松上衣</h1>', 59.00, '件', 0, 13, 2, 1);
INSERT INTO `x_goods` VALUES (35, '女童外套2024春季新款儿童棒球服春秋加绒女大童洋气开衫休闲上衣', 'https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/39bee4bcbdf74350ba9249037366daff4.png', '<p><img src=\"http://localhost:9999/files/1713109389719-4-1.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9999/files/1713109395458-4-2.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><br/></p><h1>女童外套2024春季新款儿童棒球服春秋加绒女大童洋气开衫休闲上衣</h1>', 68.90, '件', 0, 7, 2, 1);
INSERT INTO `x_goods` VALUES (36, '男童外套春秋时尚儿童春装防水风衣2024年中大童男孩冲锋衣连帽潮', 'https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/181d1677c1ea4ec58fc0101c8c1a982f5.png', '<p><img src=\"https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/73a1d2b061324f668aff96a8ff28be165-1.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/bd52cc1e44c143cea8a252a5e1369aca5-2.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/14/3f358f5d184d45cba2c17ff19b0ff3c05-3.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 206.00, '件', 0, 8, 2, 1);
INSERT INTO `x_goods` VALUES (49, '外套', 'https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/17/760ba412564c46138a01317d14ce7c468.png', '<p><img src=\"https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/17/56e4110185dc48f8bae563e6b12bc78c5.png\" style=\"max-width:100%;\" contenteditable=\"false\"/><br/>13456<img src=\"https://boluo12138.oss-cn-beijing.aliyuncs.com/2024/04/17/7183a881a1d74aa8bde2a6a761ba0f838-1.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p>', 1234.00, '套', 0, 8, 2, 1);

-- ----------------------------
-- Table structure for x_menu
-- ----------------------------
DROP TABLE IF EXISTS `x_menu`;
CREATE TABLE `x_menu`  (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `redirect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  `is_leaf` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `hidden` tinyint(1) NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_menu
-- ----------------------------
INSERT INTO `x_menu` VALUES (1, 'Layout', '/sys', '/sys/user', 'sysManage', '系统管理', 'userManage', 0, 'N', 0, 0);
INSERT INTO `x_menu` VALUES (2, 'sys/user', 'user', NULL, 'userList', '用户列表', 'user', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (3, 'sys/role', 'role', NULL, 'roleList', '角色列表', 'roleManage', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (5, 'sys/menu', 'menu', NULL, 'menuList', '菜单列表', 'menuManage', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (21, 'Layout', '/info', '/info/userInfo', 'person', '个人中心', 'userInfo', 0, 'N', 0, 0);
INSERT INTO `x_menu` VALUES (22, 'info/changepassword', 'changepassword', NULL, 'changepassword', '修改密码', 'change', 21, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (23, 'info/changeuserinfo', 'changeuserinfo', NULL, 'changeuserinfo', '修改个人信息', 'change', 21, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (24, 'Layout', '/manage', '/manage/notice', 'sysInfo', '信息管理', 'userManage', 0, 'Y', 0, 0);
INSERT INTO `x_menu` VALUES (25, 'manage/notice', 'notice', NULL, 'sysNotice', '公告管理', NULL, 24, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (26, 'manage/classification', 'classification', NULL, 'sysClassification', '分类信息', NULL, 24, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (27, 'manage/goods', 'goods', NULL, 'sysGoods', '商品信息', NULL, 24, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (28, 'manage/orders', 'orders', NULL, 'sysOrders', '订单管理', NULL, 24, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (29, 'manage/comment', 'comment', NULL, 'sysComment', '评论管理', NULL, 24, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (30, 'info/adminInfo', 'adminInfo', NULL, 'adminManage', '管理员信息', NULL, 21, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (31, 'info/businessInfo', 'businessInfo', NULL, 'businessInfo', '商家信息', NULL, 21, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (32, 'info/userInfo', 'userInfo', NULL, 'userInfo', '用户信息', NULL, 21, 'N', 0, 1);

-- ----------------------------
-- Table structure for x_notice
-- ----------------------------
DROP TABLE IF EXISTS `x_notice`;
CREATE TABLE `x_notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_notice
-- ----------------------------
INSERT INTO `x_notice` VALUES (1, '今天系统正式上线，开始内测', '今天系统正式上线，开始内测', '2023-09-05', 1);
INSERT INTO `x_notice` VALUES (2, '所有功能都已完成，可以正常使用1', '所有功能都已完成，可以正常使用', '2023-09-05', 1);
INSERT INTO `x_notice` VALUES (3, '今天天气很不错，可以出去一起玩了', '今天天气很不错，可以出去一起玩了', '2023-09-05', 1);
INSERT INTO `x_notice` VALUES (4, '欢迎加入', '欢迎加入', '2024-04-04', 1);
INSERT INTO `x_notice` VALUES (5, '123', '1234', '2024-04-04', 1);
INSERT INTO `x_notice` VALUES (8, '111', '111', '2024-04-08', 1);

-- ----------------------------
-- Table structure for x_orders
-- ----------------------------
DROP TABLE IF EXISTS `x_orders`;
CREATE TABLE `x_orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单ID',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品ID',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  `num` int NULL DEFAULT NULL COMMENT '商品数量',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '订单价格',
  `address_id` int NULL DEFAULT NULL COMMENT '地址ID',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `address_id`(`address_id` ASC) USING BTREE,
  INDEX `business_id`(`business_id` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `goods_id`(`goods_id` ASC) USING BTREE,
  CONSTRAINT `x_orders_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `x_address` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_orders_ibfk_2` FOREIGN KEY (`business_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_orders_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_orders_ibfk_4` FOREIGN KEY (`goods_id`) REFERENCES `x_goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_orders
-- ----------------------------
INSERT INTO `x_orders` VALUES (8, '20231109180904', 49, 2, 1, 9, 129.00, 6, '已完成');
INSERT INTO `x_orders` VALUES (18, '20240414204108', 49, 2, 1, 20, 159.00, 6, '待收货');
INSERT INTO `x_orders` VALUES (19, '20240414212659', 32, 2, 1, 20, 39.90, 6, '已完成');
INSERT INTO `x_orders` VALUES (20, '20240416162816', 32, 2, 2, 23, 99.80, 10, '待发货');
INSERT INTO `x_orders` VALUES (21, '20240417000713', 35, 2, 4, 26, 275.60, 11, '已评价');
INSERT INTO `x_orders` VALUES (22, '20240417161929', 32, 2, 2, 26, 99.80, 12, '待发货');

-- ----------------------------
-- Table structure for x_role
-- ----------------------------
DROP TABLE IF EXISTS `x_role`;
CREATE TABLE `x_role`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_role
-- ----------------------------
INSERT INTO `x_role` VALUES (1, 'admin', '管理员');
INSERT INTO `x_role` VALUES (2, 'bussiness', '商家');
INSERT INTO `x_role` VALUES (3, 'user', '用户');

-- ----------------------------
-- Table structure for x_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `x_role_menu`;
CREATE TABLE `x_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL,
  `menu_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  INDEX `menu_id`(`menu_id` ASC) USING BTREE,
  CONSTRAINT `menu_id` FOREIGN KEY (`menu_id`) REFERENCES `x_menu` (`menu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `x_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_role_menu
-- ----------------------------
INSERT INTO `x_role_menu` VALUES (11, 1, 1);
INSERT INTO `x_role_menu` VALUES (12, 1, 2);
INSERT INTO `x_role_menu` VALUES (13, 1, 3);
INSERT INTO `x_role_menu` VALUES (52, 3, 1);
INSERT INTO `x_role_menu` VALUES (53, 3, 2);
INSERT INTO `x_role_menu` VALUES (58, 3, 3);
INSERT INTO `x_role_menu` VALUES (67, 1, 21);
INSERT INTO `x_role_menu` VALUES (68, 1, 22);
INSERT INTO `x_role_menu` VALUES (69, 1, 23);
INSERT INTO `x_role_menu` VALUES (73, 3, 21);
INSERT INTO `x_role_menu` VALUES (74, 3, 22);
INSERT INTO `x_role_menu` VALUES (75, 3, 23);
INSERT INTO `x_role_menu` VALUES (76, 1, 24);
INSERT INTO `x_role_menu` VALUES (77, 1, 25);
INSERT INTO `x_role_menu` VALUES (78, 1, 26);
INSERT INTO `x_role_menu` VALUES (79, 1, 27);
INSERT INTO `x_role_menu` VALUES (80, 1, 28);
INSERT INTO `x_role_menu` VALUES (81, 1, 29);
INSERT INTO `x_role_menu` VALUES (91, 2, 22);
INSERT INTO `x_role_menu` VALUES (92, 2, 23);
INSERT INTO `x_role_menu` VALUES (94, 2, 27);
INSERT INTO `x_role_menu` VALUES (95, 2, 28);
INSERT INTO `x_role_menu` VALUES (96, 2, 29);
INSERT INTO `x_role_menu` VALUES (97, 2, 21);
INSERT INTO `x_role_menu` VALUES (98, 2, 24);

-- ----------------------------
-- Table structure for x_type
-- ----------------------------
DROP TABLE IF EXISTS `x_type`;
CREATE TABLE `x_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类描述',
  `parent_id` int NULL DEFAULT NULL COMMENT '父节点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_type
-- ----------------------------
INSERT INTO `x_type` VALUES (1, '外套', '男女', 0);
INSERT INTO `x_type` VALUES (2, '上装', '男女', 0);
INSERT INTO `x_type` VALUES (3, '裤装', '男女', 0);
INSERT INTO `x_type` VALUES (4, '内衣', '女', 0);
INSERT INTO `x_type` VALUES (5, '起居系列', '男女', 0);
INSERT INTO `x_type` VALUES (6, '配件', '男女', 0);
INSERT INTO `x_type` VALUES (7, '所有外套', '男女', 1);
INSERT INTO `x_type` VALUES (8, '运动外套', '男女', 1);
INSERT INTO `x_type` VALUES (9, '防晒外套', '男女', 1);
INSERT INTO `x_type` VALUES (10, '保暖外套', '男女', 1);
INSERT INTO `x_type` VALUES (11, '摇粒绒', '男女', 1);
INSERT INTO `x_type` VALUES (12, '棉服', '男女', 1);
INSERT INTO `x_type` VALUES (13, '所有上装', '男女', 2);
INSERT INTO `x_type` VALUES (14, '卫衣', '男女', 2);
INSERT INTO `x_type` VALUES (15, 'UT', '男女', 2);
INSERT INTO `x_type` VALUES (16, 'T恤', '男女', 2);
INSERT INTO `x_type` VALUES (17, 'POLO', '男女', 2);
INSERT INTO `x_type` VALUES (18, '衬衫', '男女', 2);
INSERT INTO `x_type` VALUES (19, '针织衫', '男女', 2);
INSERT INTO `x_type` VALUES (20, '裙装', '女', 3);
INSERT INTO `x_type` VALUES (21, '运动裤', '男女', 3);
INSERT INTO `x_type` VALUES (22, '牛仔裤', '男女', 3);
INSERT INTO `x_type` VALUES (23, '内衣', '女', 4);
INSERT INTO `x_type` VALUES (24, '少女文胸', '女', 4);
INSERT INTO `x_type` VALUES (25, '吊带', '女', 4);
INSERT INTO `x_type` VALUES (26, '内裤', '女', 4);
INSERT INTO `x_type` VALUES (27, '所有起居', '男女', 5);
INSERT INTO `x_type` VALUES (28, '起居裤', '男女', 5);
INSERT INTO `x_type` VALUES (29, '起居套装', '男女', 5);
INSERT INTO `x_type` VALUES (30, '袜子', '男女', 5);
INSERT INTO `x_type` VALUES (31, '所有配件', '男女', 6);
INSERT INTO `x_type` VALUES (32, '帽子·手套', '男女', 6);
INSERT INTO `x_type` VALUES (33, '短裤', '男', 3);
INSERT INTO `x_type` VALUES (34, '内裤', '男', 5);

-- ----------------------------
-- Table structure for x_user
-- ----------------------------
DROP TABLE IF EXISTS `x_user`;
CREATE TABLE `x_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deleted` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_user
-- ----------------------------
INSERT INTO `x_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'super@aliyun.com', '18677778889', 1, 'https://boluo12138.oss-cn-beijing.aliyuncs.com/ChildrenClothingSalesSystem/avatar/1698155465247-%E6%9F%B4%E7%8A%AC.jpeg', 0, '2023-08-08 17:36:26', 'admin123');
INSERT INTO `x_user` VALUES (2, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', 'zhangsan@gmail.com', '13966667777', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, '2023-08-08 17:36:26', 'zhangsan');
INSERT INTO `x_user` VALUES (3, 'lisi', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'lisi@gmail.com', '13966667778', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, 'lisi');
INSERT INTO `x_user` VALUES (4, 'wangwu', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'wangwu@gmail.com', '13966667772', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, 'test');
INSERT INTO `x_user` VALUES (5, 'zhaoer', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'zhaoer@gmail.com', '13966667776', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, 'test');
INSERT INTO `x_user` VALUES (6, 'songliu', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'songliu@gmail.com', '13966667771', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, 'test');
INSERT INTO `x_user` VALUES (7, 'aaa', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'aaa@aliyun.com', '18899998888', 1, NULL, 1, NULL, 'test');
INSERT INTO `x_user` VALUES (8, 'bbb', 'e10adc3949ba59abbe56e057f20f883e', 'bbb@qq.com', '188888889999', 1, NULL, 1, NULL, 'test');
INSERT INTO `x_user` VALUES (9, 'ccc', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', '1111@ali.com', '11111111', 1, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (10, 'xxx222', '$2a$10$dQ1n957Q3qP2D0lHBSqdEeRHym9kAauXgxnIqAv9KDhnsJOC0Kooi', '1122@qq.com', '111111122', 0, NULL, 1, NULL, 'test');
INSERT INTO `x_user` VALUES (11, 'yyy', '$2a$10$hX03AhH9Yt//V.crSdqGP.nhj3EQi.cMwIEglT9Eqd.5KDkIGaCQC', '1111111@ali.com', '1111111', 1, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (12, 'xxx6', '$2a$10$LTtlLYIPtDelUtP01FhcSe9JZkNuJmeXf66YMJyZee.04Q1yzNoj.', 'xxx6@aliyun.com', '18677778886', 1, NULL, 1, NULL, 'test');
INSERT INTO `x_user` VALUES (13, 'test1', '$2a$10$jWewP9UNr.6AhrP2ofza1OaTDBq7nBL23C9zHnwBXh8Q/9Vd2zNSC', 'test@ali.com', '10086', 1, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (14, 'xxx', '$2a$10$QpmItVrXHa5YWTfH25yCEuTu69VFjJbcxBxMYQTWb8qVNBSp0laxq', 'xxx@ali.com', '1111111111111', 1, NULL, 1, NULL, 'test');
INSERT INTO `x_user` VALUES (15, 'twh', '0ef6a0185dbfb0fbd8cea2c7eaa0d2a0', '2982681306@qq.com', NULL, NULL, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (16, 'bwh', 'dfeb3b7ba315eb9cc86dcb82cf9233d2', '2982681306@qq.com', NULL, NULL, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (17, 'atm', '32a347a83a0398706bfeb4ab52dc27fc', '2982681306@qq.com', NULL, NULL, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (18, 'zjm', '4b3b98720cacd0bab50e1fcca7f1e0cd', '2982681306@qq.com', NULL, 1, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (19, 'zjm1', '4b3b98720cacd0bab50e1fcca7f1e0cd', '2982681306@qq.com', NULL, 1, NULL, 0, NULL, 'test');
INSERT INTO `x_user` VALUES (20, 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '2982681306@qq.com', '1008611', 1, 'https://boluo12138.oss-cn-beijing.aliyuncs.com/ChildrenClothingSalesSystem/avatar/3.jpg', 0, NULL, 'wangwu');
INSERT INTO `x_user` VALUES (21, 'bwh2', 'dfeb3b7ba315eb9cc86dcb82cf9233d2', '2982681306@qq.com', '10086', 1, NULL, 1, '2023-08-14 20:11:21', 'wusong');
INSERT INTO `x_user` VALUES (22, 'wusong', '$2a$10$FL7qxSVaTKvrWbq.vdJQx.a2EOHueIWCKm78hf9p9XaSCN6C20fem', '123@qq.com', '123456', 1, NULL, 0, NULL, 'wusong');
INSERT INTO `x_user` VALUES (23, 'test', 'dcc62e31c8030d9488969c5da73d2f16', '2982681306@qq.com', NULL, 1, NULL, 0, '2024-04-16 15:39:34', 'test');
INSERT INTO `x_user` VALUES (24, 'test2', '$2a$10$5ulcwIzw5pG.T3iQAdIzzez.Uoa4yaPCROEXeUzhu2TbJPirzQV0G', '2982681306@qq.com', '11111', 1, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (26, 'test3', 'e10adc3949ba59abbe56e057f20f883e', '11@qq.com', '111111', 1, NULL, 0, NULL, 'test3');
INSERT INTO `x_user` VALUES (27, 'zxb', '6a4639f57d7572e2b0ab6ca30222936d', '2982681306@qq.com', NULL, 1, NULL, 0, '2024-04-17 16:25:15', 'zxb');

-- ----------------------------
-- Table structure for x_user_role
-- ----------------------------
DROP TABLE IF EXISTS `x_user_role`;
CREATE TABLE `x_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `x_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `x_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `x_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `x_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_user_role
-- ----------------------------
INSERT INTO `x_user_role` VALUES (8, 4, 3);
INSERT INTO `x_user_role` VALUES (88, 19, 1);
INSERT INTO `x_user_role` VALUES (100, 2, 2);
INSERT INTO `x_user_role` VALUES (110, 20, 3);
INSERT INTO `x_user_role` VALUES (111, 13, 3);
INSERT INTO `x_user_role` VALUES (112, 22, 3);
INSERT INTO `x_user_role` VALUES (113, 23, 3);
INSERT INTO `x_user_role` VALUES (116, 24, 3);
INSERT INTO `x_user_role` VALUES (117, 1, 1);
INSERT INTO `x_user_role` VALUES (119, 26, 3);
INSERT INTO `x_user_role` VALUES (120, 27, 3);

SET FOREIGN_KEY_CHECKS = 1;
