<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員検索</title>
</head>
<body>
	<form method="post" action="ChangeServlet">
		社員名 <input type="text" name="employee_name">
		部署名<select name="dep_no">
			<option value="${RESULT.list.depNo}">${RESULT.list.depName}
				<c:forEach var="result" items="${RESULT}">
					<c:if test="{Employee.Divisionno == '003'}">
						<option value="${result.list.divisionno}">${result.list.divisionName}
					</c:if>
				</c:forEach>
			<option value="RESULT.list.divisionNo">${RESULT.list.divisionName}</option>
		</select>
	</form>
	<table>
		<tr>
			<th>社員番号</th>
			<th>社員名</th>
			<th>部署</th>
			<c:if test="{Employee.Divisionno == '003'}">
				<!-- EmployeeModelのセッション名Employee -->
				<th>権限</th>
			</c:if>
		</tr>
		<tr>
			<c:forEach var="Emp" items="${Employee}">
				<td>${Emp.employeeNo}</td>
				<td>${Emp.employeeName}</td>
				<td>${Emp.depName}</td>
				<c:if test="{Employee.Divisionno == '003'}">
					<td>${Emp.authName}</td>
				</c:if>
			</c:forEach>

		</tr>
	</table>
	<div></div>

</body>
</html>