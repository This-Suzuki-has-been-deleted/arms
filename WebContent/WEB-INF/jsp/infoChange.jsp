<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/employee.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員情報変更</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<div class="main">
	<form action="ChangeServlet" method="post">
			<input type="hidden" name="employeeNo" value="${ChangeEmployee.employeeNo }">
		<table>
			<tr>
				<th class="t_top">名前</th>
				<td class="t_top"><input type="text" name="employeeName" value="${ChangeEmployee.employeeName }"></td>
			</tr>
			<tr>
				<th>部署</th>
				<td><select name="selectDivisionNo">
				<option value="${ChangeEmployee.depNo}">${ChangeEmployee.depName}</option>
				<c:forEach var="dep" items="${DepModel}">
					<option value="${dep.depNo}">${dep.depName}</option>
				</c:forEach>
				</select></td>
			</tr>

		<c:if test="${Employee.authNo == '003' || Employee.authNo == '999'}">
			<tr>
				<th>権限</th>
				<td><select name="selectAuthorityNo">
				<option value="${ChangeEmployee.authNo}">${ChangeEmployee.authName}</option>
				<c:forEach var="auth" items="${AuthModel}">
					<option value="${auth.authNo}">${auth.authName}</option>
				</c:forEach>
				</select></td>
			</tr>
		</c:if>
		</table>
		<br>
		<input type="reset" value="戻る" class="w_button">
		<input type="submit" value="確認" class="wc_button">
		<div class="clear"></div>
	</form>
	<form action="InfoChangeServlet" method="GET">
	<Input type="submit" value="パスワードリセット" class="reset_button">
	</form>

</div>
<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
<div class="clear"></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>