/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.17 : Database - pick
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pick` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pick`;

/*Table structure for table `attention` */

DROP TABLE IF EXISTS `attention`;

CREATE TABLE `attention` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `topic_id` int(11) DEFAULT NULL COMMENT '帖子 id',
  `user_id` int(11) DEFAULT NULL COMMENT '关注者 id',
  `create_time` datetime DEFAULT NULL COMMENT '关注时间，用于排序',
  PRIMARY KEY (`id`),
  KEY `fk_attention_topic_id` (`topic_id`),
  KEY `fk_attention_user_id` (`user_id`),
  CONSTRAINT `fk_attention_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_attention_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户关注帖子表';

/*Data for the table `attention` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cate_name` varchar(50) DEFAULT NULL COMMENT '分类名',
  `cate_icon` varchar(500) DEFAULT NULL COMMENT '分类图标',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`cate_name`,`cate_icon`,`sort`) values (1,'全部','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/all.png',1),(2,'生活','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/live.png',2),(3,'服装','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/fuzhuang.png',3),(4,'食品','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/food.png',4),(5,'数码','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/shuma.png',5),(6,'书籍','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/shuji.png',6),(7,'电器','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/dianqi.png',7),(8,'家具','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/jiaju.png',8),(9,'美妆','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/meizhuang.png',9),(10,'装饰品','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/zhuangshi.png',10),(11,'其他','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/admin/icons/qita.png',11);

/*Table structure for table `collect` */

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `commodity_id` int(11) DEFAULT NULL COMMENT '收藏的商品Id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户 id',
  `create_time` datetime DEFAULT NULL COMMENT '收藏时间，用于排序',
  PRIMARY KEY (`id`),
  KEY `fk_collect_commodity_id` (`commodity_id`),
  KEY `fk_collect_user_id` (`user_id`),
  CONSTRAINT `fk_collect_commodity_id` FOREIGN KEY (`commodity_id`) REFERENCES `commodity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_collect_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='用户收藏表';

/*Data for the table `collect` */

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论表主键id',
  `content` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `topic_id` int(11) DEFAULT NULL COMMENT '话题id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `publish_date` datetime DEFAULT NULL COMMENT '评论时间',
  `state` int(2) DEFAULT NULL COMMENT '0，删除状态；1，显示中',
  PRIMARY KEY (`id`),
  KEY `fk_comment_user_id` (`user_id`),
  KEY `fk_comment_topic_id` (`topic_id`),
  CONSTRAINT `fk_comment_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='评论表';

/*Data for the table `comment` */

insert  into `comment`(`id`,`content`,`topic_id`,`user_id`,`publish_date`,`state`) values (13,'小贱来的评论',1,11,'2020-11-26 22:29:42',1),(14,'小贱，留下评论',25,11,'2020-11-26 22:32:04',1),(15,'厉害了，我的歌',4,11,'2020-12-02 10:51:54',1),(16,'黄色闪光',4,11,'2020-12-02 10:56:23',1);

/*Table structure for table `commodity` */

DROP TABLE IF EXISTS `commodity`;

CREATE TABLE `commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) DEFAULT NULL COMMENT '商品标题',
  `category_id` int(11) DEFAULT NULL COMMENT '商品分类Id',
  `old_price` double DEFAULT NULL COMMENT '原价',
  `price` double DEFAULT NULL COMMENT '现价',
  `quality` varchar(100) DEFAULT NULL COMMENT '品质（九成新，使用时长）',
  `repertory` int(11) DEFAULT NULL COMMENT '库存数量',
  `click_count` int(11) DEFAULT NULL COMMENT '浏览量',
  `collect_count` int(11) DEFAULT NULL COMMENT '收藏数',
  `description` text COMMENT '商品描述',
  `user_id` int(11) DEFAULT NULL COMMENT '发布者Id',
  `state` int(11) DEFAULT NULL COMMENT '商品状态（1，出售中；2，售罄；3，下架；4，该商品已删除，保留文本内容）',
  `publish_date` datetime DEFAULT NULL COMMENT '发布时间',
  `cover_image` varchar(200) DEFAULT NULL COMMENT '商品封面图',
  `serial` varchar(50) DEFAULT NULL COMMENT '商品编号',
  `swiper` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为轮播图商品',
  PRIMARY KEY (`id`),
  KEY `fk_commodity_user_id` (`user_id`),
  KEY `fk_commodity_category_id` (`category_id`),
  CONSTRAINT `fk_commodity_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_commodity_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='商品表';

