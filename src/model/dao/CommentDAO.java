package model.dao;

import model.dto.CommentDTO;
import java.util.List;

// [A 담당] Comment 테이블 CRUD
public class CommentDAO {

    /**
     * [A] TODO: 특정 게시글의 댓글 목록 조회 (created_at ASC)
     */
    public List<CommentDTO> selectByPostId(int postId) {
        // TODO
        return null;
    }

    /**
     * [A] TODO: 댓글 INSERT
     */
    public void insertComment(CommentDTO comment) {
        // TODO
    }
}
