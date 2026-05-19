package app;

import config.DBConnection;
import config.StockAPIClient;
import controller.StockController;
import model.dao.StockDAO;
import model.dto.CommentDTO;
import model.dto.PostDTO;
import model.dto.StockDTO;
import view.MainFrame;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		try {

			Connection conn = DBConnection.getConnection();

			System.out.println("DB 연결 성공!");

			conn.close();

			SwingUtilities.invokeLater(MainFrame::new);

		} catch (Exception e) {

//			e.printStackTrace();

			JOptionPane.showMessageDialog(
					null,
					"DB 연결 실패",
					"ERROR",
					JOptionPane.ERROR_MESSAGE
			);
		}
		//		final StockController controller = new StockController();
		// 1. DB 연결 테스트
//		Connection conn = DBConnection.getConnection();
//		System.out.println("===== DB 연결 테스트 =====");
//		if (conn != null) {
//			System.out.println("DB 연결 성공!");
//			conn.close();
//		}
//
//		// 2. StockDAO 조회 테스트
//		System.out.println("\n===== Stock 테이블 조회 =====");
//		StockDAO stockDAO = new StockDAO();
//		List<StockDTO> stocks = stockDAO.selectAll();
//		for (StockDTO s : stocks) {
//			System.out.println(s); // toString() 호출
//		}
//
//		// 3. API 가격 가져와서 DB 업데이트 테스트
//		System.out.println("\n===== 가격 업데이트 테스트 =====");
//		for (StockDTO s : stocks) {
//			int price = StockAPIClient.fetchClosePrice(s.getStockCode());
//			stockDAO.updatePrice(s.getStockCode(), price);
//			System.out.println(s.getStockName() + " → " + String.format("%,d", price) + "원");
//		}
//
//		// 4. 업데이트 후 다시 조회
//		System.out.println("\n===== 업데이트 후 재조회 =====");
//		stockDAO.selectAll().forEach(System.out::println);
//
//		// 5 Controller Test
//		// 5-1. stocks 조회
////		List<StockDTO> stocks;
//		stocks = controller.getAllStocks();
//		for(StockDTO stock : stocks) {
//			System.out.println(
//					stock.getStockCode() + " / "
//							+ stock.getStockName() + " / "
//							+ stock.getCurrentPrice()
//			);
//		}
//		// 5-2. 게시글 목록 조회 테스트
//		System.out.println("\n===== 게시글 목록 조회 =====");
//
//		List<PostDTO> posts =
//				controller.getPostsByStock("005930");
//
//		for(PostDTO p : posts) {
//
//			System.out.println(
//					"번호: " + p.getPostId()
//							+ " / 제목: " + p.getTitle()
//							+ " / 작성자: " + p.getWriter()
//							+ " / 댓글수: " + p.getReplyCount()
//			);
//		}
//		// 5-3. 게시글 상세 조회
//		if(!posts.isEmpty()) {
//
//			int postId = posts.get(0).getPostId();
//
//			System.out.println("\n===== 게시글 상세 조회 =====");
//
//			PostDTO detail =
//					controller.getPostDetail(postId);
//
//			System.out.println("번호: " + detail.getPostId());
//			System.out.println("제목: " + detail.getTitle());
//			System.out.println("내용: " + detail.getContent());
//		}
//		// 5-4 댓글 조회
//		System.out.println("\n===== 댓글 목록 =====");
//		int postId = posts.get(0).getPostId();
//		List<CommentDTO> comments =
//				controller.getComments(postId);
//
//		for(CommentDTO c : comments) {
//
//			System.out.println(
//					c.getCommentId()
//							+ " / "
//							+ c.getReplyWriter()
//							+ " / "
//							+ c.getReplyContent()
//			);
//		}
	}
}