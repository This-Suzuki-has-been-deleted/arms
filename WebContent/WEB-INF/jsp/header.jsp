<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.EmployeeModel"%>
<%@page import="java.util.Date"%>
<%
	String pageTitle = (String) session.getAttribute("pageTitle");
	EmployeeModel em = (EmployeeModel) session.getAttribute("emp");
%>

<!-- ヘッダー全体 -->
<div class="header">
<header>
	<!-- 後々画像に差し替え。クリックでindexに遷移 -->
	<div class="title">
		<a href="index.jsp">ARMS</a>
	</div>

	<!-- ページタイトルを受け取って表示する -->
	<div class="page_title"><%=pageTitle%></div>

	<!-- 右上の時計及び名前表示 -->
	<div class="Employeedetails">
		<p id="RealtimeClockArea">
		<p><%=em.getEmployeeName() %></p>
	</div>
</header>
</div>

<script>
	function showClock1() {
		var nowTime = new Date();
		var nowHour = nowTime.getHours();
		var nowMin = nowTime.getMinutes();
		var nowSec = nowTime.getSeconds();
		var msg = "現在時刻は、" + nowHour + ":" + nowMin + ":" + nowSec + " です。";
		document.getElementById("RealtimeClockArea").innerHTML = msg;
	}
	setInterval('showClock1()', 1000);
</script>