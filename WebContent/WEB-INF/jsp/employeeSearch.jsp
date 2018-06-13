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
	<form method="post" action="EmployeeServlet">
		社員名 <input type="text" name="employee_name">
		部署名<select name="dep_no">
			<option value="${Employee.depNo}">${Employee.depName}</option>
				<c:forEach var="deplist" items="${DepList}">
					<c:if test="${Employee.authNo == '003' || Employee.authNo =='999'}">
						<option value="${deplist.Depno}">${deplist.DepName}</option>
					</c:if>
				</c:forEach>
		</select>
		<input type="submit" value="検索">
	</form>
	<table>
		<tr>
			<th>社員番号</th>
			<th>社員名</th>
			<th>部署</th>
			<c:if test="{Employee.depNo == '003'}">
				<!-- EmployeeModelのセッション名Employee -->
				<th>権限</th>
			</c:if>
		</tr>
		<tr>
			<c:forEach var="Emp" items="${RESULT}">
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