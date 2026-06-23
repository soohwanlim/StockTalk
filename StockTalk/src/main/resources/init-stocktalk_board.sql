-- stocktalk_board 스키마 초기화 스크립트
-- MySQL에서 실행하세요.

CREATE DATABASE IF NOT EXISTS stocktalk_board
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE stocktalk_board;

CREATE TABLE IF NOT EXISTS `users` (
  `user_seq` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `user_profile_image` varchar(500) DEFAULT 'noProfile.png',
  `user_register_date` date DEFAULT NULL,
  PRIMARY KEY (`user_seq`),
  UNIQUE KEY `user_email_unique` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text,
  `reg_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `read_count` int DEFAULT 0,
  PRIMARY KEY (`board_id`),
  KEY `idx_board_user_seq` (`user_seq`),
  CONSTRAINT `fk_board_user_seq` FOREIGN KEY (`user_seq`) REFERENCES `users` (`user_seq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
