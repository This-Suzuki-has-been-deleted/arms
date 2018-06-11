<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String cngdate = "";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出退勤確認</title>
</head>
<body>
<button type="submit" name="y_btn" value="${ANNUAL.year} -1"method="GET">前年</button>
<button type="submit" name="y_btn" value="${ANNUAL.year} + 1"method="GET">次年</button>
	<table>
		<tr>
			<th>年（今年度）</th>
			<th>深夜勤務合計時間</th>
			<th>残業合計時間</th>
			<th>総勤務合計時間</th>
		</tr>
		<tr>
			<td>${ANNUAL.year}</td>
			<td>${ANNUAL.y_workTime}</td>
			<td>${ANNUAL.y_overTime}</td>
			<td>${ANNUAL.y_nightTime}</td>
		</tr>
	</table>

	<br>
	<h3></h3>
<button type="submit" name="m_btn" value="${MOUNTHLY.year} - 1" method="GET">前月</button>
<button type="submit" name="m_btn" value="${MOUNTHLY.year} + 1" method="GET">次月</button>
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
		<c:forEach var="wtime" items="${Worktime}">
			<tr>
				<td>${wtime.day}</td>
				<td>リストに曜日がはいってる。鈴木悟めも.txt参照</td>
				<td>${wtime.attendance}</td>
				<td>${wtime.leaving}</td>
				<td>${wtime.}</td>
				<input type="image" class="wtimeEdit" src="/WEB-INF/images/wtimeedit.png">
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
			<td>${MOUNTHLY.month}</td>
			<td>${MOUNTHLY.m_workTime}</td>
			<td>${MOUNTHLY.m_overTime}</td>
			<td>${MOUNTHLY.m_nightTime}</td>
		</tr>
	</table>


</body>
</html>