package controller;

import model.dao.CommentDAO;
import model.dao.PostDAO;
import model.dao.StockDAO;
import model.dto.CommentDTO;
import model.dto.PostDTO;
import model.dto.StockDTO;
import config.StockAPIClient;

import java.sql.Timestamp;
import java.util.List;

// View와 DAO를 연결하는 중재자
// 웹 전환 후에는 src/web의 서블릿이 이 컨트롤러를 그대로 재사용한다 (TODO: src/web 참고)
public class StockController {

    public static StockDAO stockDAO = new StockDAO();
    public static PostDAO postDAO = new PostDAO();
    public static CommentDAO commentDAO = new CommentDAO();

    /**
     * 전체 종목 조회 + API로 종가 동기화 후 반환
     * 흐름: stockDAO.selectAll() -> fetchClosePrice() -> updatePrice() -> 반환
     */
    public List<StockDTO> getAllStocks() {
        List<StockDTO> stocks = stockDAO.selectAll();
        for (StockDTO stock : stocks) {
            int price = StockAPIClient.fetchClosePrice(stock.getStockCode());
            stockDAO.updatePrice(stock.getStockCode(), price);
            stock.setCurrentPrice(price); // DTO 값도 갱신해야 JList에 바로 반영됨
        }
        return stocks;
    }

    public List<PostDTO> getPostsByStock(String stockCode){
        return postDAO.selectPostsByStock(stockCode);
    }

    public PostDTO getPostDetail(int postId){
        return postDAO.selectPostById(postId);
    }

    public void writePost(PostDTO post) {
        postDAO.insertPost(post);
    }

    public boolean deletePost(int postId, String pw){
        return postDAO.deletePost(postId, pw);
    }

    public List<CommentDTO> getComments(int postId){
        return commentDAO.selectByPostId(postId);
    }

    public void addComment(int postId, String content, String writer){
        CommentDTO dto = new CommentDTO();
        dto.setPostId(postId);
        dto.setReplyContent(content);
        dto.setReplyWriter(writer);
        commentDAO.insertComment(dto);
    }
}
