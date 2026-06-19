let selectedStockCode = null;
let selectedPostId = null;

const stockList = document.getElementById('stock-list');
const postListBody = document.getElementById('post-list-body');
const detailTitle = document.getElementById('detail-title');
const detailMeta = document.getElementById('detail-meta');
const postContent = document.getElementById('post-content');
const commentList = document.getElementById('comment-list');
const commentWriter = document.getElementById('comment-writer');
const commentContent = document.getElementById('comment-content');
const commentSubmitBtn = document.getElementById('comment-submit-btn');
const writeBtn = document.getElementById('write-btn');
const deleteBtn = document.getElementById('delete-btn');
const writeModal = document.getElementById('write-modal');
const writeForm = document.getElementById('write-form');
const cancelWriteBtn = document.getElementById('write-cancel-btn');

window.addEventListener('DOMContentLoaded', async () => {
    await loadStocks();
    bindEvents();
});

function bindEvents() {
    writeBtn.addEventListener('click', () => writeModal.showModal());
    cancelWriteBtn.addEventListener('click', () => writeModal.close());

    writeForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const formData = new FormData(writeForm);
        const title = formData.get('title').trim();
        const content = formData.get('content').trim();
        const writer = formData.get('writer').trim() || '익명';
        const postPassword = formData.get('postPassword').trim();

        if (!selectedStockCode) {
            alert('종목을 먼저 선택해주세요.');
            return;
        }

        if (!title || !content || !postPassword) {
            alert('제목, 내용, 비밀번호는 필수 항목입니다.');
            return;
        }

        const result = await api.createPost({ stockCode: selectedStockCode, title, content, writer, postPassword });
        if (result === 'success') {
            writeModal.close();
            writeForm.reset();
            await loadPosts(selectedStockCode);
        } else {
            alert('게시글 작성에 실패했습니다.');
        }
    });

    deleteBtn.addEventListener('click', async () => {
        if (!selectedPostId) {
            alert('삭제할 게시글을 선택해주세요.');
            return;
        }
        const password = prompt('게시글 삭제 비밀번호를 입력하세요.');
        if (!password) {
            return;
        }
        const result = await api.deletePost({ postId: selectedPostId, password });
        if (result === 'success') {
            selectedPostId = null;
            await loadPosts(selectedStockCode);
            clearDetail();
            clearComments();
            alert('삭제되었습니다.');
        } else {
            alert('비밀번호가 틀렸거나 삭제에 실패했습니다.');
        }
    });

    commentSubmitBtn.addEventListener('click', async () => {
        const writer = commentWriter.value.trim() || '익명';
        const content = commentContent.value.trim();

        if (!selectedPostId) {
            alert('댓글을 등록할 게시글을 먼저 선택해주세요.');
            return;
        }
        if (!content) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        const result = await api.createComment({ postId: selectedPostId, writer, content });
        if (result === 'success') {
            commentContent.value = '';
            commentWriter.value = '';
            await loadComments(selectedPostId);
        } else {
            alert('댓글 등록에 실패했습니다.');
        }
    });
}

async function loadStocks() {
    const stocks = await api.fetchStocks();
    stockList.innerHTML = '';

    stocks.forEach((stock) => {
        const item = document.createElement('li');
        item.className = 'list-group-item';
        item.dataset.stockCode = stock.stockCode;
        item.innerHTML = `<div class="d-flex justify-content-between align-items-center"><strong>${stock.stockName}</strong><span>${stock.currentPrice.toLocaleString()}원</span></div><small class="text-secondary">${stock.stockCode}</small>`;
        item.addEventListener('click', () => {
            selectedStockCode = stock.stockCode;
            selectStockItem(item);
            loadPosts(stock.stockCode);
        });
        stockList.appendChild(item);
    });

    if (stocks.length > 0) {
        const firstItem = stockList.querySelector('li');
        firstItem?.click();
    }
}

function selectStockItem(item) {
    stockList.querySelectorAll('li').forEach((el) => el.classList.remove('active'));
    item.classList.add('active');
}

async function loadPosts(stockCode) {
    const posts = await api.fetchPosts(stockCode);
    selectedPostId = null;
    clearDetail();
    clearComments();
    postListBody.innerHTML = '';

    posts.forEach((post) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${post.postId}</td>
            <td>${escapeHtml(post.title)}</td>
            <td>${escapeHtml(post.writer)}</td>
            <td>${post.replyCount || 0}</td>
            <td>${new Date(post.createdAt).toLocaleString()}</td>
        `;
        row.addEventListener('click', async () => {
            selectedPostId = post.postId;
            postListBody.querySelectorAll('tr').forEach((el) => el.classList.remove('selected'));
            row.classList.add('selected');
            const detail = await api.fetchPostDetail(post.postId);
            renderPostDetail(detail);
            await loadComments(post.postId);
        });
        postListBody.appendChild(row);
    });
}

async function loadComments(postId) {
    const comments = await api.fetchComments(postId);
    commentList.innerHTML = '';
    comments.forEach((comment) => {
        const item = document.createElement('li');
        item.innerHTML = `<strong>${escapeHtml(comment.replyWriter)}</strong> <small class="text-muted">${new Date(comment.createdAt).toLocaleString()}</small><div>${escapeHtml(comment.replyContent)}</div>`;
        commentList.appendChild(item);
    });
}

function renderPostDetail(post) {
    if (!post) {
        clearDetail();
        return;
    }
    detailTitle.textContent = post.title;
    detailMeta.textContent = `${post.writer} · ${new Date(post.createdAt).toLocaleString()}`;
    postContent.textContent = post.content;
}

function clearDetail() {
    detailTitle.textContent = '';
    detailMeta.textContent = '';
    postContent.textContent = '';
}

function clearComments() {
    commentList.innerHTML = '';
}

function escapeHtml(text) {
    return String(text)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}
