package controller;

import model.dao.CommentDAO;
import model.dao.PostDAO;
import model.dao.StockDAO;
import model.dto.CommentDTO;
import model.dto.PostDTO;
import model.dto.StockDTO;

import java.sql.Timestamp;
import java.util.List;

// [B 담당] View와 DAO를 연결하는 중재자
//          단, DAO 인스턴스 선언은 A가 DAO를 완성한 뒤 B가 연결
public class StockController {

    // [B] TODO: StockDAO, PostDAO, CommentDAO 인스턴스 선언
    public static StockDAO stockDAO = new StockDAO();
    public static PostDAO postDAO = new PostDAO();
    public static CommentDAO commentDAO = new CommentDAO();
    /**
     * [B] TODO: 전체 종목 조회 + API로 종가 동기화 후 반환
     *           흐름: stockDAO.selectAll() -> fetchClosePrice() -> updatePrice() -> 반환
     */
    public List<StockDTO> getAllStocks() {
        // TODO
        return stockDAO.selectAll();
    }

    // [B] TODO: getPostsByStock(String stockCode)
    public List<PostDTO> getPostsByStock(String stockCode){
        return postDAO.selectPostsByStock(stockCode);
    }
    // [B] TODO: getPostDetail(int postId)
    public PostDTO getPostDetail(int postId){
        return postDAO.selectPostById(postId);
    }
    // [B] TODO: writePost(PostDTO post)
    public int writePost(PostDTO post) {
        postDAO.insertPost(post);
        return postDAO.selectPostById(post.getPostId()).getPostId(); // 생성된 postId반환
    }
    // [B] TODO: deletePost(int postId, String pw)
    public boolean deletePost(int postId, String pw){
        return postDAO.deletePost(postId, pw);
    }
    // [B] TODO: getComments(int postId)
    public List<CommentDTO> getComments(int postId){
        return commentDAO.selectByPostId(postId);
    }
    // [B] TODO: addComment(int postId, String content, String writer)
    public void addComment(int postId, String content, String writer){
        CommentDTO commentDTO = new CommentDTO(postId, content, writer);
        commentDAO.insertComment(commentDTO);
    }
}
