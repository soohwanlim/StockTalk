package com.mycom.myapp.model.dao;

import com.mycom.myapp.model.dto.StockDTO;
import com.mycom.myapp.model.mapper.StockMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Repository
public class StockDAO {

    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public StockDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<StockDTO> selectAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StockMapper mapper = session.getMapper(StockMapper.class);
            return mapper.selectAll();
        }
    }

    public List<StockDTO> selectStocks(String search, int offset, int limit) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StockMapper mapper = session.getMapper(StockMapper.class);
            return mapper.selectStocks(search, offset, limit);
        }
    }

    public int countStocks(String search) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StockMapper mapper = session.getMapper(StockMapper.class);
            return mapper.countStocks(search);
        }
    }

    public void updatePrice(String stockCode, int price) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            StockMapper mapper = session.getMapper(StockMapper.class);
            mapper.updatePrice(stockCode, price);
        }
    }
}
