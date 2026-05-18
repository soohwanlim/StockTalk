package view;

import controller.StockController;
import javax.swing.*;
import java.awt.*;

// [B 담당] 메인 윈도우 — 3단 분할 레이아웃
public class MainFrame extends JFrame {

    private final StockController controller = new StockController();

    public MainFrame() {
        setTitle("StockTalk");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        // [B] TODO: 좌상단 로고 패널 구성
        //           getClass().getResource("/stocktalk_logo.png") 로 이미지 로드
        //           실패 시 텍스트 JLabel로 대체 (try-catch)

        // [B] TODO: 좌측 패널 - 종목 JList (JScrollPane 감싸기)

        // [B] TODO: 우측 상단 - 게시글 JTable
        //           컬럼: 번호 | 제목 | 댓글수 | 작성자 | 작성일
        //           글쓰기 / 삭제 버튼 포함

        // [B] TODO: 우측 중단 - 본문 JTextArea (읽기 전용)

        // [B] TODO: 우측 하단 - 댓글 JList + 입력 필드 + 등록 버튼

        // [B] TODO: JSplitPane으로 좌/우, 상/중/하 분할 배치

        // [B] TODO: 이벤트 연결
        //           - 종목 선택  -> loadPosts()
        //           - 게시글 선택 -> loadPostDetail(), loadComments()
        //           - 글쓰기 버튼 -> WriteDialog 열기
        //           - 삭제 버튼  -> 비밀번호 입력 후 controller.deletePost()
        //           - 댓글 등록  -> controller.addComment()

        // [B] TODO: 초기 데이터 로드 - controller.getAllStocks()

        setVisible(true);
    }

    // [B] TODO: loadPosts(String stockCode)
    // [B] TODO: loadPostDetail(int postId)
    // [B] TODO: loadComments(int postId)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
