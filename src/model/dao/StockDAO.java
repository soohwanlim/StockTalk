package model.dao;

import model.dto.StockDTO;
import java.util.List;

// [A 담당] Stock 테이블 CRUD
public class StockDAO {

    /**
     * [A] TODO: Stock 테이블 전체 조회
     *           - try-with-resources 사용
     *           - PreparedStatement 사용 (SQL Injection 방지)
     */
    public List<StockDTO> selectAll() {
        // TODO
        return null;
    }

    /**
     * [A] TODO: 특정 종목의 current_price 업데이트
     */
    public void updatePrice(String stockCode, int price) {
        // TODO
    }
}
