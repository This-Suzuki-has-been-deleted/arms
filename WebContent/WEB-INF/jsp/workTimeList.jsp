<%@page import="model.WorkTimeModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.WorkTimeDateModel" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String cngdate = "";
	String Msg = (String)session.getAttribute("Msg");
	session.removeAttribute("Msg");
	WorkTimeDateModel Time = (WorkTimeDateModel)session.getAttribute("WorkTimeDate");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出退勤確認</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/work.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="main">
	<h3 style="color:red">${eMsg }</h3><h3><c:out value="<%=Msg%>"></c:out></h3>
	<h2>出退勤一覧</h2>
	<form action="WorkServlet" method="GET">
	<%  %>
		<button type="submit" name="y_btn" value="-1" class="wl2_button">前年</button>
		<button type="submit" name="y_btn" value="1" class="wl_button">次年</button>
	<% %>
	</form>
	<table>
		<tr>
			<th class="t_top3">年（今年度）</th>
			<th class="t_top3">深夜勤務合計時間</th>
			<th class="t_top3">残業合計時間</th>
			<th class="t_top3">総勤務合計時間</th>
		</tr>
		<%if(Time != null){ %>
		<tr>
			<td>${ANNUAL.year}</td>

			<td><fmt:formatNumber value="<%=(int)(Time.getY_nightTime()/60)%>" pattern="###時間" maxFractionDigits="0" />
				<fmt:formatNumber value="<%=(int)(Time.getY_nightTime()%60)%>" pattern="##分" /></td>
			<td><fmt:formatNumber value="<%=(int)(Time.getY_overTime()/60)%>" pattern="###時間" maxFractionDigits="0" />
				<fmt:formatNumber value="<%=(int)(Time.getY_overTime()%60) %>" pattern="##分" /></td>
			<td><fmt:formatNumber value="<%=(int)(Time.getY_workTime()/60) %>" pattern="###時間" maxFractionDigits="0" />
				<fmt:formatNumber value="<%=(int)(Time.getY_workTime()%60) %>" pattern="##分" /></td>
		</tr>
		<%} %>
	</table>
	<br>
	<h3>${now_year}年  ${now_month}月</h3>
	<form action="WorkServlet" method="GET">
		<button type="submit" name="m_btn" value="-1" class="wl2_button">前月</button>
		<button type="submit" name="m_btn" value="1" class="wl_button">次月</button>
	</form>
	<div class="scroll_bar">
	<table>
		<tr>
			<th class="t_top3">日付</th>
			<th class="t_top3">曜日</th>
			<th class="t_top3">出勤</th>
			<th class="t_top3">退勤</th>
			<th class="t_top3">深夜勤務時間</th>
			<th class="t_top3">残業時間</th>
			<th class="t_top3">総勤務時間</th>
			<th class="t_top3">編集</th>
		</tr>
		<c:forEach var="wtime" items="${Worktime}">
			<tr>
				<td>${wtime.day}</td>
				<td>${wtime.week}</td>
				<td><fmt:formatDate value="${wtime.attendance}" pattern="yyyy年MM月dd日HH時mm分"/></td>
				<td><fmt:formatDate value="${wtime.leaving}" pattern="yyyy年MM月dd日HH時mm分"/></td>
				<td>${wtime.nightTimeH}時間${wtime.nightTimeM }分</td>
				<td>${wtime.overTimeH}時間${wtime.overTimeM }分</td>
				<td>${wtime.workTimeH}時間${wtime.workTimeM }分</td>
				<td><form action="WorkServlet" method="POST">
					<input type="hidden" value="${wtime.employeeNo}" name="wtm.employeeNo" />
					<input type="hidden" value="${wtime.year }" name="wtm.year"/>
					<input type="hidden" value="${wtime.month }" name="wtm.month"/>
					<input type="hidden" value="${wtime.day }" name="wtm.day"/>
					<input type="image" src="images/icon.png" alt="送信する">
				</form></td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<br>
	<h3>当月勤務時間</h3>
	<table class="bottom">
		<tr>
			<th class="t_top3">月（当月）</th>
			<th class="t_top3">深夜勤務合計時間</th>
			<th class="t_top3">残業合計時間</th>
			<th class="t_top3">総勤務合計時間</th>
		</tr>
		<%if(Time != null){ %>
		<tr>
			<td>${MOUNTHLY.month}</td>
			<td><fmt:formatNumber value="<%=(int)(Time.getM_nightTime()/60)%>" pattern="###時間" maxFractionDigits="0" />
				<fmt:formatNumber value="<%=(int)(Time.getM_nightTime()%60)%>" pattern="##分" /></td>
			<td><fmt:formatNumber value="<%=(int)(Time.getM_overTime()/60)%>" pattern="###時間" maxFractionDigits="0" />
				<fmt:formatNumber value="<%=(int)(Time.getM_overTime()%60) %>" pattern="##分" /></td>
			<td><fmt:formatNumber value="<%=(int)(Time.getM_workTime()/60) %>" pattern="###時間" maxFractionDigits="0" />
				<fmt:formatNumber value="<%=(int)(Time.getM_workTime()%60) %>" pattern="##分" /></td>
		</tr>
		<%} %>
	</table>
	</div>
	<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
	<div class="clear"></div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>