/*Data for the table `commodity` */

insert  into `commodity`(`id`,`title`,`category_id`,`old_price`,`price`,`quality`,`repertory`,`click_count`,`collect_count`,`description`,`user_id`,`state`,`publish_date`,`cover_image`,`serial`,`swiper`) values (6,'添加商品',4,199,2200,'八成吧',3,151,12,'    提前了            ',1,1,'2020-10-22 20:31:23','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603369888585.png','45a1be77-e883-40b9-84d9-a28a334e2530','\0'),(7,'测试商品id',5,199,300,'七成，不能再多了',12,12,0,'          体无完肤    ',1,1,'2020-10-22 20:33:14','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603369996164.png','e3da72cf-9eca-43a4-8094-cc26c53199ab','\0'),(9,'销售销售',5,884,89,'九成新',3,127,0,'三张壁纸                ',1,1,'2020-11-02 11:38:11','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201102/1604288298582.jpg','5baf5ddf-8169-448f-8ae2-7af89ab4b994','\0'),(27,'测试成功页面',4,42,34,'阿萨德',34,1,0,'风格',1,1,'2020-11-04 18:31:59','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201104/1604485922313.png','1f2c8c51-942c-4d3c-a764-bd436ee01f88','\0'),(30,'测试回退页面2',4,234,2,'阿道夫',3,3,0,'发电房',11,1,'2020-11-04 20:16:45','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201104/1604492208706.png','6bfe9e90-a3ac-471c-8c19-274cbd88fd2e','\0'),(36,'小程序发布-1',4,122,32,'发电工',3,0,0,'营业厅人士',11,1,'2020-11-09 00:49:05','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854147584.png','8a6b5ac3-c17e-40c6-be86-fa347ad3f619','\0'),(37,'哪里的小程序发布了商品',4,21,12,'胡歌',3,0,0,'地方',11,1,'2020-11-09 00:49:39','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854186028.ico','afa61009-6ab9-46c0-b91c-1e471f0827c7','\0'),(38,'就是这里的小程序发布的商品',5,54,43,'1',12,0,0,'和规范化',11,1,'2020-11-09 00:50:19','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854220546.ico','d9a4325e-71cc-40b5-a8a7-adbf44478b32','\0'),(39,'小程序',11,21,12,'染发膏',3,0,0,'拖后腿',11,1,'2020-11-09 00:50:42','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854244395.ico','37cc4965-3c38-4a0b-9b2b-3344096440dd','\0'),(40,'有特么是小程序',5,32,12,'苟富贵',4,0,0,'涂鸦跳跃',11,1,'2020-11-09 00:53:59','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854445599.ico','c70367f6-d58e-4b62-a4f5-001525285c71','\0'),(41,'超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了',5,32,12,'阿萨德',1,0,0,'阿道夫',11,1,'2020-11-09 03:04:07','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604862252355.png','fba58c9e-66e6-46b1-9833-9557b6015878','\0'),(42,'超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了超过了100字数了',5,32,12,'佛挡杀佛',1,0,0,'阿道夫',11,1,'2020-11-09 03:05:11','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604862320957.png','29c4a444-6210-4ba6-8ee0-dc08973f7df5','\0'),(44,'测试轮播图',6,3224,123,'八成',32,4,0,'                放单费',1,1,'2020-11-09 10:29:15','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604888955230.jpg','1db8ba9d-2eb2-4b5a-bd14-d4bee5c9ba3b','\0'),(45,'测试轮播图2.0',6,43,12,'七成',3,3,0,'                发斯蒂芬',1,1,'2020-11-09 10:30:07','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604889016139.jpeg','ed48a27c-68f3-425d-9a9e-9932b946bc81','\0'),(53,'测试定时任务删除商品',5,645,32,'九成新',3,17,0,'的烦恼鬼积分卡',1,1,'2020-11-17 08:51:54','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201117/1605574314490.jpg','f0461b16-1787-4e0a-8300-0e1fa23624a0','\0'),(54,'测试定时任务删除商品，有图片',11,434,123,'三天前买的',32,1,0,'用来测试定时任务删除商品',1,1,'2020-10-24 21:50:07','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312214375.png','5df1645c-b3cd-4dc1-87b6-8385e8513c51','\0'),(55,'测试定时任务删除，一张图片',3,5434,434,'赴敌甘负戈',32,4,0,'韶关市分公司梵蒂冈',1,1,'2020-10-24 21:51:03','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312269815.png','f42baf7a-4b56-4bb3-820d-a03128dc0996','\0'),(56,'手机测试定时下架商品',4,434,323,'九成新',2,0,0,'无非是广泛的',11,1,'2020-10-24 21:58:04','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312692418.ico','23b54ad4-a25d-4939-a29f-e07c26e08b2d','\0'),(58,'轮播图2',2,323,43,'八成新',2,0,0,'测试轮播图片规范的显示在轮播图窗口',1,1,'2020-11-26 21:40:43','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398052873.jpg','50759239-8408-4430-ba34-45c179a1d8d5',''),(59,'轮播图测试，规范轮播图片',5,52,32,'八成新',3,0,0,'规范轮播图片显示',1,1,'2020-11-26 21:49:07','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398556862.jpg','b1f63e9d-20e2-4ee3-bdfb-e84bfff212e8',''),(60,'还是显示轮播图片',4,52,35,'七成新',5,0,0,'几张好看的壁纸',11,1,'2020-11-26 21:51:27','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398690686.jpg','4266afbf-8be2-44c9-80ad-a4dff8516ba9',''),(61,'部署前的测试发布商品',2,432,32,'当天来的',1,3,0,'部署前的测试发布商品部署前的测试发布商品',1,1,'2020-11-26 22:27:49','https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606400876768.jpg','64f224ba-2a4d-479f-94bd-65274e7c46e0','\0');

