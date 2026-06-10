/**
 * 화면 렌더링 + 이벤트 바인딩.
 * 기존 view/MainFrame.java, view/WriteDialog.java의 로직을 AJAX 기반으로 재구성한다.
 */

let currentStockCode = null;
let currentPostId = null;

// DOMContentLoaded 시 loadStocks() 호출 (기존 MainFrame 생성자의 초기 데이터 로드)
document.addEventListener("DOMContentLoaded", () => {
    loadStocks();
});

// fetchStocks() 호출 후 #stock-list에 <li data-code="stockCode"> 렌더링
// 각 li 클릭 시 currentStockCode 갱신 + .selected 클래스 토글 + loadPosts(stockCode) 호출
async function loadStocks() {
    const stocks = await fetchStocks();
    const listEl = document.getElementById("stock-list");

    listEl.innerHTML = "";

    stocks.forEach(stock => {
        const li = document.createElement("li");
        li.textContent = `${stock.stockName} (${stock.currentPrice.toLocaleString()}원)`;
        li.dataset.code = stock.stockCode;

        li.addEventListener("click", () => {
            document.querySelectorAll("#stock-list li").forEach(el => el.classList.remove("selected"));
            li.classList.add("selected");

            currentStockCode = stock.stockCode;
            currentPostId = null;
            document.getElementById("post-content").textContent = "";
            document.getElementById("comment-list").innerHTML = "";

            loadPosts(currentStockCode);
        });

        listEl.appendChild(li);
    });
}

// fetchPosts(stockCode) 호출 후 #post-list-body에 <tr data-id="postId"> 렌더링
// 컬럼: 번호 | 제목 | 댓글수(replyCount) | 작성자 | 작성일(createdAt)
// 각 tr 클릭 시 currentPostId 갱신 + .selected 클래스 토글 + loadPostDetail(), loadComments() 호출
async function loadPosts(stockCode) {
    const posts = await fetchPosts(stockCode);
    const tbody = document.getElementById("post-list-body");

    tbody.innerHTML = "";

    posts.forEach(post => {
        const tr = document.createElement("tr");
        tr.dataset.id = post.postId;

        [post.postId, post.title, post.replyCount, post.writer, post.createdAt].forEach(value => {
            const td = document.createElement("td");
            td.textContent = value;
            tr.appendChild(td);
        });

        if (post.postId === currentPostId) {
            tr.classList.add("selected");
        }

        tr.addEventListener("click", () => {
            document.querySelectorAll("#post-table tbody tr").forEach(el => el.classList.remove("selected"));
            tr.classList.add("selected");

            currentPostId = post.postId;
            loadPostDetail(currentPostId);
            loadComments(currentPostId);
        });

        tbody.appendChild(tr);
    });
}

// fetchPostDetail(postId) 호출 후 #post-content에 "제목 + 본문" 표시
async function loadPostDetail(postId) {
    const post = await fetchPostDetail(postId);
    document.getElementById("post-content").textContent = `제목 : ${post.title}\n\n${post.content}`;
}

// fetchComments(postId) 호출 후 #comment-list에 <li>[작성자] 내용</li> 렌더링
async function loadComments(postId) {
    const comments = await fetchComments(postId);
    const listEl = document.getElementById("comment-list");

    listEl.innerHTML = "";

    comments.forEach(comment => {
        const li = document.createElement("li");
        li.textContent = `[${comment.replyWriter}] ${comment.replyContent}`;
        listEl.appendChild(li);
    });
}

// 글쓰기 모달
const writeModal = document.getElementById("write-modal");
const writeForm = document.getElementById("write-form");

document.getElementById("write-btn").addEventListener("click", () => {
    if (!currentStockCode) {
        alert("종목을 먼저 선택하세요.");
        return;
    }

    writeForm.reset();
    writeModal.showModal();
});

document.getElementById("write-cancel-btn").addEventListener("click", () => {
    writeModal.close();
});

writeForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData(writeForm);
    const title = formData.get("title").trim();
    const content = formData.get("content").trim();
    const writer = formData.get("writer").trim();
    const postPassword = formData.get("postPassword").trim();

    if (!title || !content || !postPassword) {
        alert("제목/내용/비밀번호는 필수입니다.");
        return;
    }

    const result = await createPost({
        stockCode: currentStockCode,
        title,
        content,
        writer,
        postPassword
    });

    if (result.success) {
        writeModal.close();
        loadPosts(currentStockCode);
    } else {
        alert(result.message || "등록에 실패했습니다.");
    }
});

// 삭제 버튼 -> 비밀번호 입력 후 deletePost()
document.getElementById("delete-btn").addEventListener("click", async () => {
    if (!currentPostId) {
        alert("삭제할 게시글을 선택하세요.");
        return;
    }

    const password = prompt("비밀번호 입력");
    if (password === null) return;

    const result = await deletePost(currentPostId, password);

    if (result.success) {
        currentPostId = null;
        document.getElementById("post-content").textContent = "";
        document.getElementById("comment-list").innerHTML = "";
        loadPosts(currentStockCode);
    } else {
        alert("비밀번호가 틀렸습니다.");
    }
});

// 댓글 등록
document.getElementById("comment-submit-btn").addEventListener("click", async () => {
    if (!currentPostId) {
        alert("게시글을 선택하세요.");
        return;
    }

    const writerInput = document.getElementById("comment-writer");
    const contentInput = document.getElementById("comment-content");

    const writer = writerInput.value.trim();
    const content = contentInput.value.trim();

    if (!writer || !content) {
        alert("작성자와 댓글을 입력하세요.");
        return;
    }

    const result = await createComment(currentPostId, writer, content);

    if (result.success) {
        contentInput.value = "";
        loadComments(currentPostId);
        loadPosts(currentStockCode);
    } else {
        alert(result.message || "등록에 실패했습니다.");
    }
});
