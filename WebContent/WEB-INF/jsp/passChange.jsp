<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/pass.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー情報変更</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<div class="main">
<p>${eMsg}</p>
<form action="ChangeServlet" method="post">
<table>
	<tr>
		<th class="t_top">現在パスワード</th>
		<td class="t_top"><input type="password" name="pass"></td>
	</tr>
	<tr>
		<th>新しいパスワード</th>
		<td><input type="password" name="nextPass"></td>
	</tr>
</table>
<br>
<div class="p_center"><input type="submit" value="戻る" class="pc_button"><input type="submit" value="確認" class="p_button"></div>
<div class="clear"></div>
</form>
</div>
<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
<div class="clear"></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>