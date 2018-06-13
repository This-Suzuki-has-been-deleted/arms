<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.EmployeeModel" %>
<%
	EmployeeModel em = (EmployeeModel)session.getAttribute("Employee");
	String auth = "";
	if(em != null){
		auth = em.getAuthNo();
	}else{
		auth = "001";
	}
%>

<div class="side">
	<ul class=sidelist>
		<li><a href="WorkServlet">勤怠確認</a></li>
		<li><a href="ChangeServlet">情報変更</a></li>
		<%if(!auth.equals("001")){ %>
		<li><a href="EmployeeServlet">社員検索</a></li>
		<li><a href="RegistrationServlet">社員登録</a></li>
		<%} %>
	</ul>
</div>