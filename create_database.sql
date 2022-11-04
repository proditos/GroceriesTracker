CREATE DATABASE IF NOT EXISTS `groceries`;

USE `groceries`;

CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` double unsigned NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `receipts` (
  `receipt_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seller_name` varchar(255) NOT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`receipt_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `receipts_products` (
  `receipt_product_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `receipt_id` int(11) unsigned NOT NULL,
  `product_id` int(11) unsigned NOT NULL,
  `quantity` double unsigned NOT NULL,
  PRIMARY KEY (`receipt_product_id`) USING BTREE,
  KEY `FK_receipts_products_receipts` (`receipt_id`) USING BTREE,
  KEY `FK_receipts_products_products` (`product_id`),
  CONSTRAINT `FK_receipts_products_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_receipts_products_receipts` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`receipt_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
