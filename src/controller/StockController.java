package controller;

import model.dto.CommentDTO;
import model.dto.PostDTO;
import model.dto.StockDTO;
import java.util.List;

// [B 담당] View와 DAO를 연결하는 중재자
//          단, DAO 인스턴스 선언은 A가 DAO를 완성한 뒤 B가 연결
public class StockController {

    // [B] TODO: StockDAO, PostDAO, CommentDAO 인스턴스 선언

    /**
     * [B] TODO: 전체 종목 조회 + API로 종가 동기화 후 반환
     *           흐름: stockDAO.selectAll() -> fetchClosePrice() -> updatePrice() -> 반환
     */
    public List<StockDTO> getAllStocks() {
        // TODO
        return null;
    }

    // [B] TODO: getPostsByStock(String stockCode)
    // [B] TODO: getPostDetail(int postId)
    // [B] TODO: writePost(PostDTO post)
    // [B] TODO: deletePost(int postId, String pw)
    // [B] TODO: getComments(int postId)
    // [B] TODO: addComment(int postId, String content, String writer)
}
