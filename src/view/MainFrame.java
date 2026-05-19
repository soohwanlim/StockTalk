package view;

import controller.StockController;
import model.dto.CommentDTO;
import model.dto.PostDTO;
import model.dto.StockDTO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import java.net.URL;

// [B 담당] 메인 윈도우 — 3단 분할 레이아웃
public class MainFrame extends JFrame {

    private final StockController controller = new StockController();
    // 종목 리스트
    private DefaultListModel<StockDTO> stockListModel = new DefaultListModel<>();
    private JList<StockDTO> stockList = new JList<>(stockListModel);

    // 게시글 테이블
    private DefaultTableModel postTableModel;
    private JTable postTable;

    // 본문
    private JTextArea contentArea = new JTextArea();

    // 댓글
    private DefaultListModel<String> commentListModel = new DefaultListModel<>();
    private JList<String> commentList = new JList<>(commentListModel);

    // 댓글 입력
    private JTextField commentWriterField = new JTextField();
    private JTextField commentInputField = new JTextField();

    // 현재 선택
    private String currentStockCode;
    private int currentPostId = -1;

    public MainFrame() {
        setTitle("StockTalk");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        // [B] TODO: 좌상단 로고 패널 구성
        //           getClass().getResource("/stocktalk_logo.png") 로 이미지 로드
        //           실패 시 텍스트 JLabel로 대체 (try-catch)
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel logoPanel = new JPanel();
        try {
            URL imageUrl = getClass().getResource("/logo_image/stocktalk_logo.png");

            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                JLabel logoLabel = new JLabel(icon);
                logoPanel.add(logoLabel);
            } else {
                JLabel textLogo = new JLabel("STOCK TALK");
                textLogo.setFont(new Font("Arial", Font.BOLD, 24));
                logoPanel.add(textLogo);
            }
        } catch (Exception e) {
            JLabel textLogo = new JLabel("STOCK TALK");
            textLogo.setFont(new Font("Arial", Font.BOLD, 24));
            logoPanel.add(textLogo);
        }

        leftPanel.add(logoPanel, BorderLayout.NORTH);

        // [B] TODO: 좌측 패널 - 종목 JList (JScrollPane 감싸기)
        stockList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane stockScroll = new JScrollPane(stockList);
        leftPanel.add(stockScroll, BorderLayout.CENTER);
        // [B] TODO: 우측 상단 - 게시글 JTable
        //           컬럼: 번호 | 제목 | 댓글수 | 작성자 | 작성일
        //           글쓰기 / 삭제 버튼 포함
        String[] columns = {"번호", "제목", "댓글수", "작성자", "작성일"};

        postTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        postTable = new JTable(postTableModel);

        JScrollPane tableScroll = new JScrollPane(postTable);

        JButton writeBtn = new JButton("글쓰기");
        JButton deleteBtn = new JButton("삭제");

        JPanel postButtonPanel = new JPanel();
        postButtonPanel.add(writeBtn);
        postButtonPanel.add(deleteBtn);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(tableScroll, BorderLayout.CENTER);
        topPanel.add(postButtonPanel, BorderLayout.SOUTH);

