drop table if exists t_order;
-- 订单表
CREATE TABLE `t_order` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `amount` decimal(19,2) DEFAULT NULL,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count` int DEFAULT NULL,
    `order_no` varchar(255) DEFAULT NULL,
    `pay_record_id` bigint DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;