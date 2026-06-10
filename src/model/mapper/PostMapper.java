package model.mapper;

import model.dto.PostDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

// Post 테이블 관련 MyBatis Mapper 인터페이스
public interface PostMapper {
    
    // 특정 종목의 게시글 목록 조회 (댓글 개수 포함)
    List<PostDTO> selectPostsByStock(String stockCode);
    
    // 특정 게시글 상세 조회
    PostDTO selectPostById(int postId);
    
    // 게시글 작성
    int insertPost(PostDTO post);
    
    // 게시글 삭제 (비밀번호 검증 포함)
    int deletePost(@Param("postId") int postId, @Param("password") String password);
}