        // [B] TODO: 우측 중단 - 본문 JTextArea (읽기 전용)
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);

        JScrollPane contentScroll = new JScrollPane(contentArea);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(contentScroll, BorderLayout.CENTER);
        // [B] TODO: 우측 하단 - 댓글 JList + 입력 필드 + 등록 버튼
        JList<String> commentList = new JList<>(commentListModel);
        JScrollPane commentScroll = new JScrollPane(commentList);

        JButton commentBtn = new JButton("등록");

        JPanel commentInputPanel = new JPanel(new GridLayout(2, 1));

        JPanel writerPanel = new JPanel(new BorderLayout());
        writerPanel.add(new JLabel("작성자 "), BorderLayout.WEST);
        writerPanel.add(commentWriterField, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(commentInputField, BorderLayout.CENTER);
        inputPanel.add(commentBtn, BorderLayout.EAST);

        commentInputPanel.add(writerPanel);
        commentInputPanel.add(inputPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(commentScroll, BorderLayout.CENTER);
        bottomPanel.add(commentInputPanel, BorderLayout.SOUTH);
        // [B] TODO: JSplitPane으로 좌/우, 상/중/하 분할 배치
        JSplitPane verticalSplit1 =
                new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, middlePanel);

        verticalSplit1.setResizeWeight(0.5);

        JSplitPane verticalSplit2 =
                new JSplitPane(JSplitPane.VERTICAL_SPLIT, verticalSplit1, bottomPanel);

        verticalSplit2.setResizeWeight(0.7);

        JSplitPane mainSplit =
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, verticalSplit2);

        mainSplit.setResizeWeight(0.2);

        add(mainSplit);
        // [B] TODO: 이벤트 연결
        //           - 종목 선택  -> loadPosts()
        // 종목 선택
        stockList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                StockDTO stock = stockList.getSelectedValue();

                if (stock != null) {

                    currentStockCode = stock.getStockCode();

                    loadPosts(currentStockCode);
                }
            }
        });
        //           - 게시글 선택 -> loadPostDetail(), loadComments()
        postTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = postTable.getSelectedRow();

                if (row != -1) {

                    currentPostId =
                            Integer.parseInt(postTableModel.getValueAt(row, 0).toString());

                    loadPostDetail(currentPostId);
                    loadComments(currentPostId);
                }
            }
        });
        //           - 글쓰기 버튼 -> WriteDialog 열기
        writeBtn.addActionListener(e -> {

            if (currentStockCode == null) {
                JOptionPane.showMessageDialog(this, "종목을 먼저 선택하세요.");
                return;
            }

            new WriteDialog(
                    this,
                    currentStockCode,
                    controller,
                    () -> loadPosts(currentStockCode)
            );
        });

        //           - 삭제 버튼  -> 비밀번호 입력 후 controller.deletePost()
        deleteBtn.addActionListener(e -> {

            if (currentPostId == -1) {
                JOptionPane.showMessageDialog(this, "삭제할 게시글을 선택하세요.");
                return;
            }

            String pw = JOptionPane.showInputDialog(this, "비밀번호 입력");

            if (pw == null) return;

            boolean result = controller.deletePost(currentPostId, pw);

            if (result) {
                JOptionPane.showMessageDialog(this, "삭제 성공");

                loadPosts(currentStockCode);

                contentArea.setText("");
                commentListModel.clear();

            } else {
                JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다.");
            }
        });
        //           - 댓글 등록  -> controller.addComment()
        commentBtn.addActionListener(e -> {

        if (currentPostId == -1) {
            JOptionPane.showMessageDialog(this, "게시글을 선택하세요.");
            return;
        }

        String writer = commentWriterField.getText().trim();
        String content = commentInputField.getText().trim();

        if (writer.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "작성자와 댓글을 입력하세요.");
            return;
        }

        controller.addComment(currentPostId, content, writer);

        commentInputField.setText("");

        loadComments(currentPostId);
        loadPosts(currentStockCode);
    });

        // [B] TODO: 초기 데이터 로드 - controller.getAllStocks()
        List<StockDTO> stocks =
                controller.getAllStocks();

        for (StockDTO stock : stocks) {
            stockListModel.addElement(stock);
        }

        setVisible(true);
    }

    // [B] TODO: loadPosts(String stockCode)
    private void loadPosts(String stockCode) {

        postTableModel.setRowCount(0);

        List<PostDTO> posts = controller.getPostsByStock(stockCode);

        for (PostDTO post : posts) {

            Object[] row = {
                    post.getPostId(),
                    post.getTitle(),
//                    post.getCommentCount(),
                    post.getWriter(),
                    post.getCreatedAt()
            };

            postTableModel.addRow(row);
        }
    }


    // [B] TODO: loadPostDetail(int postId)
    private void loadPostDetail(int postId) {

        PostDTO post = controller.getPostDetail(postId);

        if (post != null) {

            contentArea.setText(
                    "제목 : " + post.getTitle() + "\n\n"
                            + post.getContent()
            );
        }
    }
    // [B] TODO: loadComments(int postId)
    private void loadComments(int postId) {

        commentListModel.clear();

        List<CommentDTO> comments = controller.getComments(postId);

        for (CommentDTO comment : comments) {

            commentListModel.addElement(
                    "[" + comment.getReplyWriter() + "] "
                            + comment.getReplyContent()
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
