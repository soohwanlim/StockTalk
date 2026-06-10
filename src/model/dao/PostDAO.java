package model.dao;

import model.dto.PostDTO;
import model.mapper.PostMapper;
import org.apache.ibatis.session.SqlSession;
import config.MyBatisConnectionFactory;
import java.util.List;

// MyBatis 기반으로 리팩토링된 Post 테이블 CRUD
public class PostDAO {

    /**
     * 종목별 게시글 목록 조회
     * - Post LEFT JOIN Comment -> 댓글 수(reply_count) 포함
     * - GROUP BY post_id, ORDER BY created_at DESC
     */
    public List<PostDTO> selectPostsByStock(String stockCode) {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.selectPostsByStock(stockCode);
        } catch (Exception e) {
            System.err.println("PostDAO selectPostsByStock Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 게시글 단건 조회 (상세보기용)
     */
    public PostDTO selectPostById(int postId) {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.selectPostById(postId);
        } catch (Exception e) {
            System.err.println("PostDAO selectPostById Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 게시글 INSERT
     */
    public void insertPost(PostDTO post) {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            mapper.insertPost(post);
        } catch (Exception e) {
            System.err.println("PostDAO insertPost Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 게시글 DELETE - post_password 일치 여부 확인
     * 반환값: 삭제 성공 true / 비밀번호 불일치 false
     */
    public boolean deletePost(int postId, String password) {
        try (SqlSession session = MyBatisConnectionFactory.getSqlSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            int deletedRows = mapper.deletePost(postId, password);
            return deletedRows > 0; // 0이면 비밀번호 불일치 또는 데이터 없음
        } catch (Exception e) {
            System.err.println("PostDAO deletePost Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
