<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
<link href="css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/login_header.jsp" />
	<div class="login">
		<div class="login-triangle"></div>

		<h2 class="login-header">ログイン</h2>
		<h3>${eMsg}</h3>
		<form class="login-container" action="LoginServlet" method="POST">
			<p>
				社員番号：<input type="text" name="syainNo" placeholder="id" maxlength='10'>
			</p>
			<p>
				パスワード：<input type="password" name="password" placeholder="Password">
			</p>
			<div class="login_center">
				<input type="submit" value="Login" class="login_button">
			</div>
		</form>
	</div>

</body>
</html>