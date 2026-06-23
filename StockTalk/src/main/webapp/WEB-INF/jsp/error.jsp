<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>에러 발생</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous" />
</head>
<body>
<div class="container mt-5">
    <div class="card">
        <div class="card-header bg-danger text-white">
            <h5 class="mb-0">오류가 발생했습니다.</h5>
        </div>
        <div class="card-body">
            <p><strong>메시지:</strong></p>
            <pre>${message}</pre>
            <a href="/pages/board" class="btn btn-primary">게시판으로 돌아가기</a>
        </div>
    </div>
</div>
</body>
</html>
