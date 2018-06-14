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
	ArrayList<DepModel> depList = (ArrayList<DepModel>) session
			.getAttribute("depList");
	String msg = (String) request.getAttribute("msg");
%>
<!DOCTYPE html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員登録</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="main">
		<form method="post" action="RegistrationServlet">
			<c:out value="${msg}"/>
				<table>
					<tr>
						<td>社員番号：</td>
						<td><input type="text" name="textCode" value=""></td>
					</tr>
					<tr>
						<td>社員名　：</td>
						<td><input type="text" name="textName" value=""></td>
					</tr>
					<%
						if (myEmp.getAuthNo().equals("003") || myEmp.getAuthNo().equals("999")) {
					%>
					<tr>
						<td>部署　　：</td>
						<td><select name="selectDivisionNo">
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
						<td>権限　　：</td>
						<td><select name="selectAuthorityNo">
								<option value="001">一般</option>
								<option value="002">上位</option>
								<option value="003">管理者</option>
						</select></td>
					</tr>
					<%
						}
					%>

				</table>
				<%
					if (!(myEmp.getAuthNo().equals("003") || myEmp.getAuthNo().equals("999"))) {
				%>
				<input type="hidden" name="selectDivisionNo"
					value="<%=myEmp.getDepNo()%>">
				<input type="hidden" name="selectAuthorityNo" value="001">
				<%
					}
				%>
			<input type="submit" value="確認">
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
	<div class="clear"></div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
