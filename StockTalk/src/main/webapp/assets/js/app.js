let selectedStockCode = null;
let selectedPostId = null;
let selectedPostDetail = null;
let stockSearchTerm = '';
let stockPage = 1;
let stockTotalCount = 0;

const stockList = document.getElementById('stock-list');
const postListBody = document.getElementById('post-list-body');
const detailTitle = document.getElementById('detail-title');
const detailMeta = document.getElementById('detail-meta');
const postContent = document.getElementById('post-content');
const commentList = document.getElementById('comment-list');
const commentContent = document.getElementById('comment-content');
const commentSubmitBtn = document.getElementById('comment-submit-btn');
const writeBtn = document.getElementById('write-btn');
const editBtn = document.getElementById('edit-btn');
const deleteBtn = document.getElementById('delete-btn');
const writeModal = document.getElementById('write-modal');
const writeForm = document.getElementById('write-form');
const cancelWriteBtn = document.getElementById('write-cancel-btn');
const searchInput = document.getElementById('search-input');
const searchBtn = document.getElementById('search-btn');
const postIdField = document.getElementById('post-id');
const writeModalTitle = document.getElementById('write-modal-title');
const postPasswordField = document.getElementById('post-password-field');

window.addEventListener('DOMContentLoaded', async () => {
    await loadStocks();
    bindEvents();
});

function bindEvents() {
    editBtn.style.display = 'none';
    deleteBtn.style.display = 'none';

    writeBtn.addEventListener('click', () => openWriteModal());
    editBtn.addEventListener('click', () => openEditModal());
    cancelWriteBtn.addEventListener('click', () => closeWriteModal());
    searchBtn.addEventListener('click', () => searchStocks());
    searchInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            searchStocks();
        }
    });

    writeForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const formData = new FormData(writeForm);
        const title = formData.get('title').trim();
        const content = formData.get('content').trim();
        const postPassword = formData.get('postPassword') ? formData.get('postPassword').trim() : '';
        const postId = postIdField.value;

        if (!selectedStockCode) {
            alert('종목을 먼저 선택해주세요.');
            return;
        }
        if (!title || !content) {
            alert('제목과 내용을 입력해주세요.');
            return;
        }

        try {
            if (postId) {
                await api.updatePost({ postId: Number(postId), title, content });
                alert('게시글이 수정되었습니다.');
            } else {
                if (!postPassword) {
                    alert('비밀번호를 입력해주세요.');
                    return;
                }
                await api.createPost({ stockCode: selectedStockCode, title, content, postPassword });
                alert('게시글이 등록되었습니다.');
            }
            closeWriteModal();
            writeForm.reset();
            await loadPosts(selectedStockCode);
            if (selectedPostId) {
                const detail = await api.fetchPostDetail(selectedPostId);
                renderPostDetail(detail);
                await loadComments(selectedPostId);
            }
        } catch (error) {
            alert(error.message || '요청 처리 중 오류가 발생했습니다.');
        }
    });

    deleteBtn.addEventListener('click', async () => {
        if (!selectedPostId) {
            alert('삭제할 게시글을 선택해주세요.');
            return;
        }
        if (!confirm('선택한 게시글을 삭제하시겠습니까?')) {
            return;
        }
        try {
            await api.deletePost({ postId: selectedPostId });
            selectedPostId = null;
            selectedPostDetail = null;
            await loadPosts(selectedStockCode);
            clearDetail();
            clearComments();
            alert('삭제되었습니다.');
        } catch (error) {
            alert(error.message || '삭제 중 오류가 발생했습니다.');
        }
    });

    commentSubmitBtn.addEventListener('click', async () => {
        const content = commentContent.value.trim();

        if (!selectedPostId) {
            alert('댓글을 등록할 게시글을 먼저 선택해주세요.');
            return;
        }
        if (!content) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        try {
            await api.createComment({ postId: selectedPostId, content });
            commentContent.value = '';
            await loadComments(selectedPostId);
        } catch (error) {
            alert(error.message || '댓글 등록에 실패했습니다.');
        }
    });
}

async function openWriteModal() {
    postIdField.value = '';
    writeModalTitle.textContent = '게시글 작성';
    postPasswordField.style.display = 'block';
    postPasswordField.querySelector('input').required = true;
    writeForm.reset();
    writeModal.showModal();
}

