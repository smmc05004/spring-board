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
		<div class="row">
			<div class="col-sm-3">
				<h1>게시글 목록</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-offset-3 col-sm-8">
				<form action="home.do" class="form-inline pull-right" id="search-form">
					<!-- 페이지번호 전달 -->
					<input type="hidden" name="pageNo" value="${param.pageNo }"/>
					<!-- rows값 전달 -->
					<input type="hidden" name="rows" value="${param.rows }"/>
					<div class="form-group">
						<label class="sr-only">검색조건</label>
						<select class="form-control" name="opt">
							<option value="title" ${param.opt eq 'title' ? 'selected' : '' }>제목</option>
							<option value="writer" ${param.opt eq 'writer' ? 'selected' : '' }>작성자</option>
							<option value="contents" ${param.opt eq 'contents' ? 'selected' : '' }>내용</option>
						</select>
					</div>
					<div class="form-group">
						<label class="sr-only">키워드</label>
						<input type="text" class="form-control"  name="keyword" value="${param.keyword }"/>
					</div>
					<button type="submit" class="btn btn-primary" id="btn-search">조회</button>
				</form>
			</div>
			<div class="col-sm-1">
				<a href="home.do" class="btn btn-default btn-sm">초기화</a>
			</div>
		</div>
		
		<table class="table table-condensed">
			<colgroup>
				<col width="5%">
				<col width="*%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>등록일</th>
					<th>추천수</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${empty boards }">
				<tr>
					<td colspan="5" class="text-center">검색된 결과가 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach var="board" items="${boards }">
				<!-- 쿼리스트링 파라미터 설정 -->
				<c:url var="detailURL" value="detail.do" >
					<c:param name="no" value="${board.no }" />
				</c:url>
				<tr>
					<td>${board.no } </td>
					<td><a href="${detailURL }"><c:out value="${board.title }" /></a></td>
					<td>${board.writer.fullname }</td>
					<td><fmt:formatDate value="${board.createdate }" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatNumber value="${board.likes }" pattern="#,###"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<div class="row col-sm-2">
			<form>
				<select id="rows-box">
					<option value="5" ${param.rows eq 5 ? 'selected' : '' }>5개씩 보기</option>
					<option value="10" ${param.rows eq 10 ? 'selected' : '' }>10개씩 보기</option>
					<option value="20" ${param.rows eq 20 ? 'selected' : '' }>15개씩 보기</option>
				</select>
			</form>
		</div>
		
		<%@ include file="/WEB-INF/views/include/pagination.jsp" %>
	
		<div class="text-right">
			<a href="form.do" class="btn btn-sm btn-primary">새 글</a>
			<a href="exceldownload.do" class="btn btn-sm btn-success">엑셀 다운</a>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function () {
		
		$("#rows-box").change(function() {
			$(':input[name=rows]').val($(this).val());
			$(':input[name=pageNo]').val(1);
			$('#search-form').submit();
		});
		
		$('.pagination a').click(function(event) {
			event.preventDefault();
			$(':input[name=pageNo]').val($(this).attr('href')); // $(this) -> 내가 클릭한 것
			$(':input[name=rows]').val($("#rows-box").val()); // $(this) -> 내가 클릭한 것
			$('#search-form').submit();
		});
		
		$('#btn-search').click(function() {
			$(':input[name=pageNo]').val(1);
			$(':input[name=rows]').val($("#rows-box").val());
		});
		
	});
</script>
</html>