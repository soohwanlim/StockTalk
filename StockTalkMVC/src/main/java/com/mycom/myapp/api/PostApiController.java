package com.mycom.myapp.api;

import com.mycom.myapp.model.dao.PostDAO;
import com.mycom.myapp.model.dto.PostDTO;
import com.mycom.myapp.model.dto.CommentDTO;
import com.mycom.myapp.model.dao.CommentDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostDAO postDAO;
    private final CommentDAO commentDAO;

    public PostApiController(PostDAO postDAO, CommentDAO commentDAO) {
        this.postDAO = postDAO;
        this.commentDAO = commentDAO;
    }

    @GetMapping
    public Object getPosts(@RequestParam(required = false) String stockCode,
                           @RequestParam(required = false) Integer postId) {
        if (postId != null) {
            return postDAO.selectPostById(postId);
        }
        return postDAO.selectPostsByStock(stockCode);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestParam String stockCode,
                                        @RequestParam String title,
                                        @RequestParam String content,
                                        @RequestParam(required = false) String writer,
                                        @RequestParam String postPassword) {
        if (title == null || title.isBlank() || content == null || content.isBlank() || postPassword == null || postPassword.isBlank()) {
            return ResponseEntity.badRequest().body("제목/내용/비밀번호는 필수입니다.");
        }
        PostDTO post = new PostDTO();
        post.setStockCode(stockCode);
        post.setTitle(title.trim());
        post.setContent(content.trim());
        post.setWriter(writer == null || writer.isBlank() ? "익명" : writer.trim());
        post.setPostPassword(postPassword.trim());
        postDAO.insertPost(post);
        return ResponseEntity.ok().body("success");
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam int postId, @RequestParam String password) {
        boolean deleted = postDAO.deletePost(postId, password);
        return ResponseEntity.ok().body(deleted ? "success" : "failed");
    }
}
