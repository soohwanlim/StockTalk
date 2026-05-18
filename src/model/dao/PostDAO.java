package model.dao;

import model.dto.PostDTO;
import java.util.List;

// [A 담당] Post 테이블 CRUD
public class PostDAO {

    /**
     * [A] TODO: 종목별 게시글 목록 조회
     *           - Post LEFT JOIN Comment -> 댓글 수(reply_count) 포함
     *           - GROUP BY post_id, ORDER BY created_at DESC
     */
    public List<PostDTO> selectPostsByStock(String stockCode) {
        // TODO
        return null;
    }

    /**
     * [A] TODO: 게시글 단건 조회 (상세보기용)
     */
    public PostDTO selectPostById(int postId) {
        // TODO
        return null;
    }

    /**
     * [A] TODO: 게시글 INSERT
     */
    public void insertPost(PostDTO post) {
        // TODO
    }

    /**
     * [A] TODO: 게시글 DELETE - post_password 일치 여부 확인
     *           반환값: 삭제 성공 true / 비밀번호 불일치 false
     */
    public boolean deletePost(int postId, String password) {
        // TODO
        return false;
    }
}
