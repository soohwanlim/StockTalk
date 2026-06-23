package com.mycom.myapp.model.mapper;

import com.mycom.myapp.model.dto.CommentDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CommentMapper {
    List<CommentDTO> selectByPostId(int postId);
    int insertComment(CommentDTO comment);
    int deleteComment(@Param("commentId") int commentId, @Param("replyWriter") String replyWriter);
}