function openEditModal() {
    if (!selectedPostId || !selectedPostDetail) {
        alert('수정할 게시글을 선택해주세요.');
        return;
    }
    postIdField.value = selectedPostDetail.postId;
    writeModalTitle.textContent = '게시글 수정';
    document.getElementById('post-title').value = selectedPostDetail.title;
    document.getElementById('post-content-area').value = selectedPostDetail.content;
    postPasswordField.style.display = 'none';
    postPasswordField.querySelector('input').required = false;
    writeModal.showModal();
}

function closeWriteModal() {
    writeModal.close();
}

function searchStocks() {
    stockSearchTerm = searchInput.value.trim();
    stockPage = 1;
    loadStocks(stockSearchTerm, stockPage);
}

async function loadStocks(search = '', page = 1) {
    stockSearchTerm = search;
    stockPage = page;
    const result = await api.fetchStocks(search, page);
    const stocks = result.stocks || [];
    stockTotalCount = result.totalCount || 0;
    stockList.innerHTML = '';

    if (stocks.length === 0) {
        stockList.innerHTML = '<li class="list-group-item text-center text-muted">검색 결과가 없습니다.</li>';
        selectedStockCode = null;
        selectedPostId = null;
        selectedPostDetail = null;
        clearDetail();
        clearComments();
        renderStockPagination();
        return;
    }

    stocks.forEach((stock) => {
        const item = document.createElement('li');
        item.className = 'list-group-item';
        item.dataset.stockCode = stock.stockCode;
        item.innerHTML = `<div class="d-flex justify-content-between align-items-center"><strong>${escapeHtml(stock.stockName)}</strong><span>${stock.currentPrice.toLocaleString()}원</span></div><small class="text-secondary">${escapeHtml(stock.stockCode)}</small>`;
        item.addEventListener('click', () => {
            selectedStockCode = stock.stockCode;
            selectStockItem(item);
            loadPosts(stock.stockCode);
        });
        stockList.appendChild(item);
    });

    renderStockPagination();

    if (stocks.length > 0) {
        const firstItem = stockList.querySelector('li');
        firstItem?.click();
    }
}

function movePage(pageIndex) {
    if (pageIndex < 1) {
        return;
    }
    loadStocks(stockSearchTerm, pageIndex);
}

function renderStockPagination() {
    makePaginationHtml(10, 5, stockPage, stockTotalCount, 'stock-pagination');
}

function selectStockItem(item) {
    stockList.querySelectorAll('li').forEach((el) => el.classList.remove('active'));
    item.classList.add('active');
}

async function loadPosts(stockCode, search = '') {
    const posts = await api.fetchPosts(stockCode, search);
    selectedPostId = null;
    selectedPostDetail = null;
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
            selectedPostDetail = post;
            postListBody.querySelectorAll('tr').forEach((el) => el.classList.remove('selected'));
            row.classList.add('selected');
            const detail = await api.fetchPostDetail(post.postId);
            selectedPostDetail = detail;
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
        item.className = 'd-flex justify-content-between align-items-start';
        const contentHtml = `<div><strong>${escapeHtml(comment.replyWriter)}</strong> <small class="text-muted">${new Date(comment.createdAt).toLocaleString()}</small><div>${escapeHtml(comment.replyContent)}</div></div>`;
        item.innerHTML = contentHtml;
        if (comment.replyWriter === currentUserName) {
            const deleteButton = document.createElement('button');
            deleteButton.type = 'button';
            deleteButton.className = 'btn btn-sm btn-outline-danger ms-2';
            deleteButton.textContent = '삭제';
            deleteButton.addEventListener('click', async () => {
                if (!confirm('댓글을 삭제하시겠습니까?')) {
                    return;
                }
                try {
                    await api.deleteComment(comment.commentId);
                    await loadComments(postId);
                } catch (error) {
                    alert(error.message || '댓글 삭제에 실패했습니다.');
                }
            });
            item.appendChild(deleteButton);
        }
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

    const isOwner = currentUserName && post.writer === currentUserName;
    editBtn.style.display = isOwner ? '' : 'none';
    deleteBtn.style.display = isOwner ? '' : 'none';
}

function clearDetail() {
    detailTitle.textContent = '';
    detailMeta.textContent = '';
    postContent.textContent = '';
    editBtn.style.display = 'none';
    deleteBtn.style.display = 'none';
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
