-- StockTalk stock/post/comment 스키마 초기화 스크립트
-- MySQL에서 실행하세요.

CREATE DATABASE IF NOT EXISTS stock_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE stock_db;

CREATE TABLE IF NOT EXISTS users (
    user_seq INT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    user_password VARCHAR(50) NOT NULL,
    user_email VARCHAR(100) NOT NULL,
    user_profile_image VARCHAR(500) DEFAULT 'noProfile.png',
    user_register_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_seq),
    UNIQUE KEY user_email_unique (user_email)
);

CREATE TABLE IF NOT EXISTS Stock (
    stock_code VARCHAR(10) NOT NULL,
    stock_name VARCHAR(50) NOT NULL,
    current_price INT DEFAULT 0,
    PRIMARY KEY (stock_code)
);

CREATE TABLE IF NOT EXISTS Post (
    post_id INT AUTO_INCREMENT NOT NULL,
    stock_code VARCHAR(10) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    writer VARCHAR(30) DEFAULT '익명',
    post_password VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id),
    FOREIGN KEY (stock_code) REFERENCES Stock(stock_code) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Comment (
    comment_id INT AUTO_INCREMENT NOT NULL,
    post_id INT NOT NULL,
    reply_content VARCHAR(500) NOT NULL,
    reply_writer VARCHAR(30) DEFAULT '익명',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (post_id) REFERENCES Post(post_id) ON DELETE CASCADE
);

INSERT IGNORE INTO Stock (stock_code, stock_name) VALUES
('005930', '삼성전자'),
('000660', 'SK하이닉스'),
('402340', 'SK스퀘어'),
('005935', '삼성전자우'),
('005380', '현대차'),
('373220', 'LG에너지솔루션'),
('034020', '두산에너빌리티'),
('329180', 'HD현대중공업'),
('028260', '삼성물산'),
('009150', '삼성전기'),
('000270', '기아'),
('035720', '카카오');
