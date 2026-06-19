package com.mycom.myapp.model.dao;

import com.mycom.myapp.model.dto.CommentDTO;
import com.mycom.myapp.model.mapper.CommentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CommentDAO {

    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public CommentDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<CommentDTO> selectByPostId(int postId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            CommentMapper mapper = session.getMapper(CommentMapper.class);
            return mapper.selectByPostId(postId);
        }
    }

    public void insertComment(CommentDTO comment) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            CommentMapper mapper = session.getMapper(CommentMapper.class);
            mapper.insertComment(comment);
        }
    }
}
