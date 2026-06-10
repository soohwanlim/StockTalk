package model.mapper;

import model.dto.CommentDTO;
import java.util.List;

// Comment 테이블 관련 MyBatis Mapper 인터페이스
public interface CommentMapper {
    
    // 특정 게시글의 댓글 목록 조회 (작성시간 오름차순)
    List<CommentDTO> selectByPostId(int postId);
    
    // 댓글 작성
    int insertComment(CommentDTO comment);
}
