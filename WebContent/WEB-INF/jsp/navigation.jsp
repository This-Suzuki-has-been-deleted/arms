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
		<li><div class="icon"> </div><a href="WorkServlet">勤怠確認</a></li>
		<li><div class="icon"> </div><a href="ChangeServlet">情報変更</a></li>
		<%if(!auth.equals("001")){ %>
		<li><div class="icon"> </div><a href="EmployeeServlet">社員検索</a></li>
		<li><div class="icon"> </div><a href="RegistrationServlet">社員登録</a></li>
		<%} %>
	</ul>
</div>