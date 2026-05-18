package view;

import controller.StockController;
import javax.swing.*;

// [B 담당] 게시글 작성 모달 다이얼로그
public class WriteDialog extends JDialog {

    /**
     * @param parent      부모 프레임
     * @param stockCode   현재 선택된 종목 코드
     * @param controller  StockController
     * @param onSuccess   등록 성공 후 목록 새로고침용 콜백 (Runnable)
     */
    public WriteDialog(JFrame parent, String stockCode,
                       StockController controller, Runnable onSuccess) {
        super(parent, "게시글 작성", true);
        setSize(450, 350);
        setLocationRelativeTo(parent);

        // [B] TODO: 입력 필드 구성 - 제목, 내용(JTextArea), 작성자, 비밀번호
        // [B] TODO: 등록 버튼 클릭 시
        //           - 빈 값 검증 (제목, 내용, 비밀번호 필수)
        //           - PostDTO 조립 -> controller.writePost()
        //           - onSuccess.run() -> dispose()
        // [B] TODO: 취소 버튼 -> dispose()
    }
}
