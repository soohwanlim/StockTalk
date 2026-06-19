package com.mycom.myapp.model.mapper;

import com.mycom.myapp.model.dto.CommentDTO;
import java.util.List;

public interface CommentMapper {
    List<CommentDTO> selectByPostId(int postId);
    int insertComment(CommentDTO comment);
}
