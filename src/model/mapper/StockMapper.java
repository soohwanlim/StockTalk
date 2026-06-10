package model.mapper;

import model.dto.StockDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

// Stock 테이블 관련 MyBatis Mapper 인터페이스
public interface StockMapper {
    
    // 전체 종목 조회
    List<StockDTO> selectAll();
    
    // 특정 종목 주가 수정
    int updatePrice(@Param("stockCode") String stockCode, @Param("price") int price);
}
