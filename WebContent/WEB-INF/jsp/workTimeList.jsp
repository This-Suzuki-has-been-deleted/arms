<%@page import="model.WorkTimeModel"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String cngdate = "";

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出退勤確認</title>
</head>
<body>
	<form action="workServlet" method="GET">
		<button type="submit" name="y_btn" value="${ANNUAL.year - 1}">前年</button>
		<button type="submit" name="y_btn" value="${ANNUAL.year + 1}">次年</button>
	</form>
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
	<form action="workServlet" method="GET">
		<button type="submit" name="m_btn" value="${MOUNTHLY.year - 1}">前月</button>
		<button type="submit" name="m_btn" value="${MOUNTHLY.year + 1}">次月</button>
	</form>
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
				<td>${wtime.week}</td>
				<td>${wtime.attendance}</td>
				<td>${wtime.leaving}</td>
				<td>${wtime.nightTimeH}:${wtime.nightTimeM }</td>
				<td>${wtime.overTimeH}:${wtime.overTimeM }</td>
				<td>${wtime.workTimeH}:${wtime.workTimeM }</td>
				<td><form action="WorkServlet" method="POST">
					<input type="hidden" value="${wtime.employeeNo}" name="wtm.employeeNo" />
					<input type="hidden" value="${wtime.year }" name="wtm.year"/>
					<input type="hidden" value="${wtime.month }" name="wtm.month"/>
					<input type="hidden" value="${wtime.day }" name="wtm.day"/>
					<input type="image" src="/WEB-INF/images/wtimeedit.png">
				</form></td>
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