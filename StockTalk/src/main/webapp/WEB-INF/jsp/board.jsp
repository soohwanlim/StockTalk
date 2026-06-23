<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>StockTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <style>
        body { background-color: #f5f7fa; }
        #stock-list li { cursor: pointer; }
        #stock-list li.active { background-color: #0d6efd; color: #fff; }
        #post-table tbody tr.selected { background-color: #f1f3f5; }
        #comment-list { list-style: none; padding-left: 0; }
        #comment-list li { padding: .75rem; border-bottom: 1px solid #dee2e6; }
        #comment-list li:last-child { border-bottom: none; }
        dialog { width: 100%; max-width: 520px; border: none; border-radius: .5rem; }
    </style>
</head>
<body>
<div class="container-fluid mt-3">
    <div class="row g-3">
        <div class="col-xl-3 col-md-4">
            <div class="card">
                <div class="card-header">종목 목록</div>
                <div class="card-body p-0">
                    <ul class="list-group list-group-flush" id="stock-list"></ul>
                </div>
                <div class="card-footer">
                    <nav id="stock-pagination"></nav>
                </div>
            </div>
        </div>
        <div class="col-xl-6 col-md-8">
            <div class="card mb-3">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <span>게시글</span>
                    <div class="btn-group" role="group">
                        <a class="btn btn-sm btn-outline-secondary" href="/pages/logout">로그아웃</a>
                        <button class="btn btn-sm btn-primary" id="write-btn">글쓰기</button>
                        <button class="btn btn-sm btn-secondary" id="edit-btn">수정</button>
                        <button class="btn btn-sm btn-danger" id="delete-btn">삭제</button>
                    </div>
                </div>
                <div class="card-body p-3">
                    <div class="mb-3">
                        <div class="input-group">
                            <input type="text" class="form-control" id="search-input" placeholder="종목 검색">
                            <button class="btn btn-outline-secondary" id="search-btn" type="button">종목 검색</button>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover mb-0" id="post-table">
                            <thead>
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>댓글</th>
                                <th>작성일</th>
                            </tr>
                            </thead>
                            <tbody id="post-list-body"></tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header">게시글 상세</div>
                <div class="card-body">
                    <h5 id="detail-title"></h5>
                    <p class="text-muted" id="detail-meta"></p>
                    <div id="post-content" style="white-space: pre-wrap; word-break: break-word;"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3">
            <div class="card">
                <div class="card-header">댓글</div>
                <div class="card-body">
                    <ul id="comment-list"></ul>
                    <div class="mt-3">
                        <textarea class="form-control mb-2" id="comment-content" rows="3" placeholder="댓글을 입력하세요."></textarea>
                        <button class="btn btn-success w-100" id="comment-submit-btn">등록</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<dialog id="write-modal">
    <form id="write-form">
        <input type="hidden" name="postId" id="post-id">
        <h5 class="mb-3" id="write-modal-title">게시글 작성</h5>
        <input type="text" id="post-title" name="title" class="form-control mb-2" placeholder="제목" required>
        <textarea id="post-content-area" name="content" class="form-control mb-2" rows="5" placeholder="내용" required></textarea>
        <div id="post-password-field">
            <input type="password" name="postPassword" class="form-control mb-3" placeholder="비밀번호" required>
        </div>
        <div class="d-flex justify-content-end gap-2">
            <button type="button" class="btn btn-secondary" id="write-cancel-btn">취소</button>
            <button type="submit" class="btn btn-primary">저장</button>
        </div>
    </form>
</dialog>

<script>
<%
    String currentUserName = "";
    com.mycom.myapp.user.dto.UserDto userDto = (com.mycom.myapp.user.dto.UserDto) session.getAttribute("userDto");
    if (userDto != null) {
        currentUserName = userDto.getUserName();
    }
%>
    const currentUserName = '<%= currentUserName.replace("'", "\\'") %>';
</script>
<script src="/assets/js/util.js"></script>
<script src="/assets/js/api.js"></script>
<script src="/assets/js/app.js"></script>
</body>
</html>
