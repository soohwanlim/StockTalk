package model.dao;

import model.dto.StockDTO;
import model.mapper.StockMapper;
import org.apache.ibatis.session.SqlSession;
import config.MyBatisConnectionFactory;
import java.util.List;

// MyBatis 기반으로 리팩토링된 Stock 테이블 CRUD
public class StockDAO {

    /**
     * Stock 테이블 전체 조회
     * - MyBatis SqlSession 및 StockMapper 인터페이스 사용
     */
    public List<StockDTO> selectAll() {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            StockMapper mapper = session.getMapper(StockMapper.class);
            return mapper.selectAll();
        } catch (Exception e) {
            System.err.println("StockDAO selectAll Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 특정 종목의 current_price 업데이트
     */
    public void updatePrice(String stockCode, int price) {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            StockMapper mapper = session.getMapper(StockMapper.class);
            mapper.updatePrice(stockCode, price);
        } catch (Exception e) {
            System.err.println("StockDAO updatePrice Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
