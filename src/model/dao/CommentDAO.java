package model.dao;

import model.dto.CommentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;

// [A 담당] Comment 테이블 CRUD
public class CommentDAO {

    /**
     * [A] TODO: 특정 게시글의 댓글 목록 조회 (created_at ASC)
     */
    public List<CommentDTO> selectByPostId(int postId) {
        // TODO
        List<CommentDTO> list = new ArrayList<>();
            String sql = "SELECT comment_id, post_id, reply_content, reply_writer, created_at " +
                            "FROM Comment WHERE post_id = ? ORDER BY created_at ASC";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, postId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        CommentDTO dto = new CommentDTO();
                        dto.setCommentId(rs.getInt("comment_id"));
                        dto.setPostId(rs.getInt("post_id"));
                        dto.setReplyContent(rs.getString("reply_content"));
                        dto.setReplyWriter(rs.getString("reply_writer"));
                        dto.setCreatedAt(rs.getTimestamp("created_at"));
                        list.add(dto);
                    }
                }

        } catch (Exception e) {
            System.out.println("CommentDAO selectByPostId Error: " + e.getMessage());
        }
        return list;
    }

    /**
     * [A] TODO: 댓글 INSERT
     */
    public void insertComment(CommentDTO comment) {
        // TODO
        String sql = "INSERT INTO Comment (post_id, reply_content, reply_writer) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, comment.getPostId());
                pstmt.setString(2, comment.getReplyContent());
                pstmt.setString(3, comment.getReplyWriter());
                pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("CommentDAO insertComment Error: " + e.getMessage());
        }
    }
}
