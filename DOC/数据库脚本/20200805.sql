/*
 Navicat Premium Data Transfer

 Source Server         : 172.168.251.66
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 172.168.251.66:3306
 Source Schema         : elms_anqing_v1.0

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 05/08/2020 15:51:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tt_sys_atta
-- ----------------------------
DROP TABLE IF EXISTS `tt_sys_atta`;
CREATE TABLE `tt_sys_atta`  (
  `OID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件名称',
  `ORIGIN_NAME` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件原始名称',
  `FILE_PATH` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件路径',
  `EXTENSION_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件扩展名',
  `UPLOAD_DATE` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `USER_OID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属用户 : 上传用户的编号',
  `IS_DELETE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除状态',
  `SYN_STATUS` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N',
  PRIMARY KEY (`OID`) USING BTREE,
  INDEX `NAME`(`NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统附件表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
