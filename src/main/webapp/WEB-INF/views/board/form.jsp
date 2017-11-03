<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<!DOCTYPE>
<html>
<head>
<title>Insert title here</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="menu" value="board" />
<%@ include file="/WEB-INF/views/include/navi.jsp" %>
	<div class="container">
		<h1>게시글 폼</h1>
		
		<form method="post" action="add.do" enctype="multipart/form-data">
			<div class="form-group">
				<label>제목</label>
				<input type="text" class="form-control" id="title" name="title">
			</div>
			<div class="form-group">
				<label>첨부파일</label>
				<input type="file" class="form-control" id="attachedfile" name="attachedfile">
			</div>
			<div class="form-group">
				<label>내용</label>
				<textarea rows="5" class="form-control" id="contents" name="contents"></textarea>
			</div>
			<div class="form-group text-right">
				<button type="submit" class="btn btn-sm btn-primary">저장</button>
				<a href="home.do" class="btn btn-sm btn-default">취소</a>			
			</div>
		</form>
	</div>
</body>
</html>