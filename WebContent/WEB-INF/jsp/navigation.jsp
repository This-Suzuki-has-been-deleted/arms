<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.EmployeeModel" %>
<%
	EmployeeModel em = (EmployeeModel)session.getAttribute("employee");
	String auth = em.getAuthNo();
%>

<div class="side">
	<ul class=sidelist>
		<li><a href="work.jsp">勤怠確認</a></li>
		<li><a href="employeeupdate.jsp">情報変更</a></li>
		<%if(auth.equals("003")||auth.equals("999")){ %>
		<li><a href="employeesearch.jsp">社員検索</a></li>
		<li><a href="employeeregistration.jsp">社員登録</a></li>
		<%} %>
	</ul>
</div>