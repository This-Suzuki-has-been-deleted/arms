<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出退勤修正</title>
</head>
<body>
<h2>出退勤修正</h2>
<h3>${workTimeModel.year}/${workTimeModel.month}/${workTimeModel.day} </h3>
<input type="text" value="${ workTimeModel.attendance}">
<input type="text" value="${ workTimeModel.leaving}">
<form action="WorkServlet" method="get">
<input type="submit" value="戻る">
</form>
<form action="WorkModifiServlet" method="post">
<input type="submit" value="更新">
</form>
</body>
</html>