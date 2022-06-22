

/*========= Order ==========*/
DROP TABLE IF EXISTS `Order`; 
CREATE TABLE `Order` ( 
`id` bigint,
`user_id` bigint,
`goods_id` bigint,
`delivery_addr_id` bigint,
`goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`goods_count` int,
`goods_price` decimal(19,2),
`order_channel` int,
`status` int,
`create_date` datetime,
`pay_date` datetime,
PRIMARY KEY (`id`) USING BTREE,
INDEX `id`(`id`) USING BTREE ) 
ENGINE = INNODB DEFAULT CHARSET= utf8;

/*========= Goods ==========*/
DROP TABLE IF EXISTS `Goods`; 
CREATE TABLE `Goods` ( 
`id` bigint,
`goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`goods_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`goods_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`goods_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`goods_price` decimal(19,2),
`goods_stock` int,
`sales_number` int,
`goods_versions` int,
PRIMARY KEY (`id`) USING BTREE,
INDEX `id`(`id`) USING BTREE ) 
ENGINE = INNODB DEFAULT CHARSET= utf8;

/*========= SeckillOrder ==========*/
DROP TABLE IF EXISTS `SeckillOrder`; 
CREATE TABLE `SeckillOrder` ( 
`id` bigint,
`user_id` bigint,
`order_id` bigint,
`goods_id` bigint,
PRIMARY KEY (`id`) USING BTREE,
INDEX `id`(`id`) USING BTREE ) 
ENGINE = INNODB DEFAULT CHARSET= utf8;

/*========= User ==========*/
DROP TABLE IF EXISTS `User`; 
CREATE TABLE `User` ( 
`id` bigint,
`nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`slat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`hand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
`register_date` datetime,
`last_login_date` datetime,
`log_count` int,
PRIMARY KEY (`id`) USING BTREE,
INDEX `id`(`id`) USING BTREE ) 
ENGINE = INNODB DEFAULT CHARSET= utf8;

/*========= SeckillGoods ==========*/
DROP TABLE IF EXISTS `SeckillGoods`; 
CREATE TABLE `SeckillGoods` ( 
`id` bigint,
`goods_id` bigint,
`seckill_price` decimal(19,2),
`stock_count` int,
`start_date` datetime,
`end_date` datetime,
PRIMARY KEY (`id`) USING BTREE,
INDEX `id`(`id`) USING BTREE ) 
ENGINE = INNODB DEFAULT CHARSET= utf8;