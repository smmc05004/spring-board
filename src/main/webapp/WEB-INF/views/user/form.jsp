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
<c:set var="menu" value="signup" />
<%@ include file="/WEB-INF/views/include/navi.jsp" %>
	<div class="container">
		<h1>Register form</h1>
		
		<div class="alert alert-danger" id="error-message-box">
			<strong>Error!</strong> <span id="error-message"></span> 
		</div>
		<!-- form url의 form 사용하기 위해 name을 path로 변경 / 페이지 소스 열면 name으로 변경되어 있어-->
		<!-- commandName -> userController 클래스의 form메소드의 model안에 담은 이름 -->
		<form:form id="register-form" method="post" action="add.do" class="well" commandName="userForm">
			<div class="form-group">
				<label>Name</label>
				<form:input type="text" class="form-control" id="fullname" path="fullname"  />
				<form:errors path="fullname" cssClass="text-danger"></form:errors>
			</div>
			<div class="form-group">
				<label>Id</label>
				<form:input type="text" class="form-control" id="id" path="id" />
				<form:errors path="id" cssClass="text-danger"></form:errors>
				<span class="help-block" id="id-help-block"></span>
			</div>
			<div class="form-group">
				<label>Password</label>
				<form:password class="form-control" id="password" path="password" />
				<form:errors path="password" cssClass="text-danger"></form:errors>
			</div>
			<div class="form-group">
				<label>Birth</label>
				<form:input type="date" class="form-control" id="birth" path="birth" />
				<form:errors path="birth" cssClass="text-danger"></form:errors>
			</div>
			<div class="form-group">
				<label>Email</label>
				<form:input type="text" class="form-control" id="email" path="email" />
				<form:errors path="email" cssClass="text-danger"></form:errors>
			</div>
			<div class="form-group">
				<label>Phone</label>
				<form:input type="text" class="form-control" id="phone" path="phone" />
				<form:errors path="phone" cssClass="text-danger"></form:errors>
			</div>
			<div class="form-group text-right">
				<button type="submit" class="btn btn-primary btn-sm">Register</button>
				<a href="home.do" class="btn btn-default btn-sm">Cancel</a>
			</div>
		</form:form>
	</div>
</body>
<script type="text/javascript">

$(function() {
	
	var idcheckpassd = false;
	
	var nameExp = /^[가-힣]{2,}$/;
	var idPwdExp = /^[a-zA-Z0-9]{4,20}$/;
	var emailExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	var phoneExp = /^01[016789]-\d{3,4}-\d{4}$/;
	
	$("#error-message-box").hide();
	
	$("#id").keyup(function() {
		var userid = $(this).val()
		if (!idPwdExp.test(userid)) {
			return;
		}
		
		$.ajax({
			type:"GET",
			url:"idcheck.do",
			data:{id:userid},
			dataType:"json",
			success: function(result) {
				//result에 응답 결과가 있음
				//console.log(result);
				// 객체는 .연산자로 접근 가능
				var used = result.used;
				if (used) {
					$("#id-help-block").removeClass('text-success')
					.addClass('text-danger').text("이미 사용중인 아이디 입니다.");
					idcheckpassd = false;
				} else {
					$("#id-help-block").removeClass('text-danger')
					.addClass('text-success').text("사용 가능한 아이디 입니다.");
					idcheckpassd = true;
				}
			}
		});
	});
	
	$("#register-form").submit(function() {
		
		if (!$.trim($("#fullname").val())) {
			$("#error-message").text("이름은 필수입력값입니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!nameExp.test($("#fullname").val())) {
			$("#error-message").text("이름은 2글자이상 한글만 가능합니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!$.trim($("#id").val())) {
			$("#error-message").text("아이디는 필수입력값입니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!idPwdExp.test($("#id").val())) {
			$("#error-message").text("아이디는 6~20글자로 영문자, 숫자만 가능합니다.");
			$("#error-message-box").show();
			return false;
		}
		if(!idcheckpassd) {
			$("#error-message").text("아이디 중복검사를 통과하지 못했습니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!$.trim($("#password").val())) {
			$("#error-message").text("비밀번호는 필수입력값입니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!idPwdExp.test($("#password").val())) {
			$("#error-message").text("비밀번호는 6~20글자로 영문자, 숫자만 가능합니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!$.trim($("#birth").val())) {
			$("#error-message").text("생일은 필수입력값입니다.");
			$("#error-message-box").show();
			return false;
		}
		
		if (!$.trim($("#email").val())) {
			$("#error-message").text("이메일은 필수입력값입니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!emailExp.test($("#email").val())) {
			$("#error-message").text("유효한 이메일 주소형식이 아닙니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!$.trim($("#phone").val())) {
			$("#error-message").text("전화번호는 필수입력값입니다.");
			$("#error-message-box").show();
			return false;
		}
		if (!phoneExp.test($("#phone").val())) {
			$("#error-message").text("유효한 전화번호 형식이 아닙니다.");
			$("#error-message-box").show();
			return false;
		}
		
		return true;
	});
})

</script>
</html>











