package model.dao;

import model.dto.StockDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;

// [A 담당] Stock 테이블 CRUD
public class StockDAO {

    /**
     * [A] TODO: Stock 테이블 전체 조회
     * - try-with-resources 사용
     * - PreparedStatement 사용 (SQL Injection 방지)
     */
    public List<StockDTO> selectAll() {
        // TODO
        List<StockDTO> list = new ArrayList<>();

        String sql = "SELECT stock_code, stock_name, current_price FROM Stock ORDER BY stock_name ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setStockCode(rs.getString("stock_code"));
                dto.setStockName(rs.getString("stock_name"));
                dto.setCurrentPrice(rs.getInt("current_price"));
                list.add(dto);
            }

        } catch (Exception e) {
            System.out.println("StockDAO selectAll Error: " + e.getMessage());
        }
        return list;
    }

    /**
     * [A] TODO: 특정 종목의 current_price 업데이트
     */
    public void updatePrice(String stockCode, int price) {
        // TODO
        String sql = "UPDATE Stock SET current_price = ? WHERE stock_code = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, price);
            pstmt.setString(2, stockCode);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("StockDAO updatePrice Error: " + e.getMessage());
        }
    }
}
