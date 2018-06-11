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

	<form action="" method="post">

		<input type="text" name="employeeName">

		部署 ：<select name="selectDivisionNo">
				<c:forEach var="dep" items="${ChangeEmployee.DepModel}" varStatus="status">
					<option value="${dep.depNo}">${dep.depName}</option>
				</c:forEach>
				</select>
		<c:if test="${Employee.AuthNo = '003' } ">
			権限  ：<select name="selectAuthorityNo">
				<c:forEach var="auth" items="${ChangeEmployee.AuthModel}" varStatus="status">
					<option value="${auth.authNo}">${auth.authName}</option>
				</c:forEach>
				</select>
		</c:if>

		<input type="reset" value="戻る">
		<input type="submit" value="確認">
	</form>

</body>
</html>