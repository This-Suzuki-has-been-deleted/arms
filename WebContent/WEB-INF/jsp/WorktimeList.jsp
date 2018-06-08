<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出退勤確認</title>
</head>
<body>
<button type="button" name="y_back_btn" value="-1">前年</button>
<button type="button" name="y_next_btn" value="1">次年</button>
	<table>
		<tr>
			<th>年（今年度）</th>
			<th>深夜勤務合計時間</th>
			<th>残業合計時間</th>
			<th>総勤務合計時間</th>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>

	<br>
	<h3></h3>
	<button type="button" name="m_back_btn" value="-1">前月</button>
<button type="button" name="m_next_btn" value="1">次月</button>
	<table>
		<tr>
			<th>日付</th>
			<th>曜日</th>
			<th>出勤</th>
			<th>退勤</th>
			<th>深夜勤務時間</th>
			<th>残業時間</th>
			<th>総勤務時間</th>
		</tr>
		<c:forEach var="battlehis" items="${TR.hislist}">
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</c:forEach>
	</table>

	<br>
	<h3>当月勤務時間</h3>
	<table>
		<tr>
			<th>月（当月）</th>
			<th>深夜勤務合計時間</th>
			<th>残業合計時間</th>
			<th>総勤務合計時間</th>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>


</body>
</html>