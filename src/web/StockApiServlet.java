package web;

import com.google.gson.Gson;
import controller.StockController;
import model.dto.StockDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 종목 목록 조회 API
 *
 * 사전 준비:
 * - lib/에 servlet-api(jakarta.servlet, Tomcat 10 기준) jar 추가 완료
 * - lib/에 gson jar 추가 완료
 */
@WebServlet("/api/stocks")
public class StockApiServlet extends HttpServlet {

    private final StockController controller = new StockController();
    private final Gson gson = new Gson();

    /**
     * GET /api/stocks
     * 종목 목록을 외부 API 종가 동기화 후 JSON 배열로 반환한다.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StockDTO> stocks = controller.getAllStocks();

        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(gson.toJson(stocks));
    }
}
