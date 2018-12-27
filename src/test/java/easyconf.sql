/*

Date: 2018-12-26 15:17:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '所属工程id',
  `env_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '环境名称，建议英文',
  `env_type_id` bigint(20) NOT NULL COMMENT '所属环境类型id',
  `content` text COLLATE utf8_bin,
  `last_modify_uid` bigint(20) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` smallint(6) NOT NULL DEFAULT '0' COMMENT '1 删除, 0 未删除',
  `creator_uid` bigint(20) DEFAULT NULL COMMENT '创建者uid',
  `format` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '配置内容格式 properties , yml等',
  `version` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of config
-- ----------------------------

-- ----------------------------
-- Table structure for config_history
-- ----------------------------
DROP TABLE IF EXISTS `config_history`;
CREATE TABLE `config_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_id` bigint(20) NOT NULL COMMENT '配置表的id',
  `version` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '版本号',
  `content` text COLLATE utf8_bin,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of config_history
-- ----------------------------

-- ----------------------------
-- Table structure for config_user
-- ----------------------------
DROP TABLE IF EXISTS `config_user`;
CREATE TABLE `config_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'MD5加密',
  `fullname` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `is_revise` smallint(6) DEFAULT NULL COMMENT '0: 初始化密码   1: 修改后密码',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱地址，接收验证码等',
  `is_admin` smallint(6) NOT NULL DEFAULT '0' COMMENT '1是管理员，0不是管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of config_user
-- ----------------------------
INSERT INTO `config_user` VALUES ('1', 'admin', '96e79218965eb72c92a549dd5a330112', '系统管理员', null, null, '0', '253187898@qq.com', '1');
-- ----------------------------
-- Table structure for env_type
-- ----------------------------
DROP TABLE IF EXISTS `env_type`;
CREATE TABLE `env_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `public_flag` smallint(6) NOT NULL DEFAULT '1' COMMENT '1开放环境，0机密环境',
  `secret` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '获取环境配置需要的安全密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of env_type
-- ----------------------------
INSERT INTO `env_type` VALUES ('7', '开发', '1', null);
INSERT INTO `env_type` VALUES ('8', '测试', '1', null);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '创作者id',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '工程英文名称',
  `owner` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '负责人',
  `contact` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '负责人联系方式',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '工程说明',
  `label` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '标签信息，可用于分组查询',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of project
-- ----------------------------

-- ----------------------------
-- Table structure for project_member
-- ----------------------------
DROP TABLE IF EXISTS `project_member`;
CREATE TABLE `project_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `project_id` bigint(20) NOT NULL COMMENT '工程id',
  `is_creator` smallint(6) NOT NULL DEFAULT '0' COMMENT '1 创建者，0 非创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of project_member
-- ----------------------------