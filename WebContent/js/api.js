/**
 * 서버 API(fetch 기반 AJAX) 호출 모음.
 * 모든 함수는 Promise를 반환하며, 호출부(app.js)에서 await/then으로 사용한다.
 */

// GET /api/stocks -> 종목 목록(JSON 배열) 반환
async function fetchStocks() {
    const res = await fetch("api/stocks");
    return res.json();
}

// GET /api/posts?stockCode={stockCode} -> 게시글 목록(JSON 배열) 반환
async function fetchPosts(stockCode) {
    const res = await fetch(`api/posts?stockCode=${encodeURIComponent(stockCode)}`);
    return res.json();
}

// GET /api/posts?postId={postId} -> 게시글 상세(JSON 객체) 반환
async function fetchPostDetail(postId) {
    const res = await fetch(`api/posts?postId=${postId}`);
    return res.json();
}

// POST /api/posts (params: stockCode, title, content, writer, postPassword)
// -> { success: true|false, message? } 형태의 결과 반환
async function createPost(params) {
    const res = await fetch("api/posts", {
        method: "POST",
        body: new URLSearchParams(params)
    });
    return res.json();
}

// DELETE /api/posts?postId={postId}&password={password}
// -> { success: true|false } 형태의 결과 반환
async function deletePost(postId, password) {
    const res = await fetch(`api/posts?postId=${postId}&password=${encodeURIComponent(password)}`, {
        method: "DELETE"
    });
    return res.json();
}

// GET /api/comments?postId={postId} -> 댓글 목록(JSON 배열) 반환
async function fetchComments(postId) {
    const res = await fetch(`api/comments?postId=${postId}`);
    return res.json();
}

// POST /api/comments (params: postId, writer, content)
// -> { success: true|false, message? } 형태의 결과 반환
async function createComment(postId, writer, content) {
    const res = await fetch("api/comments", {
        method: "POST",
        body: new URLSearchParams({ postId, writer, content })
    });
    return res.json();
}
