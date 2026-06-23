package com.mycom.myapp.api;

import com.mycom.myapp.model.dao.PostDAO;
import com.mycom.myapp.model.dto.PostDTO;
import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostDAO postDAO;

    public PostApiController(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(required = false) String stockCode,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) Integer postId) {
        if (postId != null) {
            PostDTO post = postDAO.selectPostById(postId);
            if (post == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Post not found"));
            }
            return ResponseEntity.ok(post);
        }
        if (stockCode == null || stockCode.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "stockCode is required"));
        }
        List<PostDTO> posts = postDAO.selectPostsByStock(stockCode, search);
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestParam String stockCode,
                                        @RequestParam String title,
                                        @RequestParam String content,
                                        @RequestParam String postPassword,
                                        HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }
        if (title == null || title.isBlank() || content == null || content.isBlank() || postPassword == null || postPassword.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "제목/내용/비밀번호는 필수입니다."));
        }
        PostDTO post = new PostDTO();
        post.setStockCode(stockCode);
        post.setTitle(title.trim());
        post.setContent(content.trim());
        post.setWriter(userDto.getUserName());
        post.setPostPassword(postPassword.trim());
        postDAO.insertPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result", "success"));
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@RequestParam int postId,
                                        @RequestParam String title,
                                        @RequestParam String content,
                                        HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }
        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "제목과 내용은 필수입니다."));
        }
        boolean updated = postDAO.updatePost(postId, title.trim(), content.trim(), userDto.getUserName());
        if (!updated) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "권한이 없거나 게시글을 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam int postId,
                                        HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }
        boolean deleted = postDAO.deletePost(postId, userDto.getUserName());
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "삭제 권한이 없거나 게시글을 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
