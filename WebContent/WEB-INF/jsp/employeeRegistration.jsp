<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.EmployeeModel"%>
<%@ page import="model.DepModel"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	EmployeeModel myEmp = (EmployeeModel) session
			.getAttribute("Employee");
	@SuppressWarnings("unchecked")
	ArrayList<DepModel> depList = (ArrayList<DepModel>) session.getAttribute("depList");
	String eMsg = (String) session.getAttribute("eMsg");
	String msg = (String) session.getAttribute("Msg");
	session.removeAttribute("eMsg");
	session.removeAttribute("Msg");
	if(eMsg == null){
		eMsg = "";
	}
	if(msg == null){
		msg = "";
	}
%>
<!DOCTYPE html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/registration.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員登録</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="main">
		<form method="post" action="RegistrationServlet">
			<h3 style="color:red"><%=eMsg %></h3>
			<%=msg %>
				<div class="regi_table">
				<table>
					<tr>
						<th class="t_top">社員番号</th>
						<td class="t_top">：<input type="text" name="textCode" value=""></td>
					</tr>
					<tr>
						<th>社員名</th>
						<td>：<input type="text" name="textName" value=""></td>
					</tr>
					<%
						if (myEmp.getAuthNo().equals("003") || myEmp.getAuthNo().equals("999")) {
					%>
					<tr>
						<th>部署</th>
						<td>：<select name="selectDivisionNo">
								<%
									for (DepModel dm : depList) {
								%>
								<option value="<%=dm.getDepNo()%>"><%=dm.getDepName()%></option>
								<%
									}
								%>
						</select></td>
					</tr>
					<tr>
						<th>権限</th>
						<td>：<select name="selectAuthorityNo">
								<option value="001">一般</option>
								<option value="002">上位</option>
								<option value="003">管理者</option>
						</select></td>
					</tr>
					<%
						}
					%>

				</table>
				</div>
				<%
					if (!(myEmp.getAuthNo().equals("003") || myEmp.getAuthNo().equals("999"))) {
				%>
				<input type="hidden" name="selectDivisionNo"
					value="<%=myEmp.getDepNo()%>">
				<input type="hidden" name="selectAuthorityNo" value="001">
				<%
					}
				%>
			<div class="regist_center">
				<input type="submit" value="確認" class="regist_button">
			</div>
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
	<div class="clear"></div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
