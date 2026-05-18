-- [A 담당] 초기 스키마 및 시드 데이터
-- MySQL Workbench 등에서 직접 실행하세요

CREATE DATABASE IF NOT EXISTS stock_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE stock_db;

-- [A] TODO: Stock 테이블 생성
--           (stock_code VARCHAR PK, stock_name VARCHAR, current_price INT DEFAULT 0)

-- [A] TODO: Post 테이블 생성
--           (post_id INT PK AUTO_INCREMENT, stock_code FK, title, content,
--            writer DEFAULT '익명', post_password, created_at TIMESTAMP DEFAULT NOW())
--           힌트: FOREIGN KEY (stock_code) REFERENCES Stock(stock_code) ON DELETE CASCADE

-- [A] TODO: Comment 테이블 생성
--           (comment_id INT PK AUTO_INCREMENT, post_id FK, reply_content,
--            reply_writer DEFAULT '익명', created_at TIMESTAMP DEFAULT NOW())
--           힌트: FOREIGN KEY (post_id) REFERENCES Post(post_id) ON DELETE CASCADE

-- 시드 데이터 (테이블 생성 완료 후 주석 해제)
-- INSERT IGNORE INTO Stock (stock_code, stock_name) VALUES
-- ('005930', '삼성전자'),
-- ('000660', 'SK하이닉스'),
-- ('000270', '기아'),
-- ('035720', '카카오');
