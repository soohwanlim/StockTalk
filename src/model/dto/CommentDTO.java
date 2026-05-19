package model.dto;

import java.sql.Timestamp;

// [A 담당] 댓글 데이터 전달 객체
public class CommentDTO {

    private int commentId; // 기본키
    private int postId; // 외래키
    private String replyContent;
    private String replyWriter;
    private Timestamp createdAt;

    // [A] TODO: getter / setter 작성
    public CommentDTO() {
    }

    public CommentDTO(int commentId, int postId, String replyContent, String replyWriter, Timestamp createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.replyContent = replyContent;
        this.replyWriter = replyWriter;
        this.createdAt = createdAt;
    }
    // insert용 생성자
    public CommentDTO(int postId, String replyContent, String replyWriter) {
        this.postId = postId;
        this.replyContent = replyContent;
        this.replyWriter = replyWriter;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyWriter() {
        return replyWriter;
    }

    public void setReplyWriter(String replyWriter) {
        this.replyWriter = replyWriter;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
