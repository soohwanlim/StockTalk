package web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.StockController;
import model.dto.PostDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 게시글 목록/상세 조회, 작성, 삭제 API
 */
@WebServlet("/api/posts")
public class PostApiServlet extends HttpServlet {

    private final StockController controller = new StockController();
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * GET /api/posts?stockCode=005930  -> 종목별 게시글 목록 (JSON 배열)
     * GET /api/posts?postId=12         -> 게시글 상세 (JSON 객체)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        String postIdParam = req.getParameter("postId");
        String stockCode = req.getParameter("stockCode");

        if (postIdParam != null) {
            PostDTO post = controller.getPostDetail(Integer.parseInt(postIdParam));
            resp.getWriter().write(gson.toJson(post));
        } else {
            resp.getWriter().write(gson.toJson(controller.getPostsByStock(stockCode)));
        }
    }

    /**
     * POST /api/posts (게시글 작성)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String stockCode = req.getParameter("stockCode");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String writer = req.getParameter("writer");
        String postPassword = req.getParameter("postPassword");

        if (isBlank(title) || isBlank(content) || isBlank(postPassword)) {
            resp.getWriter().write("{\"success\": false, \"message\": \"제목/내용/비밀번호는 필수입니다.\"}");
            return;
        }

        PostDTO post = new PostDTO();
        post.setStockCode(stockCode);
        post.setTitle(title.trim());
        post.setContent(content.trim());
        post.setWriter(isBlank(writer) ? "익명" : writer.trim());
        post.setPostPassword(postPassword.trim());

        controller.writePost(post);

        resp.getWriter().write("{\"success\": true}");
    }

    /**
     * DELETE /api/posts?postId=12&password=1234 (게시글 삭제)
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        int postId = Integer.parseInt(req.getParameter("postId"));
        String password = req.getParameter("password");

        boolean success = controller.deletePost(postId, password);

        resp.getWriter().write("{\"success\": " + success + "}");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
