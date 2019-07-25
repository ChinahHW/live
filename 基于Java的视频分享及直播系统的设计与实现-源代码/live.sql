/*
Navicat MySQL Data Transfer

Source Server         : 123
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : live

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2019-05-09 11:29:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `videoid` int(11) DEFAULT NULL,
  `isAlive` int(11) DEFAULT '1' COMMENT '数据是否有效',
  PRIMARY KEY (`id`),
  KEY `col_uid` (`userid`),
  KEY `col_mid` (`videoid`),
  CONSTRAINT `collection_ibfk_1` FOREIGN KEY (`videoid`) REFERENCES `video` (`id`),
  CONSTRAINT `collection_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('33', '2', '5', '1');
INSERT INTO `collection` VALUES ('34', '1', '6', '1');
INSERT INTO `collection` VALUES ('35', '1', '5', '1');
INSERT INTO `collection` VALUES ('36', '1', '4', '1');
INSERT INTO `collection` VALUES ('37', '1', '1', '1');
INSERT INTO `collection` VALUES ('38', '2', '6', '1');
INSERT INTO `collection` VALUES ('39', '11', '3', '1');
INSERT INTO `collection` VALUES ('40', '4', '6', '1');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `videoid` int(11) DEFAULT NULL,
  `agree` int(11) DEFAULT '0',
  `disagree` int(255) DEFAULT '0',
  `isAlive` int(11) DEFAULT '1' COMMENT '数据是否有效',
  PRIMARY KEY (`id`),
  KEY `c_uid` (`userid`),
  KEY `c_mid` (`videoid`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`videoid`) REFERENCES `video` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '这部电影不错，适合大家闲暇时观看', '2', '1', '211', '20', '1');
INSERT INTO `comment` VALUES ('2', '可以的', '3', '1', '80', '1', '1');
INSERT INTO `comment` VALUES ('3', 'tom send to jerry善于搜索的人生，简直就像开挂', '1', '1', '51', '0', '1');
INSERT INTO `comment` VALUES ('4', 'tom send to tom同样是九年义务教务，为何你如此优秀', '1', '1', '5', '0', '1');
INSERT INTO `comment` VALUES ('5', 'tom send to jerry有木有wifi，有木有wifi，有而且耐用5毛嘞', '1', '1', '0', '0', '1');
INSERT INTO `comment` VALUES ('6', 'tom send to jerry优秀', '1', '1', '0', '0', '1');
INSERT INTO `comment` VALUES ('7', 'tom send to jerry : 停车，这不是去幼儿园的车', '1', '1', '0', '0', '1');
INSERT INTO `comment` VALUES ('8', 'jerry : 真好看！', '2', '4', '0', '0', '1');
INSERT INTO `comment` VALUES ('9', 'tom : 优秀如你', '1', '6', '2', '0', '1');
INSERT INTO `comment` VALUES ('10', 'tom send to tom : 我回复我自己', '1', '6', '1', '0', '1');
INSERT INTO `comment` VALUES ('11', 'tom send to jerry : 滴，老司机卡', '1', '1', '0', '0', '1');
INSERT INTO `comment` VALUES ('12', 'hua send to tom : 有时候，感觉身体被掏空', '3', '1', '0', '0', '1');
INSERT INTO `comment` VALUES ('13', 'hua : 嘀，老司机卡', '3', '3', '6', '0', '1');
INSERT INTO `comment` VALUES ('14', 'hua send to hua : 同志，这是去幼儿园的车', '3', '3', '0', '0', '1');
INSERT INTO `comment` VALUES ('15', 'huwei send to hua : 请问秋名山怎么走？', '11', '3', '1', '0', '1');
INSERT INTO `comment` VALUES ('16', 'jerry send to Tom : 老司机，带带我', '2', '6', '0', '0', '1');
INSERT INTO `comment` VALUES ('17', 'Enter Name : 放我下车，这不是去幼儿园的车', '2', '5', '1', '0', '1');
INSERT INTO `comment` VALUES ('18', 'king send to tom : 不错，是部好片', '4', '6', '0', '0', '1');
INSERT INTO `comment` VALUES ('19', 'Enter Name : ', '4', '6', '0', '0', '1');
INSERT INTO `comment` VALUES ('20', 'wer', '1', '5', '0', '0', '1');
INSERT INTO `comment` VALUES ('21', '2432134', '1', '5', '0', '0', '1');
INSERT INTO `comment` VALUES ('22', '', '1', '5', '0', '0', '1');
INSERT INTO `comment` VALUES ('23', '123213', '1', '5', '0', '0', '1');
INSERT INTO `comment` VALUES ('24', '123123', '1', '5', '0', '0', '1');
INSERT INTO `comment` VALUES ('25', '222222222222222222', '1', '5', '0', '0', '1');
INSERT INTO `comment` VALUES ('26', '123123', '1', '5', '0', '0', '1');
INSERT INTO `comment` VALUES ('28', 'root send to hua : 123123', '1', '1', '0', '0', '1');

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `friendid` int(11) DEFAULT NULL,
  `groupname` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `isAlive` int(11) DEFAULT '1' COMMENT '数据是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('2', '1', '4', '同学', null, '1');
INSERT INTO `friend` VALUES ('3', '2', '1', null, null, '1');
INSERT INTO `friend` VALUES ('13', '1', '3', null, null, '1');
INSERT INTO `friend` VALUES ('14', '1', '2', null, null, '1');
INSERT INTO `friend` VALUES ('15', '11', '3', null, null, '1');
INSERT INTO `friend` VALUES ('16', '2', '11', null, null, '1');
INSERT INTO `friend` VALUES ('40', '4', '2', null, null, '1');
INSERT INTO `friend` VALUES ('41', '1', '1', null, null, '0');
INSERT INTO `friend` VALUES ('43', '1', '11', null, null, '1');
INSERT INTO `friend` VALUES ('44', '1', '6', null, null, '1');

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `videoid` int(11) DEFAULT NULL,
  `isAlive` int(11) DEFAULT '1' COMMENT '数据是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history
-- ----------------------------
INSERT INTO `history` VALUES ('2', '1', '1', '1');
INSERT INTO `history` VALUES ('3', '2', '1', '1');
INSERT INTO `history` VALUES ('4', '2', '2', '1');
INSERT INTO `history` VALUES ('5', '3', '1', '1');
INSERT INTO `history` VALUES ('6', '1', '3', '1');
INSERT INTO `history` VALUES ('7', '2', '3', '1');
INSERT INTO `history` VALUES ('10', '6', '1', '1');
INSERT INTO `history` VALUES ('17', '2', '4', '1');
INSERT INTO `history` VALUES ('21', '1', '5', '1');
INSERT INTO `history` VALUES ('22', '1', '6', '1');
INSERT INTO `history` VALUES ('24', '4', '3', '1');
INSERT INTO `history` VALUES ('25', '3', '3', '1');
INSERT INTO `history` VALUES ('26', '1', '4', '1');
INSERT INTO `history` VALUES ('32', '11', '1', '1');
INSERT INTO `history` VALUES ('37', '2', '5', '1');
INSERT INTO `history` VALUES ('51', '2', null, '1');
INSERT INTO `history` VALUES ('52', '2', null, '1');
INSERT INTO `history` VALUES ('53', '4', '6', '1');
INSERT INTO `history` VALUES ('54', '4', null, '1');
INSERT INTO `history` VALUES ('55', '1', '2', '1');
INSERT INTO `history` VALUES ('56', '1', '12', '1');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `senderid` int(11) DEFAULT NULL,
  `receiverid` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `isAlive` int(11) DEFAULT '1' COMMENT '数据是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', '1', '2', 'test', '1');
INSERT INTO `message` VALUES ('2', '2', '1', 'test2', '1');
INSERT INTO `message` VALUES ('5', '3', '2', '可以的', '1');
INSERT INTO `message` VALUES ('6', '4', '1', '你是猴子派来的逗比么', '1');
INSERT INTO `message` VALUES ('7', '1', '4', '你是猴子派来恶心我的逗比么', '1');
INSERT INTO `message` VALUES ('8', '4', '1', '你是猴子派来的救兵么', '1');
INSERT INTO `message` VALUES ('10', '4', '1', 'test', '1');
INSERT INTO `message` VALUES ('11', '4', '1', 'test2\n', '1');
INSERT INTO `message` VALUES ('12', '4', '1', 'succeess', '1');
INSERT INTO `message` VALUES ('13', '4', '2', 'HelloWorld', '1');
INSERT INTO `message` VALUES ('14', '2', '4', 'helloworld', '1');
INSERT INTO `message` VALUES ('15', '4', '1', 'test3', '1');
INSERT INTO `message` VALUES ('16', '1', '1', 'aaa', '1');
INSERT INTO `message` VALUES ('17', '1', '2', '123123', '1');
INSERT INTO `message` VALUES ('18', '1', '2', '12312', '1');
INSERT INTO `message` VALUES ('19', '1', '2', '12', '1');
INSERT INTO `message` VALUES ('20', '1', '3', '23132123', '1');
INSERT INTO `message` VALUES ('21', '1', '3', '2333', '1');
INSERT INTO `message` VALUES ('22', '1', '6', '213123123', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT 'man',
  `age` int(11) DEFAULT NULL,
  `picture` varchar(255) DEFAULT 'f3.jpg',
  `password` varchar(255) DEFAULT NULL,
  `isAdmin` varchar(255) NOT NULL DEFAULT 'false',
  `isAlive` int(11) DEFAULT '1' COMMENT '数据是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '男', '22', '222.jpg', '123', 'true', '1');
INSERT INTO `user` VALUES ('2', 'Jerry', 'man', '21', 'user1.jpg', '123', 'false', '1');
INSERT INTO `user` VALUES ('3', 'hua', 'woman', '18', 'user3.jpg', '123', 'false', '1');
INSERT INTO `user` VALUES ('4', 'king', 'man', '21', 'user2.jpg', '123', 'false', '1');
INSERT INTO `user` VALUES ('5', 'James', 'man', '26', 'user2.jpg', '123', 'false', '1');
INSERT INTO `user` VALUES ('6', 'Jim', 'man', '19', 'user2.jpg', '123', 'false', '1');
INSERT INTO `user` VALUES ('11', 'huwei', 'man', '22', 'user2.jpg', '123', 'false', '0');
INSERT INTO `user` VALUES ('12', 'huwei', 'man', '22', 'user2.jpg', '123', 'false', '2');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `videopicture` varchar(255) DEFAULT NULL,
  `clazz` varchar(255) DEFAULT NULL,
  `recommend` int(11) DEFAULT '0' COMMENT '受推荐数',
  `time` datetime DEFAULT NULL,
  `isAlive` int(11) DEFAULT '1' COMMENT '数据时候有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('1', '杀戮都市O', '高中生加藤胜（小野大辅 配音）和弟弟过着相依为命的生活，某日，加藤胜所在的地铁站遭到了无差别杀人犯的袭击，为了救人，加藤胜身中数刀不幸身亡，而当他再度睁开双眼之时，却发现自己完好无损的站在一个陌生的房间里，眼前是一个黑色的诡异圆球。 和加藤胜在一起的，还有下平蕾佳（早见沙织 配音）、西丈一郎（郭智博 配音）和铃木良一（池田秀一 配音）三人，他们和加藤胜一样，都是已死之人。蕾佳告诉满头雾水的加藤胜，黑色的圆球名叫“GANTZ”，而他们所要做的，是等待圆球下达的命令。这一次，他们所要面对的是叫做“滑瓢”的怪物', 'http://127.0.0.1:81/1.mp4', '杀戮都市.jpg', '动画，动作，改编', '136', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('2', '蜘蛛侠', '这是内容', 'http://127.0.0.1:81/1.mp4', 'r2.jpg', '动作', '14', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('3', '钢铁侠', '这是内容', 'http://127.0.0.1:81/1.mp4', 'r3.jpg', '动作', '253', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('4', '这个杀手不太冷', '片子并没有获许多大奖，但是这个两个一无所有灵魂爱上彼此的故事，触及到了我们内心深处对于爱这种事物的解读。相爱之后，才彼此完整。看完后，可能都不记得剧情，但是会记得那个木讷的，内心其实停留在孩子的大叔，还有古灵精怪的小萝莉，以及他们之间的那种让人难以忘怀的依靠。', 'http://127.0.0.1:81/1.mp4', 'r5.jpg', '动作', '0', '2018-08-14 17:28:23', '2');
INSERT INTO `video` VALUES ('5', '猩球崛起', '为了能让身患老年痴呆症的父亲重回正常的生活，威尔·罗曼（詹姆斯·弗兰科 James Franco 饰）一直致力于名为“Cure”的基因药物的开发，并在大猩猩身上不断进行着药物试验。在公司已经失去信心的同时，威尔继续在家完成试验，并在一只名为凯撒的大猩猩身上获得成功。借助灵长类学家卡洛琳（芙蕾达·平托 Freida Pinto 饰）的帮助，威尔发现凯撒的智力极大地提高。不过，人类的社会始终无法接受拥有极高智力的凯撒，凯撒被送往了猩猩看护所。', '//player.bilibili.com/player.html?aid=13719656&cid=22439776&page=1', 'r4.jpg', '科幻，动作', '0', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('6', '超能小队', '未来世界的超级都市旧京山（San Fransokyo），热爱发明创造的天才少年小宏，在哥哥泰迪的鼓励下参加了罗伯特·卡拉汉教授主持的理工学院机器人专业的入学大赛。他凭借神奇的微型磁力机器人赢得观众、参赛者以及考官的一致好评，谁知突如其来的灾难却将小宏的梦想和人生毁于一旦。大火烧毁了展示会场，而哥哥为了救出受困的卡拉汉教授命丧火场。身心饱受创伤的小宏闭门不出，哥哥生前留下的治疗型机器人大白则成为安慰他的唯一伙伴。', '//player.bilibili.com/player.html?aid=2086409&cid=3237510&page=1', 'r6.jpg', '动画', '0', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('7', '肖申克的救赎', '该片改编自斯蒂芬·金《四季奇谭》中收录的同名小说，该片中涵盖全片的主题是“希望”，全片透过监狱这一强制剥夺自由、高度强调纪律的特殊背景来展现作为个体的人对“时间流逝、环境改造”的恐惧。', '//player.bilibili.com/player.html?aid=5414334&cid=8801210&page=1', '肖申克的救赎.jpg', '动画', '0', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('8', '阿甘正传', '正如同影片开始和结束时候的羽毛，看起来，人生似乎只是在风中漂浮，但是谁又知道，这自身的努力是否可以改变未来的命运呢？其实一般来说，这种宏大的主题，是只有史诗类作品才会选择的，但影片能用一个人的小故事，讲出史诗般宏大的主题，却又娓娓道来，宏大与细微的交汇，成为一部普通人的日常生活史诗，让人无比动容。', '//player.bilibili.com/player.html?aid=5530290&cid=8983631&page=1', '阿甘正传.jpg', '动作', '0', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('9', '拯救大兵瑞恩', '其实一部失败的战争片，主题很容易陷入到两个极点，一是进入到一种永远正确的没有立场的反战主义，二是被渲染成一种单薄虚假的个人英雄主义，而斯皮尔伯格小心的没有陷入到任何一极。他保持了对战争的冷酷残忍的描述，诚实的表达了暴力对人的伤害，但他也同样赞颂了士兵们在这场反法西斯战争中的努力', '//player.bilibili.com/player.html?aid=17323176&cid=28325200&page=1', '拯救大兵瑞恩.jpg', '动作', '0', '2018-08-14 17:28:23', '1');
INSERT INTO `video` VALUES ('10', '乐高大电影', '艾米特（克里斯·帕拉特 Chris Pratt 配音）是乐高世界中一个普通到没有存在感的建筑师，他每天精神饱满，乐观向上，按照说明书的指示从事一天的活动。某天收工后，他意外掉入一个深洞，后背还黏了奇怪的东西，随后就被兼具凶暴和善良两面的警察（利亚姆·尼森 Liam Neeson 配音）带走问话。原来统治乐高世界的生意王（威尔·法瑞尔 Will Ferrell 配音）对那些极富创造力的小人极为不满，他讨厌不同系列的乐高世界相互交叉.暴风雨般的迫害袭向创意大师们，想象力贫乏的艾米特能否担此重任？', '//player.bilibili.com/player.html?aid=28469462&cid=49258982&page=2', 'r1.jpg', '动画', '0', '2018-08-21 20:30:34', '1');
INSERT INTO `video` VALUES ('12', '我不是药神', '徐峥主演', 'http://127.0.0.1:81/我不是药神.mp4', '我不是药神.jpg', '喜剧', '0', '2019-04-04 20:47:54', '1');
