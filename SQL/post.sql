-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- 主機: 127.0.0.1:3306
-- 產生時間： 2018-09-20 12:53:48
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
-- 資料表結構 `post`
--

DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post` (
  `Post_Id` int(11) NOT NULL AUTO_INCREMENT,
  `User_Id` varchar(255) NOT NULL,
  `Node_Name` varchar(255) NOT NULL,
  `Post_Time` datetime NOT NULL,
  `Post_Pic` varchar(255) DEFAULT NULL,
  `Post_Content` varchar(255) NOT NULL,
  `Post_Star` double NOT NULL,
  PRIMARY KEY (`Post_Id`),
  KEY `Node_Id` (`Node_Name`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `post`
--

INSERT INTO `post` (`Post_Id`, `User_Id`, `Node_Name`, `Post_Time`, `Post_Pic`, `Post_Content`, `Post_Star`) VALUES
(1, 'mike11', '呷子園', '2018-08-31 10:24:16', '3727.jpg', '好吃!! 讚', 4.5),
(32, 'asd', '滬家全自助餐', '2018-09-08 08:26:57', '16789_1536366417.jpg', '乾淨，衛生，好吃', 5),
(5, 'philippine123', '椒麻雞大王', '2018-08-31 13:18:08', '56712.jpg', '好吃但是價格稍高，店內環境較小', 4.5),
(35, '1231212123', '滬家全自助餐', '2018-09-08 19:58:48', '19392_1536407928.jpg', '這家自助餐好吃 我每天都去那邊包便當', 5),
(38, 'iCuisine123', '小小麥', '2018-09-14 16:45:27', '15004_1536914727.jpg', '讚!好吃!', 5),
(33, 'BANANA', '', '2018-09-08 14:26:04', '5024_1536387964.jpg', '好粗 好好粗', 5);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
