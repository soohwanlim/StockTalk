package com.mycom.myapp.model.mapper;

import com.mycom.myapp.model.dto.StockDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface StockMapper {
    List<StockDTO> selectAll();
    List<StockDTO> selectStocks(@Param("search") String search, @Param("offset") int offset, @Param("limit") int limit);
    int countStocks(@Param("search") String search);
    int updatePrice(@Param("stockCode") String stockCode, @Param("price") int price);
}
