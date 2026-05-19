package model.dao;

import model.dto.PostDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;

// [A 담당] Post 테이블 CRUD
public class PostDAO {

    /**
     * [A] TODO: 종목별 게시글 목록 조회
     *           - Post LEFT JOIN Comment -> 댓글 수(reply_count) 포함
     *           - GROUP BY post_id, ORDER BY created_at DESC
     */
    public List<PostDTO> selectPostsByStock(String stockCode) {
        // TODO
        List<PostDTO> list = new ArrayList<>();

        String sql = "SELECT p.post_id, p.stock_code, p.title, p.content, p.writer, p.created_at, COUNT(c.comment_id) AS reply_count " +
                        "FROM Post p LEFT JOIN Comment c ON p.post_id = c.post_id " +
                        "WHERE p.stock_code = ? " +
                        "GROUP BY p.post_id " +
                        "ORDER BY p.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, stockCode);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        PostDTO dto = new PostDTO();
                        dto.setPostId(rs.getInt("post_id"));
                        dto.setStockCode(rs.getString("stock_code"));
                        dto.setTitle(rs.getString("title"));
                        dto.setContent(rs.getString("content"));
                        dto.setWriter(rs.getString("writer"));
                        dto.setCreatedAt(rs.getTimestamp("created_at"));
                        dto.setReplyCount(rs.getInt("reply_count"));
                        list.add(dto);
                    }
                }

        } catch (Exception e) {
            System.out.println("PostDAO selectPostsByStock Error: " + e.getMessage());
        }
        return list;
    }

    /**
     * [A] TODO: 게시글 단건 조회 (상세보기용)
     */
    public PostDTO selectPostById(int postId) {
        // TODO
        String sql = "SELECT post_id, stock_code, title, content, writer, created_at FROM Post WHERE post_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, postId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        PostDTO dto = new PostDTO();
                        dto.setPostId(rs.getInt("post_id"));
                        dto.setStockCode(rs.getString("stock_code"));
                        dto.setTitle(rs.getString("title"));
                        dto.setContent(rs.getString("content"));
                        dto.setWriter(rs.getString("writer"));
                        dto.setCreatedAt(rs.getTimestamp("created_at"));
                        return dto;
                    }
            }

        } catch (Exception e) {
            System.out.println("PostDAO selectPostById Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * [A] TODO: 게시글 INSERT
     */
    public void insertPost(PostDTO post) {
        // TODO
        String sql = "INSERT INTO Post (stock_code, title, content, writer, post_password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, post.getStockCode());
                pstmt.setString(2, post.getTitle());
                pstmt.setString(3, post.getContent());
                pstmt.setString(4, post.getWriter());
                pstmt.setString(5, post.getPostPassword());
                pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("PostDAO insertPost Error: " + e.getMessage());
        }
    }

    /**
     * [A] TODO: 게시글 DELETE - post_password 일치 여부 확인
     *           반환값: 삭제 성공 true / 비밀번호 불일치 false
     */
public boolean deletePost(int postId, String password) {
    String sql = "DELETE FROM Post WHERE post_id = ? AND post_password = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, postId);
        pstmt.setString(2, password);
        return pstmt.executeUpdate() > 0; // 0이면 비밀번호 불일치
    } catch (Exception e) {
        System.out.println("PostDAO deletePost Error: " + e.getMessage());
    }
    return false;
}
}
