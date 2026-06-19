package com.mycom.myapp.model.mapper;

import com.mycom.myapp.model.dto.PostDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PostMapper {
    List<PostDTO> selectPostsByStock(String stockCode);
    PostDTO selectPostById(int postId);
    int insertPost(PostDTO post);
    int deletePost(@Param("postId") int postId, @Param("password") String password);
}
