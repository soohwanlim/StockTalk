package model.dto;

import java.sql.Timestamp;

// [A 담당] 게시글 데이터 전달 객체
public class PostDTO {

    private int       postId;
    private String    stockCode;
    private String    title;
    private String    content;
    private String    writer;
    private String    postPassword;
    private Timestamp createdAt;
    private int       replyCount; // LEFT JOIN으로 집계한 댓글 수

    // [A] TODO: getter / setter 작성
}
