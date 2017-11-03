<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/home.do">우리사이트</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="${menu eq 'home' ? 'active' : '' }"><a href="/home.do">홈</a></li>
				<li class="${menu eq 'board' ? 'active' : '' }"><a href="/board/home.do">게시판</a></li>
				<li><a href="#">Page 2</a></li>
				<li><a href="#">Page 3</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
			<c:choose>
				<c:when test="${not empty LOGIN_USER }">
					<li><a href="/user/loginout.do">로그아웃</a></li>
					<li><a href="/user/my.do">내 정보</a></li>
				</c:when>
				<c:otherwise>
					<li class="${menu eq 'singup' ? 'active' : '' }" ><a href="/user/login.do">로그인</a></li>
					<li class="${menu eq 'signin' ? 'active' : '' }"><a href="/user/form.do">회원가입</a></li>
				</c:otherwise>
			</c:choose>
			</ul>
		</div>
	</nav>