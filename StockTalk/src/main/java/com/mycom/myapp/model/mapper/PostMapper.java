package com.mycom.myapp.model.mapper;

import com.mycom.myapp.model.dto.PostDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PostMapper {
    List<PostDTO> selectPostsByStock(@Param("stockCode") String stockCode, @Param("search") String search);
    PostDTO selectPostById(int postId);
    int insertPost(PostDTO post);
    int updatePost(@Param("postId") int postId,
                   @Param("title") String title,
                   @Param("content") String content,
                   @Param("writer") String writer);
    int deletePost(@Param("postId") int postId, @Param("writer") String writer);
}
