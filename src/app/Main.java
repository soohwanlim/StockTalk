package app;

import java.sql.Connection;
import java.sql.SQLException;

import config.DBConnection;

public class Main {

    public static void main(String[] args) throws SQLException {

        System.out.println("===== DB 연결 테스트 =====");
	        Connection conn = DBConnection.getConnection();
		if(conn != null) {
	        System.out.println("DB 연결 성공!");
	    } else {
	        System.out.println("DB 연결 실패...");
	    }
    }
}