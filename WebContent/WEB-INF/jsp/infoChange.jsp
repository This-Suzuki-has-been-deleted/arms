<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員情報変更</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<div class="main">
	<form action="ChangeServlet" method="post">
			<input type="hidden" name="employeeNo" value="${ChangeEmployee.employeeNo }">
		名前：<input type="text" name="employeeName" value="${ChangeEmployee.employeeName }">
		<br>
		部署 ：<select name="selectDivisionNo">
				<option value="${ChangeEmployee.depNo}">${ChangeEmployee.depName}</option>
				<c:forEach var="dep" items="${DepModel}">
					<option value="${dep.depNo}">${dep.depName}</option>
				</c:forEach>
				</select>

		<c:if test="${Employee.authNo == '003' || Employee.authNo == '999'}">
			<br>
			権限  ：<select name="selectAuthorityNo">
				<option value="${ChangeEmployee.authNo}">${ChangeEmployee.authName}</option>
				<c:forEach var="auth" items="${AuthModel}">
					<option value="${auth.authNo}">${auth.authName}</option>
				</c:forEach>
				</select>
		</c:if>
		<br>
		<input type="reset" value="戻る">
		<input type="submit" value="確認">
	</form>
	<form action="InfoChangeServlet" method="GET">
	<Input type="submit" value="パスワードリセット">
	</form>

</div>
<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
<div class="clear"></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>