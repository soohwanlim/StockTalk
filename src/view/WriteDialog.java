package view;

import controller.StockController;
import model.dto.PostDTO;

import javax.swing.*;
import java.awt.*;

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

        JTextField titleField = new JTextField();

        JTextArea contentArea = new JTextArea();

        JTextField writerField = new JTextField();

        JPasswordField passwordField =
                new JPasswordField();

        JScrollPane contentScroll =
                new JScrollPane(contentArea);

        JPanel formPanel =
                new JPanel(new GridLayout(4, 1, 5, 5));

        formPanel.add(createField("제목", titleField));
        formPanel.add(createField("작성자", writerField));
        formPanel.add(createField("비밀번호", passwordField));

        JPanel contentPanel =
                new JPanel(new BorderLayout());

        contentPanel.add(new JLabel("내용"),
                BorderLayout.NORTH);

        contentPanel.add(contentScroll,
                BorderLayout.CENTER);

        JButton submitBtn = new JButton("등록");
        JButton cancelBtn = new JButton("취소");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        setLayout(new BorderLayout(10, 10));

        add(formPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // [B] TODO: 입력 필드 구성 - 제목, 내용(JTextArea), 작성자, 비밀번호
        // [B] TODO: 등록 버튼 클릭 시
        //           - 빈 값 검증 (제목, 내용, 비밀번호 필수)
        //           - PostDTO 조립 -> controller.writePost()
        //           - onSuccess.run() -> dispose()
        submitBtn.addActionListener(e -> {

            String title =
                    titleField.getText().trim();

            String content =
                    contentArea.getText().trim();

            String writer =
                    writerField.getText().trim();

            String password =
                    new String(passwordField.getPassword()).trim();

            if (title.isEmpty()
                    || content.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "제목/내용/비밀번호 필수");

                return;
            }

            PostDTO post = new PostDTO();

            post.setStockCode(stockCode);
            post.setTitle(title);
            post.setContent(content);
            post.setWriter(writer);
            post.setPostPassword(password);

            controller.writePost(post);

            JOptionPane.showMessageDialog(this,
                    "게시글 등록 완료");

            onSuccess.run();

            dispose();
        });

        // [B] TODO: 취소 버튼 -> dispose()
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JPanel createField(String label,
                               JComponent field) {

        JPanel panel =
                new JPanel(new BorderLayout(5, 5));

        panel.add(new JLabel(label),
                BorderLayout.WEST);

        panel.add(field,
                BorderLayout.CENTER);

        return panel;
    }
}
