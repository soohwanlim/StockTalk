package model.dto;

import java.sql.Timestamp;

// [A 담당] 댓글 데이터 전달 객체
public class CommentDTO {

    private int       commentId;
    private int       postId;
    private String    replyContent;
    private String    replyWriter;
    private Timestamp createdAt;

    // [A] TODO: getter / setter 작성
}
