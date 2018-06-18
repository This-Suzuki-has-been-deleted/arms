<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出退勤修正</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/work.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="main">
		<h2>出退勤修正</h2>
		<h3>${workTimeModel.year}/${workTimeModel.month}/${workTimeModel.day}
		</h3>
		<div class="">
		<form action="WorkModifiServlet" method="post">
			<table>
				<tr>
					<th class="t_top">出勤時刻</th>
					<td class="t_top"><input type="text" value="<fmt:formatDate value="${ workTimeModel.attendance}" pattern="yyyy年MM月dd日 HH時mm分"/>"name="attendance"></td>
				</tr>
				<tr>
					<th>退勤時刻</th>
					<td class="t_top"><input type="text" value="<fmt:formatDate value="${ workTimeModel.leaving}" pattern="yyyy年MM月dd日 HH時mm分"/>"name="leaving"></td>
				</tr>
			</table>
			<div class="w_center">
				<input type="submit" value="更新" class="w_button">
			</div>
		</form>
		<form action="WorkServlet" method="get">
			<div class="w_center">
			<input type="submit" value="戻る" class="wc_button">
			</div>
		</form>
		<div class="clear"></div>
		</div>


	</div>
	<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
	<div class="clear"></div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>