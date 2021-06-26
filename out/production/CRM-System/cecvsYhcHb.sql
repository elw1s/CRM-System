-- --------------------------------------------------------
-- Sunucu:                       127.0.0.1
-- Sunucu sürümü:                10.4.19-MariaDB - mariadb.org binary distribution
-- Sunucu İşletim Sistemi:       Win64
-- HeidiSQL Sürüm:               11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- cecvsYhcHb için veritabanı yapısı dökülüyor
CREATE DATABASE IF NOT EXISTS `cecvsYhcHb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `cecvsYhcHb`;

-- tablo yapısı dökülüyor cecvsYhcHb.comments
CREATE TABLE IF NOT EXISTS `comments` (
  `product_id` varchar(55) NOT NULL,
  `comments` varchar(55) DEFAULT NULL,
  KEY `product_comments` (`product_id`),
  CONSTRAINT `product_comments` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- cecvsYhcHb.comments: ~2 rows (yaklaşık) tablosu için veriler indiriliyor
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` (`product_id`, `comments`) VALUES
	('p1', 'comment_of_p1_1'),
	('p2', 'comment_of_p2_2'),
	('p1', 'comment_of_p1_2'),
	('p2', 'comment_of_p2_1');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;

-- tablo yapısı dökülüyor cecvsYhcHb.messages
CREATE TABLE IF NOT EXISTS `messages` (
  `customer_id` varchar(55) NOT NULL,
  `message` varchar(55) DEFAULT NULL,
  KEY `customer_messages` (`customer_id`),
  CONSTRAINT `customer_messages` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- cecvsYhcHb.messages: ~4 rows (yaklaşık) tablosu için veriler indiriliyor
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` (`customer_id`, `message`) VALUES
	('C1', 'Message1_for_customer1'),
	('C2', 'Message2_for_customer2'),
	('C2', 'Message1_for_customer2'),
	('C1', 'Message2_for_customer1');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;

-- tablo yapısı dökülüyor cecvsYhcHb.products
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` varchar(55) NOT NULL,
  `name` varchar(55) NOT NULL,
  `category` varchar(55) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- cecvsYhcHb.products: ~4 rows (yaklaşık) tablosu için veriler indiriliyor
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`product_id`, `name`, `category`) VALUES
	('p1', 'product1', 'sport'),
	('p2', 'product2', 'shoes'),
	('p3', 'product3', 'jewel');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- tablo yapısı dökülüyor cecvsYhcHb.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(55) NOT NULL,
  `password` varchar(55) NOT NULL,
  `name` varchar(55) NOT NULL,
  `surname` varchar(55) NOT NULL,
  `email` varchar(55) DEFAULT NULL,
  `phone` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`id`,`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- cecvsYhcHb.users: ~4 rows (yaklaşık) tablosu için veriler indiriliyor
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `password`, `name`, `surname`, `email`, `phone`) VALUES
	('A1', 'A1psw', 'a1name', 'a1srname', NULL, NULL),
	('B1', 'B1psw', 'b1name', 'b1srname', NULL, NULL),
	('C1', 'C1psw', 'c1name', 'c1srname', 'c1email', 'c1phone'),
	('C2', 'C2psw', 'c2name', 'c2srname', 'c2email', 'c2phone');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