/*Table structure for table `commodity_img` */

DROP TABLE IF EXISTS `commodity_img`;

CREATE TABLE `commodity_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `img_src` varchar(200) DEFAULT NULL COMMENT '图片地址',
  `commodity_id` int(11) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  KEY `fk_commodity_img_id` (`commodity_id`),
  CONSTRAINT `fk_commodity_img_id` FOREIGN KEY (`commodity_id`) REFERENCES `commodity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='商品图片表';

/*Data for the table `commodity_img` */

insert  into `commodity_img`(`id`,`img_src`,`commodity_id`) values (15,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603369889295.png',6),(16,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603369885530.png',6),(17,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603369885062.png',6),(18,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603370004148.png',7),(19,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603370003633.png',7),(20,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201022/1603369996144.png',7),(24,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201102/1604288298449.jpg',9),(25,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201102/1604288297118.jpeg',9),(26,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201102/1604288300252.jpg',9),(40,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201104/1604492208706.png',30),(41,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854148711.png',36),(42,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854147584.png',36),(43,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854148698.ico',36),(44,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854186028.ico',37),(45,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854220546.ico',38),(46,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854244395.ico',39),(47,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604854445599.ico',40),(48,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604862252355.png',41),(49,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604862320957.png',42),(51,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604888957285.jpg',44),(52,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604888967251.jpg',44),(53,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604889015792.jpeg',45),(54,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201109/1604889016630.jpeg',45),(67,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201117/1605574315033.jpg',53),(68,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201117/1605574315865.jpg',53),(69,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312215464.png',54),(70,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312215225.png',54),(71,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312215693.png',54),(72,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312267114.png',55),(73,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201125/1606312692418.ico',56),(74,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398050769.jpg',58),(75,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398052873.jpg',58),(76,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398556862.jpg',59),(77,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398553304.jpg',59),(78,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398555557.jpg',59),(79,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398695303.jpg',60),(80,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398690686.jpg',60),(81,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606398693038.jpg',60),(82,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/commodity/20201126/1606400876768.jpg',61);

/*Table structure for table `inform` */

DROP TABLE IF EXISTS `inform`;

CREATE TABLE `inform` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(1000) DEFAULT NULL COMMENT '举报内容',
  `user_id` int(11) DEFAULT NULL COMMENT '举报者',
  `commodity_id` int(11) DEFAULT NULL COMMENT '被举报商品',
  `seek_id` int(11) DEFAULT NULL COMMENT '被举报的求购信息',
  `topic_id` int(11) DEFAULT NULL COMMENT '被举报的帖子',
  `create_time` datetime DEFAULT NULL COMMENT '举报时间',
  `state` bit(1) DEFAULT b'0' COMMENT '0,未处理；1，已处理',
  PRIMARY KEY (`id`),
  KEY `fk_inform_commodity_id` (`commodity_id`),
  KEY `fk_inform_user_id` (`user_id`),
  KEY `fk_inform_seek_id` (`seek_id`),
  KEY `fk_inform_topic_id` (`topic_id`),
  CONSTRAINT `fk_inform_commodity_id` FOREIGN KEY (`commodity_id`) REFERENCES `commodity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inform_seek_id` FOREIGN KEY (`seek_id`) REFERENCES `seek` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inform_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_inform_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='举报信息表';

/*Data for the table `inform` */

insert  into `inform`(`id`,`content`,`user_id`,`commodity_id`,`seek_id`,`topic_id`,`create_time`,`state`) values (19,'人在家中做，锅从天上来',11,NULL,22,NULL,'2020-11-16 23:07:29',''),(21,'这个封面图片含有违规内容',11,53,NULL,NULL,'2020-11-18 16:56:48','\0'),(22,'这个求购信息，，，就是看着不舒服。。。',11,NULL,22,NULL,'2020-11-18 16:57:12','\0'),(25,'举报了一张图片的商品',11,55,NULL,NULL,'2020-11-25 21:51:53','\0'),(28,'收藏你，我还举报你',11,55,NULL,NULL,'2020-11-25 23:22:55','\0'),(33,'小贱，收藏后还要举报',11,61,NULL,NULL,'2020-11-26 22:30:13',''),(34,'求购，我就是不买，但我要举报',11,NULL,27,NULL,'2020-11-26 22:30:31',''),(35,'留下了评论，然后举报',11,NULL,NULL,25,'2020-11-26 22:32:46','');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(200) DEFAULT NULL COMMENT '消息主题',
  `content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `user_id` int(11) DEFAULT NULL COMMENT '消息接收用户',
  `state` int(11) DEFAULT '0' COMMENT '消息阅读状态（0，未读；1，已读；2，删除状态）',
  `create_time` datetime DEFAULT NULL COMMENT '消息发送时间',
  PRIMARY KEY (`id`),
  KEY `fk_message_user_id` (`user_id`),
  CONSTRAINT `fk_message_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='用户消息表';

/*Data for the table `message` */

insert  into `message`(`id`,`title`,`content`,`user_id`,`state`,`create_time`) values (1,'商品下架提醒','\"-------\"商品被举报，经鉴定，该商品涉及违规行为！多次商品违规，将取消用户发布商品的功能！',11,1,'2020-11-06 19:30:47'),(2,'警告','您发布的商品被多次举报，我们将禁用您的发布商品功能！',11,1,'2020-11-06 20:47:39'),(4,'商品下架提醒','你发布的商品 \"又是那个测试轮播图5.0\" 已被举报，经管理员审查，我们已将该商品下架！',11,1,'2020-11-10 09:38:04'),(6,'通知',NULL,NULL,NULL,NULL),(7,'通知','双方都公司法规',NULL,NULL,NULL),(8,'通知','消息发送3      ',1,NULL,NULL),(9,'通知','消息发送正式测试',1,0,'2020-11-10 23:26:45'),(10,'通知','测试文本域',1,0,'2020-11-10 23:27:53'),(11,'通知','测试文本与',1,0,'2020-11-10 23:28:05'),(12,'通知','                还是测试文本与\n',1,0,'2020-11-10 23:28:19'),(13,'通知','            阿道夫    ',1,0,'2020-11-10 23:28:38'),(14,'通知','阿斯蒂芬',11,1,'2020-11-10 23:29:02'),(15,'商品下架提醒','你发布的商品\"测试图片上传问题\"已被举报，经管理员审查，我们已将该商品下架！',11,1,'2020-11-11 10:34:02'),(16,'商品下架提醒','你发布的商品\"就是这里的小程序发布的商品就是这里的小程序发布的商品就是这里的小程序发布的商品就是这里的小程序发布的商品就是这里的小程序发布的商品就是这里的小程序发布的商品就是这里的小程序发布的商\"已被举报，经管理员审查，我们已将该商品下架！',11,1,'2020-11-11 10:40:56'),(17,'通知','测试一下通知',11,1,'2020-11-11 23:44:15'),(18,'商品下架提醒','你发布的商品\"最后十天了啊\"已被举报，经管理员审查，我们已将该商品下架！',1,0,'2020-11-16 19:26:19'),(19,'商品下架提醒','你发布的商品\"测试轮播图3.0\"已被举报，经管理员审查，我们已将该商品下架！',1,0,'2020-11-16 22:42:01'),(20,'商品下架提醒','你发布的商品\"手机上发布一个商品\"已被举报，经管理员审查，我们已将该商品下架！',11,1,'2020-11-16 22:42:57'),(24,'商品下架提醒','你发布的商品 \"小程序发布-1\" 已被举报，经管理员审查，我们已将该商品下架！',11,1,'2020-11-18 15:30:20'),(25,'求购信息删除提醒','你发布的求购信息 \"OMG，价格区间栏崩了\" 已被举报，经管理员审查，我们已将该求购信息删除！',11,1,'2020-11-18 15:32:38'),(26,'论坛帖子删除提醒','你发布的帖子 \"这里是id为11号发的帖子\" 已被举报，经管理员审查，我们已将该帖子信息删除！',11,1,'2020-11-23 00:35:10'),(27,'论坛帖子删除提醒','你发布的帖子 \"第一次测试帖子发布\" 已被举报，经管理员审查，我们已将该帖子信息删除！',1,0,'2020-11-23 02:48:44'),(28,'论坛帖子删除提醒','你发布的帖子 \"再来一次测试发帖子\" 已被举报，经管理员审查，我们已将该帖子信息删除！',1,0,'2020-11-24 17:50:49'),(29,'论坛帖子删除提醒','你发布的帖子 \"还是我，发布了帖子后我要去哪里呢？\" 已被举报，经管理员审查，我们已将该帖子信息删除！',11,1,'2020-11-24 17:51:17'),(30,'论坛帖子删除提醒','你发布的帖子 \"第一次测试帖子发布\" 已被举报，经管理员审查，我们已将该帖子信息删除！',1,0,'2020-11-24 20:56:58'),(31,'求购信息删除提醒','你发布的求购信息 \"定时删除的求购信息\" 已被举报，经管理员审查，我们已将该求购信息删除！',11,1,'2020-11-25 21:55:38'),(32,'商品下架提醒','你发布的商品\"\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:09:00'),(33,'商品下架提醒','你发布的商品\"\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:09:00'),(34,'商品下架提醒','你发布的商品\"\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:09:00'),(35,'商品下架提醒','你发布的商品\"\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:09:00'),(36,'商品下架提醒','你发布的商品\"\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:09:00'),(38,'商品下架提醒','你发布的商品\"\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:09:00'),(41,'商品下架提醒','你发布的商品\"保温杯便宜卖\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:12:00'),(42,'商品下架提醒','你发布的商品\"吃饭的家伙什\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:12:00'),(43,'商品下架提醒','你发布的商品\"宁夏枸杞，正宗宁夏枸杞，宁夏枸杞宁夏枸杞宁夏枸杞，宁夏枸杞宁夏枸杞宁夏枸杞宁夏枸杞\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:12:00'),(44,'商品下架提醒','你发布的商品\"添加商品\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:12:00'),(45,'商品下架提醒','你发布的商品\"测试商品id\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:12:00'),(47,'商品下架提醒','你发布的商品\"测试定时任务删除，一张图片\"30天已到期！现已将该商品下架！',1,0,'2020-11-25 23:12:00'),(50,'论坛帖子删除提醒','你发布的帖子 \"这个话题，主要讨论，部署前的测试\" 已被举报，经管理员审查，我们已将该帖子信息删除！',1,0,'2020-11-26 22:32:56'),(51,'求购信息删除提醒','你发布的求购信息 \"部署前的求购信息发布\" 已被举报，经管理员审查，我们已将该求购信息删除！',1,0,'2020-11-26 22:33:19'),(52,'商品下架提醒','你发布的商品\"保温杯便宜卖\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(53,'商品下架提醒','你发布的商品\"吃饭的家伙什\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(54,'商品下架提醒','你发布的商品\"宁夏枸杞，正宗宁夏枸杞，宁夏枸杞宁夏枸杞宁夏枸杞，宁夏枸杞宁夏枸杞宁夏枸杞宁夏枸杞\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(55,'商品下架提醒','你发布的商品\"添加商品\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(56,'商品下架提醒','你发布的商品\"测试商品id\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(57,'商品下架提醒','你发布的商品\"销售销售\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(58,'商品下架提醒','你发布的商品\"第一次手机上传商品\"30天已到期！现已将该商品下架！',11,2,'2020-12-06 22:44:00'),(59,'商品下架提醒','你发布的商品\"测试成功页面\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(60,'商品下架提醒','你发布的商品\"测试回退页面2\"30天已到期！现已将该商品下架！',11,2,'2020-12-06 22:44:00'),(61,'商品下架提醒','你发布的商品\"测试定时任务删除商品，有图片\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(62,'商品下架提醒','你发布的商品\"测试定时任务删除，一张图片\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:44:00'),(63,'商品下架提醒','你发布的商品\"手机测试定时下架商品\"30天已到期！现已将该商品下架！',11,2,'2020-12-06 22:44:00'),(64,'商品下架提醒','你发布的商品\"保温杯便宜卖\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(65,'商品下架提醒','你发布的商品\"吃饭的家伙什\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(66,'商品下架提醒','你发布的商品\"宁夏枸杞，正宗宁夏枸杞，宁夏枸杞宁夏枸杞宁夏枸杞，宁夏枸杞宁夏枸杞宁夏枸杞宁夏枸杞\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(67,'商品下架提醒','你发布的商品\"添加商品\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(68,'商品下架提醒','你发布的商品\"测试商品id\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(69,'商品下架提醒','你发布的商品\"销售销售\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(70,'商品下架提醒','你发布的商品\"第一次手机上传商品\"30天已到期！现已将该商品下架！',11,2,'2020-12-06 22:46:00'),(71,'商品下架提醒','你发布的商品\"测试成功页面\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(72,'商品下架提醒','你发布的商品\"测试回退页面2\"30天已到期！现已将该商品下架！',11,2,'2020-12-06 22:46:00'),(73,'商品下架提醒','你发布的商品\"测试定时任务删除商品，有图片\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(74,'商品下架提醒','你发布的商品\"测试定时任务删除，一张图片\"30天已到期！现已将该商品下架！',1,0,'2020-12-06 22:46:00'),(75,'商品下架提醒','你发布的商品\"手机测试定时下架商品\"30天已到期！现已将该商品下架！',11,2,'2020-12-06 22:46:00');

/*Table structure for table `seek` */

DROP TABLE IF EXISTS `seek`;

CREATE TABLE `seek` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) DEFAULT NULL COMMENT '求购标题',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注（对求购商品有什么要求么）',
  `state` int(11) DEFAULT '1' COMMENT '求购信息状态：0，已删除；1，存在中',
  `min_price` double DEFAULT NULL COMMENT '价格区间（小）',
  `max_price` double DEFAULT NULL COMMENT '价格区间（大）',
  `user_id` int(11) DEFAULT NULL COMMENT '发布者',
  `publish_date` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `fk_seek_user_id` (`user_id`),
  CONSTRAINT `fk_seek_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='求购表';

/*Data for the table `seek` */

insert  into `seek`(`id`,`title`,`remark`,`state`,`min_price`,`max_price`,`user_id`,`publish_date`) values (21,'用来举报的，你来啊','1233234',1,23,32,11,'2020-11-16 22:11:40'),(22,'你过来撒，你过来撒','安防公司符合的还有痛入骨髓',1,23,454,11,'2020-11-16 22:11:58'),(27,'部署前的求购信息发布','阿道夫',0,23,43,1,'2020-11-26 22:28:03');

/*Table structure for table `sys_admin` */

DROP TABLE IF EXISTS `sys_admin`;

CREATE TABLE `sys_admin` (
  `id` varchar(11) NOT NULL COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `admin` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为超级管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员表';

/*Data for the table `sys_admin` */

insert  into `sys_admin`(`id`,`username`,`password`,`register_date`,`admin`) values ('1','xiaojian','4b7c39d836aec906444e3ee254c116cc','2020-10-12 18:42:07','');

/*Table structure for table `topic` */

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `theme` varchar(500) DEFAULT NULL COMMENT '帖子主题',
  `description` varchar(1000) DEFAULT NULL COMMENT '帖子说明',
  `user_id` int(11) DEFAULT NULL COMMENT '帖子发布者',
  `comment_count` int(11) DEFAULT NULL COMMENT '帖子评论数',
  `click_count` int(11) DEFAULT NULL COMMENT '帖子点击率',
  `publish_date` datetime DEFAULT NULL COMMENT '帖子发布时间',
  `state` int(2) DEFAULT NULL COMMENT '0，删除状态；1，展示中',
  `hot_degree` int(2) DEFAULT '0' COMMENT '热门程度：1,2,3',
  PRIMARY KEY (`id`),
  KEY `fk_topic_user_id` (`user_id`),
  CONSTRAINT `fk_topic_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='论坛表';

/*Data for the table `topic` */

insert  into `topic`(`id`,`theme`,`description`,`user_id`,`comment_count`,`click_count`,`publish_date`,`state`,`hot_degree`) values (1,'《挑三“谏”四》论坛版规========= ^ _ ^ ↑置顶↑','理性消费，别让“二手”再变多手；\n\n在这里大家可以发帖子咨询各方大佬，准备入手的商品都有哪些坑可以避，要买的游戏有哪些亮点，甚至不知道怎么买到心仪东西的购物小白，也可以将自己的购物需求放出来，大家一起讨论讨论~\n\n对于论坛模块，大家有什么别样新奇的想法，欢迎发送邮件至2436009116@qq.com反馈~\n\n预祝大家在这里聊得愉快、有所收获~\n\n\n和谐家园，文明用语，一经举报，严肃处理！',11,1,0,'2020-11-26 21:00:26',1,3),(2,'这里测试一下提交按钮的禁用功能','这里测试一下提交按钮的禁用功能撒地方的',1,0,5,'2020-11-22 23:35:06',1,1),(4,'还是发帖子','测试阀体诶',1,51,23,'2020-11-22 23:40:01',1,1),(6,'这里是id为11号发的帖子','测试删除以及删除通知的发送',11,0,2,'2020-11-23 00:33:23',1,2),(7,'主要为了小程序上面看见你主要为了小程序上面看见你主要为了小程序上面看见你主要为了小程序上面看见你主要为了小程序上面看见你',' 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表 测试小程序显示帖子列表',11,0,0,'2020-11-23 03:53:21',1,1),(8,'这不有被删除了的帖子么、你倒是改回来啊','这不有被删除了的帖子么、你倒是改回来啊这不有被删除了的帖子么、你倒是改回来啊这不有被删除了的帖子么、你倒是改回来啊这不有被删除了的帖子么、你倒是改回来啊这不有被删除了的帖子么、你倒是改回来啊这不有被删除了的帖子么、你倒是改回来啊',11,0,0,'2020-11-23 03:54:24',0,0),(9,'如果是法国','的意图已经发货过',11,0,0,'2020-11-23 23:38:11',1,0),(10,'正式测试小程序端发布帖子正式测试小程序端发布帖子正式测试小程序端发布帖子正式测试小程序端发布帖子正式测试小程序端发布帖子正式测试小程序端发布帖子','正式测试小程序端发布帖子正式测试小程序端发布帖子正式测试小程序端发布帖子正式测试小程序端发布帖子正式测试小程序端发布帖子',11,0,1,'2020-11-23 23:39:07',1,0),(11,'还是那个帖子呢','公司工会的更改后',11,0,0,'2020-11-23 23:43:06',0,0),(12,'手机小程序发布帖子','可以看到帖子内容这里样式崩坏了',11,0,0,'2020-11-24 00:15:20',1,0),(15,'我发布了帖子后你要返回到哪里去呢？','我发布了帖子后你要返回到哪里去呢？我发布了帖子后你要返回到哪里去呢？我发布了帖子后你要返回到哪里去呢？我发布了帖子后你要返回到哪里去呢？',11,0,0,'2020-11-24 12:47:50',1,0),(17,'测试首页计数能力','测试首页计数能力测试首页计数能力测试首页计数能力测试首页计数能力测试首页计数能力测试首页计数能力测试首页计数能力测试首页计数能力',11,0,0,'2020-11-24 23:37:19',1,0),(18,'用来测试定时真实删除帖子','用来测试定时真实删除帖子用来测试定时真实删除帖子用来测试定时真实删除帖子用来测试定时真实删除帖子用来测试定时真实删除帖子',11,0,1,'2020-11-25 23:04:47',1,0),(19,'真实删除帖子测试','真实删除帖子测试真实删除帖子测试真实删除帖子测试真实删除帖子测试真实删除帖子测试',11,0,0,'2020-11-25 23:16:58',0,3),(25,'这个话题，主要讨论，部署前的测试','这个话题，主要讨论，部署前的测试这个话题，主要讨论，部署前的测试这个话题，主要讨论，部署前的测试',1,1,4,'2020-11-26 22:28:31',0,0),(26,'有四张图片的布局','有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局有四张图片的布局',11,0,0,'2021-02-12 15:23:40',1,0),(27,'测试后台白名单','大哥商业街的归属感受过伤人体',11,0,0,'2021-02-12 15:31:09',1,0),(28,'车发电工','挂的发给是梵蒂冈',11,0,0,'2021-02-23 23:20:19',1,0);

/*Table structure for table `topic_img` */

DROP TABLE IF EXISTS `topic_img`;

CREATE TABLE `topic_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '帖子内图片，主键id',
  `img_src` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `topic_id` int(11) DEFAULT NULL COMMENT '帖子id',
  PRIMARY KEY (`id`),
  KEY `fk_topic_img_id` (`topic_id`),
  CONSTRAINT `fk_topic_img_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='帖子内包含图片';

/*Data for the table `topic_img` */

insert  into `topic_img`(`id`,`img_src`,`topic_id`) values (4,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606059313029.jpg',2),(5,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606059311427.jpg',2),(6,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606059610099.jpg',4),(8,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606062807004.png',6),(9,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606062805539.jpg',6),(10,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606074801419.jpg',7),(11,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606074871238.jpg',8),(12,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606074867286.png',8),(13,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606074873569.jpg',8),(14,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606145951921.png',10),(15,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606145953974.ico',10),(16,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606145947632.ico',10),(17,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606146191628.png',11),(18,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606146190130.ico',11),(19,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606146192964.ico',11),(20,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606148122487.jpg',12),(21,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606148129498.jpg',12),(22,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606148121792.jpg',12),(33,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606193272252.png',15),(34,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606193275782.ico',15),(36,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606232246019.ico',17),(37,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606316692446.ico',18),(38,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606316688207.ico',18),(39,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606317422371.ico',19),(40,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606317428016.ico',19),(41,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606317427679.ico',19),(47,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606395175141.png',1),(48,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606395180369.png',1),(49,'https://pick-mini-applet.oss-cn-hangzhou.aliyuncs.com/forum/1606400912868.jpg',25);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小程序用户openid',
  `nickName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户昵称',
  `avatarUrl` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别（0：女；1：男）',
  `country` varchar(50) DEFAULT NULL COMMENT '国家',
  `province` varchar(50) DEFAULT NULL COMMENT '省份',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `have_user_info` enum('0','1','2','3') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '标记获取用户信息的程度（0，未获取；1，部分信息；2，完善信息；3，禁用发布功能）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `qq_num` varchar(50) DEFAULT NULL COMMENT '预留QQ号',
  `wechat_num` varchar(50) DEFAULT NULL COMMENT '预留微信号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`openid`,`nickName`,`avatarUrl`,`gender`,`country`,`province`,`city`,`have_user_info`,`create_time`,`update_time`,`qq_num`,`wechat_num`) values (1,'001','xiaojian','https://thirdwx.qlogo.cn/mmopen/vi_32/COBr7H8YoNvQ6KDkiaxp0TT1ibcnZszFnYickyhjSlZPUgPHPgPficLQONlmHHlzqPw0YFeQEnBfLWaTIv5ibTN0HEg/132',1,'中国','江西省','南昌市','0','2020-10-14 16:52:48',NULL,'1479666897','JR20514139152119'),(11,'oRddA5byi6TKu_WqboJZ6P7cmmm0','小贱','https://thirdwx.qlogo.cn/mmopen/vi_32/COBr7H8YoNvQ6KDkiaxp0TT1ibcnZszFnYickyhjSlZPUgPHPgPficLQONlmHHlzqPw0YFeQEnBfLWaTIv5ibTN0HEg/132',1,NULL,NULL,NULL,'2','2020-11-04 11:26:48','2021-02-07 00:17:01','2326552177','shshfghfghfghtsws'),(12,'007','007','https://thirdwx.qlogo.cn/mmopen/vi_32/COBr7H8YoNvQ6KDkiaxp0TT1ibcnZszFnYickyhjSlZPUgPHPgPficLQONlmHHlzqPw0YFeQEnBfLWaTIv5ibTN0HEg/132',1,NULL,NULL,NULL,'2','2020-11-18 15:39:56','2020-11-18 16:37:31','15464121855','wx_323sfdgfgfgdagsf'),(13,'008','008','https://thirdwx.qlogo.cn/mmopen/vi_32/COBr7H8YoNvQ6KDkiaxp0TT1ibcnZszFnYickyhjSlZPUgPHPgPficLQONlmHHlzqPw0YFeQEnBfLWaTIv5ibTN0HEg/132',0,NULL,NULL,NULL,'1','2020-11-18 16:38:12','2020-11-18 16:38:16','',''),(14,'009','','',NULL,NULL,NULL,NULL,'0','2020-11-18 16:38:47',NULL,'','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
