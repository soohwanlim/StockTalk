package com.mycom.myapp.api;

import com.mycom.myapp.model.dao.CommentDAO;
import com.mycom.myapp.model.dto.CommentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentDAO commentDAO;

    public CommentApiController(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @GetMapping
    public List<CommentDTO> getComments(@RequestParam int postId) {
        return commentDAO.selectByPostId(postId);
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestParam int postId,
                                           @RequestParam String writer,
                                           @RequestParam String content) {
        if (writer == null || writer.isBlank() || content == null || content.isBlank()) {
            return ResponseEntity.badRequest().body("작성자/댓글 내용은 필수입니다.");
        }
        CommentDTO dto = new CommentDTO();
        dto.setPostId(postId);
        dto.setReplyWriter(writer.trim());
        dto.setReplyContent(content.trim());
        commentDAO.insertComment(dto);
        return ResponseEntity.ok().body("success");
    }
}
