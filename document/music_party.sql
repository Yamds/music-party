/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : music_party

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 26/05/2025 18:09:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bili_config
-- ----------------------------
DROP TABLE IF EXISTS `bili_config`;
CREATE TABLE `bili_config`  (
  `cookie_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cookie_context` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`cookie_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bili_config
-- ----------------------------
INSERT INTO `bili_config` VALUES ('buvid3', 'D3FCB6CA-EB41-6072-9A07-483AA245E68B93687infoc');
INSERT INTO `bili_config` VALUES ('SESSDATA', 'ef1d5e87,1763747580,073fd*51CjDlPzPH7rmlsLIAOgJYK28mGWGLN9RTirDBd9EWsiC6pc7DxDmogC3dlKwQ6VgLdEYSVm1xUzZBQllCVkhYMG95bDgzRlZJclpmc0VEWjNqcnVubmpPLVV3a0NRZXVWcE5ZdHh4b1AxYjNISTFlREdzaGhIS0pvd2s2c0JvYUhkVWRpSlB1TTR3IIEC');

-- ----------------------------
-- Table structure for biliuser
-- ----------------------------
DROP TABLE IF EXISTS `biliuser`;
CREATE TABLE `biliuser`  (
  `bili_id` bigint NOT NULL,
  `bili_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bili_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bili_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of biliuser
-- ----------------------------
INSERT INTO `biliuser` VALUES (546195, '老番茄', '//i0.hdslb.com/bfs/face/bc5ca101313d4db223c395d64779e76eb3482d60.jpg');
INSERT INTO `biliuser` VALUES (220675708, 'Yamds', '//i0.hdslb.com/bfs/face/67500e3a48f1380bb72512f7ca7d8475f1f8b555.jpg');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int NOT NULL COMMENT '权限id',
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名字',
  `permission_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'user:info', '用户获取信息');
INSERT INTO `permission` VALUES (2, 'user:logout', '用户退出');
INSERT INTO `permission` VALUES (3, 'user:edit', '用户编辑');
INSERT INTO `permission` VALUES (4, 'user:bind', '账号信息绑定菜单');
INSERT INTO `permission` VALUES (5, 'user:room', '房间系统');
INSERT INTO `permission` VALUES (6, 'admin:room_create', '创建房间');
INSERT INTO `permission` VALUES (7, 'admin:room_delete', '删除房间');
INSERT INTO `permission` VALUES (8, 'master:backstage', '后台');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint NOT NULL COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名字',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色描述',
  `delete_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标识(0正常，1删除)',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'master', '群主', 0);
INSERT INTO `role` VALUES (2, 'admin', '管理员', 0);
INSERT INTO `role` VALUES (3, 'user', '普通用户', 0);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` int NOT NULL COMMENT '角色id',
  `permission_id` int NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (1, 5);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (2, 2);
INSERT INTO `role_permission` VALUES (2, 3);
INSERT INTO `role_permission` VALUES (2, 4);
INSERT INTO `role_permission` VALUES (2, 5);
INSERT INTO `role_permission` VALUES (2, 6);
INSERT INTO `role_permission` VALUES (2, 7);
INSERT INTO `role_permission` VALUES (3, 1);
INSERT INTO `role_permission` VALUES (3, 2);
INSERT INTO `role_permission` VALUES (3, 3);
INSERT INTO `role_permission` VALUES (3, 4);
INSERT INTO `role_permission` VALUES (3, 5);
INSERT INTO `role_permission` VALUES (1, 6);
INSERT INTO `role_permission` VALUES (1, 7);
INSERT INTO `role_permission` VALUES (1, 8);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `netease_id` bigint NOT NULL DEFAULT -1 COMMENT '网易云音乐绑定的id',
  `bili_id` bigint NOT NULL DEFAULT -1 COMMENT 'b站绑定的id',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '启用状态(0正常， 1禁用)',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_flag` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0正常，1删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1924466681585905667 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1916767788031279106, 'Yamds', '$2a$10$d9dBNXVLhP9hxbWpo7Dvfew.D6p6AfwoO7aQhZDQPxeAAFM8/Q4Sa', -1, 220675708, 0, 'system', 'system', '2025-05-26 17:44:37', '2025-05-26 17:44:37', 0);
INSERT INTO `user` VALUES (1924373523158044673, 'asd', '$2a$10$mjQyUEjtwe0HNCCZLPwq.O2oTlhmMRXrSN7iVMiaMFkR/R/U75Uoa', -1, -1, 0, NULL, NULL, '2025-05-19 15:56:16', '2025-05-19 15:56:16', 0);
INSERT INTO `user` VALUES (1924374867612831745, 'Naxiyo', '$2a$10$6WhHbIO0EemNyAKhQxiktOEOYQPu.3PJ4h2E4zC1889xsJNVwcARe', -1, -1, 0, NULL, NULL, '2025-05-19 16:01:36', '2025-05-19 16:01:36', 0);
INSERT INTO `user` VALUES (1924466681585905666, 'kuuhaku', '$2a$10$DoISkIZC2V69XDHu/YWyXuaHhQWE3bpPhaz0R/J/OVJrgO3sAWlA2', -1, -1, 0, NULL, NULL, '2025-05-19 22:06:26', '2025-05-19 22:06:26', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1916767788031279106, 1);
INSERT INTO `user_role` VALUES (1924373523158044673, 3);
INSERT INTO `user_role` VALUES (1924374867612831745, 3);
INSERT INTO `user_role` VALUES (1924466681585905666, 3);

SET FOREIGN_KEY_CHECKS = 1;
