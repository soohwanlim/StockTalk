package model.dto;

import java.sql.Timestamp;

// [A 담당] 게시글 데이터 전달 객체
public class PostDTO {

    private int postId; // 기본키
    private String stockCode; // 외래키
    private String title;
    private String content;
    private String writer;
    private String postPassword;
    private Timestamp createdAt;
    private int replyCount; // LEFT JOIN으로 집계한 댓글 수

    // [A] TODO: getter / setter 작성
    public PostDTO() {
    }

    public PostDTO(int postId, String stockCode, String title, String content, String writer, String postPassword,
            Timestamp createdAt, int replyCount) {
        this.postId = postId;
        this.stockCode = stockCode;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.postPassword = postPassword;
        this.createdAt = createdAt;
        this.replyCount = replyCount;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPostPassword() {
        return postPassword;
    }

    public void setPostPassword(String postPassword) {
        this.postPassword = postPassword;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
}
