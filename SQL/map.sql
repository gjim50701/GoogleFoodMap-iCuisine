-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- 主機: 127.0.0.1:3306
-- 產生時間： 2018-09-20 12:53:34
-- 伺服器版本: 5.7.21
-- PHP 版本： 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `db_test`
--

-- --------------------------------------------------------

--
-- 資料表結構 `map`
--

DROP TABLE IF EXISTS `map`;
CREATE TABLE IF NOT EXISTS `map` (
  `Map_Id` int(11) NOT NULL AUTO_INCREMENT,
  `User_Id` varchar(255) NOT NULL,
  `Node_Name` varchar(255) NOT NULL,
  `Node_Amenity` varchar(255) DEFAULT NULL,
  `Node_Lat` double NOT NULL,
  `Node_Lon` double NOT NULL,
  `Node_Address` varchar(255) DEFAULT '暫無',
  `Node_Phone` varchar(20) DEFAULT '暫無',
  `Node_Star` double DEFAULT '0',
  `Node_Pic` varchar(255) DEFAULT '暫無',
  PRIMARY KEY (`Map_Id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `map`
--

INSERT INTO `map` (`Map_Id`, `User_Id`, `Node_Name`, `Node_Amenity`, `Node_Lat`, `Node_Lon`, `Node_Address`, `Node_Phone`, `Node_Star`, `Node_Pic`) VALUES
(2, 'spaceship007', '宮燈教室', NULL, 25.174412878021155, 121.44935365766288, '暫無', '0226215656', 5, '暫無'),
(3, 'spaceship007', '小小麥', 'fast_food', 25.175819, 121.4495562, '251 新北市淡水區英專路151號', '暫無', 0, '0188.jpg'),
(4, 'BANANA', '九湯屋日式拉麵', NULL, 25.17243388738749, 121.44593182951212, '暫無', '', 5, '暫無'),
(5, 'BANANA', '九湯屋日式拉麵', NULL, 25.17243388738749, 121.44593182951212, '暫無', '', 5, '暫無'),
(6, 'BANANA', '大快披薩', NULL, 25.17776634721707, 121.44916255027056, '暫無', '', 5, '暫無'),
(7, 'BANG', '九湯屋', NULL, 25.172446935115712, 121.44593250006436, '暫無', '', 5, '暫無'),
(12, 'iCuisine123', '淡江大學', NULL, 25.176259239422745, 121.44874311983587, '暫無', '0123456789', 5, '暫無'),
(11, 'Gjim', '安安安安安', NULL, 25.175548310151424, 121.44819024950266, '暫無', '089599565', 3.5, '暫無');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
