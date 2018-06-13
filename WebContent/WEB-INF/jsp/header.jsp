<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.EmployeeModel"%>
<%@page import="java.util.Date"%>
<%
	String pageTitle = (String) session.getAttribute("pageTitle");
	if(pageTitle == null){
		pageTitle = "";
	}
	EmployeeModel em = (EmployeeModel) session.getAttribute("Employee");
	String name = "";
	if(em==null){
		name="ログインできていない";
	}else{
		name = em.getEmployeeName();
	}
%>

<!-- ヘッダー全体 -->
<div class="gray">
	<div class="title">
		<a href="index.jsp"><img src="images/ARMS.png" alt="title" border="0"></a>
	</div>
	<div class="pagetitle">
		<p><%= pageTitle%></p>
	</div>
	<div class="clock">
		<%=name %>
		<p id="RealtimeClockArea">
	</div>
</div>
<div class="clear"></div>


<script>
	function showClock1() {
		var nowTime = new Date();
		var nowHour = nowTime.getHours();
		var nowMin = nowTime.getMinutes();
		var nowSec = nowTime.getSeconds();
		var msg = nowHour + ":" + nowMin + ":" + nowSec;
		document.getElementById("RealtimeClockArea").innerHTML = msg;
	}
	setInterval('showClock1()', 1000);
</script>