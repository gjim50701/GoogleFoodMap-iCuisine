-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- 主機: 127.0.0.1:3306
-- 產生時間： 2018-09-20 12:54:03
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
-- 資料表結構 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `User_Id` varchar(255) NOT NULL,
  `User_Password` varchar(255) NOT NULL,
  `User_Name` varchar(255) NOT NULL,
  `User_Pic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `user`
--

INSERT INTO `user` (`User_Id`, `User_Password`, `User_Name`, `User_Pic`) VALUES
('mike11', 'm5130410', 'Mike Wu', '15960_1536021132.jpg'),
('123', '123', 'gg01', 'Default.jpg'),
('abobrother', '87000', '阿柏哥', '0004.jpg'),
('philippine123', 'ilovemycountry', '中正旭哥', '29517_1536387777.jpg'),
('Gjim', 'qwertyuiop', '我是誰 皮卡丘', '9821_1536304653.jpg'),
('spaceship007', 'asdf123', '宇宙太空人', '0007.jpg'),
('asd', 'asd', '王希銘', '31956_1536366320.jpg'),
('1231212123', '1231212123', '陳俊傑', '17571_1536366576.jpg'),
('Pig6666', '66666Pig', 'Piggy', 'Default.jpg'),
('BANANA', 'BANANA', 'BANANA', '7764_1536319820.jpg'),
('iCuisine123', '123', 'iCuisine2', '31247_1536913993.jpg');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
