<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー情報変更</title>
</head>
<body>
<p>"${eMsg}"</p>
<form action="ChangeServlet" method="post">
現在パスワード<input type="password" name="pass">
<br>
新しいパスワード<input type="password" name="nextPass">
<br>
<input type="submit" value="戻る"><input type="submit" value="確認">
</form>
</body>
</html>