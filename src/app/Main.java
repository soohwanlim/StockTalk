import config.DBConnection;
import config.StockAPIClient;
import model.dao.StockDAO;
import model.dto.StockDTO;

import java.sql.Connection;
import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {

		// 1. DB 연결 테스트
		System.out.println("===== DB 연결 테스트 =====");
		Connection conn = DBConnection.getConnection();
		if (conn != null) {
			System.out.println("DB 연결 성공!");
			conn.close();
		}

		// 2. StockDAO 조회 테스트
		System.out.println("\n===== Stock 테이블 조회 =====");
		StockDAO stockDAO = new StockDAO();
		List<StockDTO> stocks = stockDAO.selectAll();
		for (StockDTO s : stocks) {
			System.out.println(s); // toString() 호출
		}

		// 3. API 가격 가져와서 DB 업데이트 테스트
		System.out.println("\n===== 가격 업데이트 테스트 =====");
		for (StockDTO s : stocks) {
			int price = StockAPIClient.fetchClosePrice(s.getStockCode());
			stockDAO.updatePrice(s.getStockCode(), price);
			System.out.println(s.getStockName() + " → " + String.format("%,d", price) + "원");
		}

		// 4. 업데이트 후 다시 조회
		System.out.println("\n===== 업데이트 후 재조회 =====");
		stockDAO.selectAll().forEach(System.out::println);
	}
}