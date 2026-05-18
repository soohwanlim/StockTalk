package config;

import java.sql.Connection;

// [A 담당] DB 커넥션 관리 유틸리티
public class DBConnection {

    private static final String DRIVER   = "com.mysql.cj.jdbc.Driver";
    private static final String URL      = "jdbc:mysql://localhost:3306/stock_db?serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USER     = "root";
    private static final String PASSWORD = "your_password"; // <- [A] 본인 비밀번호로 수정

    /**
     * [A] TODO: DriverManager로 DB 커넥션을 생성해서 반환하세요.
     *           드라이버 로드 실패 시 SQLException으로 감싸서 던지세요.
     */
    public static Connection getConnection() {
        return null;
    }
}
