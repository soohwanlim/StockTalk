package com.mycom.myapp.api;

import com.mycom.myapp.model.dao.CommentDAO;
import com.mycom.myapp.model.dto.CommentDTO;
import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
                                           @RequestParam String content,
                                           HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "댓글 내용은 필수입니다."));
        }
        CommentDTO dto = new CommentDTO();
        dto.setPostId(postId);
        dto.setReplyWriter(userDto.getUserName());
        dto.setReplyContent(content.trim());
        commentDAO.insertComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result", "success"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam int commentId,
                                           HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }
        boolean deleted = commentDAO.deleteComment(commentId, userDto.getUserName());
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "삭제 권한이 없거나 댓글을 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
