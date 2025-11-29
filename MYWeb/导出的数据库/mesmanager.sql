/*
 Navicat Premium Dump SQL

 Source Server         : lai
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : mesmanager

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 29/11/2025 17:52:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_commodity_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_commodity_info`;
CREATE TABLE `tb_commodity_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品原产地',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品描述',
  `quantityNum` int NULL DEFAULT NULL COMMENT '库存数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `mailingType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮寄类型',
  `addTime` datetime NULL DEFAULT NULL COMMENT '上架时间',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_commodity_info
-- ----------------------------
INSERT INTO `tb_commodity_info` VALUES (1, '脱骨李子', '四川成都', '好吃不贵，酸酸甜甜脱骨李子', 123, 36.00, '包邮', '2025-11-28 10:30:20', '2025-11-28 10:30:20');
INSERT INTO `tb_commodity_info` VALUES (2, '云南过桥米钱', '云南昆明', '云南过桥米线大桶装速食', 32, 4.50, '不包邮', '2025-11-28 10:30:20', '2025-11-28 10:30:20');
INSERT INTO `tb_commodity_info` VALUES (3, '鸿星尔克防滑鞋', '福建泉州', '特步男鞋夏季2024新款', 678, 200.00, '包邮', '2025-11-28 10:30:20', '2025-11-28 10:30:20');

-- ----------------------------
-- Table structure for tb_pdt_evaluate_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_pdt_evaluate_info`;
CREATE TABLE `tb_pdt_evaluate_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mainId` int NULL DEFAULT NULL COMMENT '主表的主键',
  `userId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `evaluateInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户评价信息',
  `evaluateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_pdt_evaluate_info
-- ----------------------------
INSERT INTO `tb_pdt_evaluate_info` VALUES (1, 4, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (2, 4, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (3, 4, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (4, 4, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (5, 2, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (6, 2, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (7, 3, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (8, 3, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (9, 3, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (10, 1, 'x001', '777', '2025-11-28 10:30:20');
INSERT INTO `tb_pdt_evaluate_info` VALUES (11, 1, 'x001', '777', '2025-11-28 10:30:20');

-- ----------------------------
-- Table structure for tb_product_comment_stats
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_comment_stats`;
CREATE TABLE `tb_product_comment_stats`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` int NOT NULL COMMENT '关联商品ID（关联tb_product_info.id）',
  `good_comment_count` int NOT NULL DEFAULT 0 COMMENT '好评数量',
  `bad_comment_count` int NOT NULL DEFAULT 0 COMMENT '差评数量',
  `total_comment_count` int NOT NULL DEFAULT 0 COMMENT '评价总数（好评+差评）',
  `good_comment_rate` decimal(5, 2) NOT NULL DEFAULT 0.00 COMMENT '好评率（好评数/评价总数，保留两位小数）',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计更新时间（自动更新）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `fk_product_stats` FOREIGN KEY (`product_id`) REFERENCES `tb_product_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品好评与差评数量统计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_comment_stats
-- ----------------------------
INSERT INTO `tb_product_comment_stats` VALUES (1, 1, 5, 0, 5, 100.00, '2025-11-28 10:30:20');
INSERT INTO `tb_product_comment_stats` VALUES (2, 2, 30, 2, 32, 93.75, '2025-11-28 10:30:20');
INSERT INTO `tb_product_comment_stats` VALUES (3, 3, 0, 0, 0, 0.00, '2025-11-28 10:30:20');
INSERT INTO `tb_product_comment_stats` VALUES (4, 4, 0, 0, 0, 0.00, '2025-11-28 10:30:20');
INSERT INTO `tb_product_comment_stats` VALUES (5, 5, 0, 0, 0, 0.00, '2025-11-28 10:30:20');
INSERT INTO `tb_product_comment_stats` VALUES (6, 6, 0, 0, 0, 0.00, '2025-11-28 10:30:20');
INSERT INTO `tb_product_comment_stats` VALUES (8, 9, 0, 0, 0, 0.00, '2025-11-28 16:42:40');
INSERT INTO `tb_product_comment_stats` VALUES (9, 10, 200, 1, 201, 99.50, '2025-11-28 16:43:14');
INSERT INTO `tb_product_comment_stats` VALUES (10, 11, 0, 0, 0, 0.00, '2025-11-28 16:50:04');
INSERT INTO `tb_product_comment_stats` VALUES (11, 12, 0, 0, 0, 0.00, '2025-11-29 16:30:04');
INSERT INTO `tb_product_comment_stats` VALUES (12, 13, 0, 0, 0, 0.00, '2025-11-29 17:10:22');
INSERT INTO `tb_product_comment_stats` VALUES (13, 14, 0, 0, 0, 0.00, '2025-11-29 17:38:13');
INSERT INTO `tb_product_comment_stats` VALUES (14, 15, 0, 0, 0, 0.00, '2025-11-29 17:51:48');
INSERT INTO `tb_product_comment_stats` VALUES (15, 16, 0, 0, 0, 0.00, '2025-11-29 17:52:09');

-- ----------------------------
-- Table structure for tb_product_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_info`;
CREATE TABLE `tb_product_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '货品id,自动递增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货品名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品描述',
  `imgSrc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货品来源',
  `manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源厂家',
  `placeSale` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '销售地',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '货品单价价格',
  `shelfLife` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货品保质期截止日期（格式：年-月）',
  `quantityNum` int NULL DEFAULT NULL COMMENT '库存数量',
  `addTime` datetime NULL DEFAULT NULL COMMENT '上架时间（默认为now()）',
  `publishTime` datetime NULL DEFAULT NULL COMMENT '货品发布时间',
  `publishState` int NULL DEFAULT 0 COMMENT '发布状态（1:已发布；0：未发布；默认为0）',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间（默认为now()）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_info
-- ----------------------------
INSERT INTO `tb_product_info` VALUES (1, '奶油面包', '真的超赞得一款面包', '../images/SpMRimg/MrIMG.gif', '江苏南京', '南京面包企业有限公司', '南京', 12.00, '2年', 12, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 0, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (2, 'AD钙奶', 'ad高钙奶,买东西上摸鱼网', '../images/SpMRimg/MrIMG.gif', '浙江杭州', '杭州娃哈哈集团有限公司', '杭州', 36.00, '2年', 34, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 1, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (3, '美的空调', '这个夏天你值得拥有', '../images/SpMRimg/MrIMG.gif', '广东广州', '美的集团', '广州', 1240.00, '2年', 45, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 0, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (4, '脱骨李子', '好吃不贵，酸酸甜甜脱骨李子', '../images/SpMRimg/MrIMG.gif', '四川成都', '四川鲜果公司', '成都', 32.00, '2年', 1000, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 0, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (5, '云南过桥米钱', '云南过桥米线大桶装速食', '../images/SpMRimg/MrIMG.gif', '云南昆明', '米线公司', '昆明', 13.00, '2年', 20, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 0, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (6, '鸿星尔克防滑鞋', '特步男鞋夏季2024新款', '../images/SpMRimg/MrIMG.gif', '福建泉州', '红星公司', '泉州', 653.00, '2年', 10, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 0, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (9, '123', '123', '../images/SpMRimg/MrIMG.gif', '广东广州', '广东', '广州', 666.00, '2年', 6, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 1, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (10, '666', '666', '../images/SpMRimg/MrIMG.gif', '广东广州', '666', '广州', 666.00, '2年', 666, '2025-11-28 10:30:20', '2025-11-28 10:30:20', 1, '2025-11-28 10:30:20');
INSERT INTO `tb_product_info` VALUES (11, '图片测试', '商品图片上传测试', '../images/productImg/148c94da-fbf0-431c-98d7-58b9da0e7ab8.jpg', '广东广州', '321', '广州', 9.90, '长期', 1, '2025-11-28 16:50:04', '2025-11-28 16:50:04', 1, '2025-11-28 16:50:04');
INSERT INTO `tb_product_info` VALUES (12, '图片测试1', '测试2', '../images/productImg/f68ef5f7-d59f-4e53-b025-721b44e59079.jpg', '广东广州', '111', '广州', 1.00, '', 1, '2025-11-29 16:30:04', '2025-11-29 16:30:04', 1, '2025-11-29 16:30:04');
INSERT INTO `tb_product_info` VALUES (13, '图片测试2', '222', '../images/productImg/4ee3e02e-adb6-4e38-a8a3-91623b59ffb7.jpg', '广东广州', '222', '广州', 2.00, '', 2, '2025-11-29 17:10:21', '2025-11-29 17:10:21', 1, '2025-11-29 17:10:21');
INSERT INTO `tb_product_info` VALUES (14, '图片测试3', '333', '../images/productImg/232431e9-b027-4ef3-8a88-90cbdbb05cdf.jpg', '广东广州', '333', '广州', 3.00, '长期', 3, '2025-11-29 17:38:13', '2025-11-29 17:38:13', 1, '2025-11-29 17:38:13');
INSERT INTO `tb_product_info` VALUES (15, '233', '233', '../images/SpMRimg/MrIMG.gif', '233', '233', '233', 233.00, '长期', 233, '2025-11-29 17:51:48', '2025-11-29 17:51:48', 1, '2025-11-29 17:51:48');
INSERT INTO `tb_product_info` VALUES (16, '33', '33', NULL, '33', '33', '33', 33.00, '长期', 33, '2025-11-29 17:52:09', '2025-11-29 17:52:09', 1, '2025-11-29 17:52:09');

-- ----------------------------
-- Table structure for tb_sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_admin`;
CREATE TABLE `tb_sys_admin`  (
  `loginId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员登录账号（主键）',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录密码（建议加密存储）',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建时间（自动填充）',
  PRIMARY KEY (`loginId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '网站后台管理员账号密码表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sys_admin
-- ----------------------------
INSERT INTO `tb_sys_admin` VALUES ('Lairoot', 'Lairoot', '2025-11-27 10:24:09');

-- ----------------------------
-- Table structure for tb_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user`;
CREATE TABLE `tb_sys_user`  (
  `loginId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录密码',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`loginId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sys_user
-- ----------------------------
INSERT INTO `tb_sys_user` VALUES ('123456', '123456', '2025-11-26 16:47:57');

-- ----------------------------
-- Table structure for tb_sys_user_staff
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user_staff`;
CREATE TABLE `tb_sys_user_staff`  (
  `loginId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `userImgSrc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`loginId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sys_user_staff
-- ----------------------------
INSERT INTO `tb_sys_user_staff` VALUES ('123456', 'Lai', '../images/userImg/x001.png', '1780000000', '11116789@qq.com', '2025-11-28 10:30:20');

-- ----------------------------
-- Triggers structure for table tb_product_comment_stats
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_insert_comment_stats`;
delimiter ;;
CREATE TRIGGER `trg_insert_comment_stats` BEFORE INSERT ON `tb_product_comment_stats` FOR EACH ROW BEGIN
  SET NEW.`total_comment_count` = NEW.`good_comment_count` + NEW.`bad_comment_count`;
  -- 处理除数为0的情况（避免报错）
  IF NEW.`total_comment_count` = 0 THEN
    SET NEW.`good_comment_rate` = 0.00;
  ELSE
    SET NEW.`good_comment_rate` = ROUND((NEW.`good_comment_count` / NEW.`total_comment_count`) * 100, 2);
  END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_product_comment_stats
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_update_comment_stats`;
delimiter ;;
CREATE TRIGGER `trg_update_comment_stats` BEFORE UPDATE ON `tb_product_comment_stats` FOR EACH ROW BEGIN
  SET NEW.`total_comment_count` = NEW.`good_comment_count` + NEW.`bad_comment_count`;
  IF NEW.`total_comment_count` = 0 THEN
    SET NEW.`good_comment_rate` = 0.00;
  ELSE
    SET NEW.`good_comment_rate` = ROUND((NEW.`good_comment_count` / NEW.`total_comment_count`) * 100, 2);
  END IF;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
