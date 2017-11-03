<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<!DOCTYPE>
<html>
<head>
<title>게시판</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="menu" value="board" />
<%@ include file="/WEB-INF/views/include/navi.jsp"%>
<div class="container">
	<h1>게시글</h1>
	
	<table class="table table-condensed">
		<colgroup>
			<col width="10%">
			<col width="40%">
			<col width="10%">
			<col width="40%">
		</colgroup>
		<tbody>
			<tr>
				<th>제목</th><td colspan="3"><c:out value="${board.title }" /> </td>
			</tr>
			<tr>
				<th>번호</th><td>${board.no }</td>
				<th>추천수</th><td>${board.likes }</td>
			</tr>
			<tr>
				<th>작성자</th><td>${board.writer.fullname }</td>
				<th>등록일</th><td> <fmt:formatDate value="${board.createdate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
					${fn:substring(board.filename, 13, fn:length(board.filename)) }
					<!-- 파일의 번호 혹은 글 번호를 parameter로 설정 -->
					<c:if test="${not empty board.filename }">
						<a href="filedownload.do?no=${board.no }" class="btn btn-default btn-xs">다운</a>
					</c:if>
					
					<%--  파일 이름 클릭하면 다운
					<c:if test="${not empty board.filename }">
						<a href="filedownload.do?no=${board.no }// 글 no 대신 파일 no 입력" >${fn:substring(board.filename, 13, fn:length(board.filename)) }</a>
					</c:if>
					 --%>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><c:out value="${board.contents }" /> </td>
			</tr>
		</tbody>
	</table>
	<div class="text-right">
		<a href="home.do" class="btn btn-sm btn-primary">글 목록</a>
	</div>
	<hr>
	<div class="row well">
		<form method="post" class="form-horizontal">
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-9">
					<input type="text" class="form-control" name="contents" />
				</div>
				<div class="col-sm-1">
					<button type="submit" class="btn btn-primary btn-sm" id="btn-add-comment">등록</button>
				</div>
			</div>
		</form>
		
		<div>
			<table class="table table-condensed" id="table-comments">
				<colgroup>
					<col width="*">
					<col width="20%">
					<col width="20%">
					<col width="5%">
				</colgroup>
				<thead>
					<tr>
						<th>내용</th>
						<th>날짜</th>
						<th>작성자</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		
	</div>
</div>
</body>
<script type="text/javascript">
	$(function() {
		
		$.ajax({
			type:"GET",
			url:"getComments.do",
			data:{boardNo:'${board.no}'},
			dataType:'json',
			success:function(comments) {
				// console.log(comments);
				var html="";
				$.each(comments, function(index, comment) {
					html += "<tr>";
					html += "<td>"+comment.contents+"</td>";
					html += "<td>"+comment.createdate+"</td>";
					html += "<td>"+comment.writer.fullname+"</td>";
					html += "<td><button class='btn btn-danger btn-xs' id='btn-del-comment-"+comment.no+"'>삭제</button></td>";
					html += "</tr>";
				});
				
				$("#table-comments tbody").html(html);
				
				$(":input[name='contents']").val("");
			}
		});
		
		$("#btn-add-comment").click(function (event) {
			// console.log("버튼클릭");
			event.preventDefault();
			
			$.ajax({
				type:"POST",
				url:"addComment.do",
				data:{contents:$(":input[name='contents']").val(), boardNo:'${board.no}'},
				dataType:"json",
				success:function(comment) {
					// console.log(comment)
					var html ="<tr>";
					html += "<td>"+comment.contents+"</td>";
					html += "<td>"+comment.createdate+"</td>";
					html += "<td>"+comment.writer.fullname+"</td>";
					html += "<td><button class='btn btn-danger btn-xs' id='btn-del-comment-"+comment.no+"'>삭제</button></td>";
					html += "</tr>";
					
					$("#table-comments tbody").prepend(html);
					
					$(":input[name='contents']").val("");
					
				}
			});
		});
		
		$("#table-comments tbody").on('click', 'button[id^=btn-del-comment-]', function() {
		console.log("this-1,", this)
			var button = this;
			var cNo = $(button).attr('id').replace('btn-del-comment-', '');
			// alert(commentNo);
			
			$.ajax({
				type:"GET",
				url:"delComment.do",
				data:{commentNo:cNo},
				dataType:"json",
				success:function(comment) {
					console.log("this-2", this);
					//$(this)
					$(button).closest("tr").remove();
				}
				
			});
		});
	});
</script>
</html>