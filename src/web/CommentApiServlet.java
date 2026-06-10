package web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.StockController;
import model.dto.CommentDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 댓글 목록 조회, 등록 API
 */
@WebServlet("/api/comments")
public class CommentApiServlet extends HttpServlet {

    private final StockController controller = new StockController();
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * GET /api/comments?postId=12 -> 댓글 목록 (JSON 배열)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        int postId = Integer.parseInt(req.getParameter("postId"));
        List<CommentDTO> comments = controller.getComments(postId);

        resp.getWriter().write(gson.toJson(comments));
    }

    /**
     * POST /api/comments (댓글 등록)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        int postId = Integer.parseInt(req.getParameter("postId"));
        String writer = req.getParameter("writer");
        String content = req.getParameter("content");

        if (writer == null || writer.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            resp.getWriter().write("{\"success\": false, \"message\": \"작성자/댓글 내용은 필수입니다.\"}");
            return;
        }

        controller.addComment(postId, content.trim(), writer.trim());

        resp.getWriter().write("{\"success\": true}");
    }
}
