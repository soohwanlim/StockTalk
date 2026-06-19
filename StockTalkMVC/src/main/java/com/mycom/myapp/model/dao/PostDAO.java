package com.mycom.myapp.model.dao;

import com.mycom.myapp.model.dto.PostDTO;
import com.mycom.myapp.model.mapper.PostMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PostDAO {

    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public PostDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<PostDTO> selectPostsByStock(String stockCode) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.selectPostsByStock(stockCode);
        }
    }

    public PostDTO selectPostById(int postId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.selectPostById(postId);
        }
    }

    public void insertPost(PostDTO post) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            mapper.insertPost(post);
        }
    }

    public boolean deletePost(int postId, String password) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.deletePost(postId, password) > 0;
        }
    }
}
