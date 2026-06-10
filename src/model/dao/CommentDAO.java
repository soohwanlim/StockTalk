package model.dao;

import model.dto.CommentDTO;
import model.mapper.CommentMapper;
import org.apache.ibatis.session.SqlSession;
import config.MyBatisConnectionFactory;
import java.util.List;

// MyBatis 기반으로 리팩토링된 Comment 테이블 CRUD
public class CommentDAO {

    /**
     * 특정 게시글의 댓글 목록 조회 (created_at ASC)
     */
    public List<CommentDTO> selectByPostId(int postId) {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            CommentMapper mapper = session.getMapper(CommentMapper.class);
            return mapper.selectByPostId(postId);
        } catch (Exception e) {
            System.err.println("CommentDAO selectByPostId Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 댓글 INSERT
     */
    public void insertComment(CommentDTO comment) {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            CommentMapper mapper = session.getMapper(CommentMapper.class);
            mapper.insertComment(comment);
        } catch (Exception e) {
            System.err.println("CommentDAO insertComment Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
