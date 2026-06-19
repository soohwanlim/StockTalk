package com.mycom.myapp.model.mapper;

import com.mycom.myapp.model.dto.StockDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface StockMapper {
    List<StockDTO> selectAll();
    int updatePrice(@Param("stockCode") String stockCode, @Param("price") int price);
}
