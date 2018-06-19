<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/search.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員検索</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<h3>${Msg }</h3>
	<div class="main">
		<form method="post" action="EmployeeServlet">
			社員名 <input type="text" name="employee_name">
			部署名<select name="dep_no">
				<c:forEach var="deplist" items="${DepList}">
					<c:if test="${Employee.authNo == '003' || Employee.authNo =='999'}">
						<c:choose>
							<c:when test="${Employee.depNo == deplist.depNo}">
								<option value="${deplist.depNo}" selected>${deplist.depName}</option>
							</c:when>
							<c:otherwise>
								<option value="${deplist.depNo}">${deplist.depName}</option>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</select><input type="submit" value="検索" class="s_button">
		</form>
		<table border="1">
			<tr>
				<th class="t_top">社員番号</th>
				<th class="t_top">社員名</th>
				<th class="t_top2">部署</th>
				<c:if test="${Employee.authNo == '003' || Employee.authNo == 999}">
					<!-- EmployeeModelのセッション名Employee -->
					<th class="t_top2">権限</th>
				</c:if>
				<th class="t_top2">編集</th>
			</tr>

			<c:forEach var="Emp" items="${RESULT}">
				<tr>
					<td>${Emp.employeeNo}</td>
					<td>${Emp.employeeName}</td>
					<td>${Emp.depName}</td>
					<c:if test="${Employee.authNo == '003' || Employee.authNo == 999}">
						<td>${Emp.authName}</td>
					</c:if>
					<td>
						<form action="InfoChangeServlet" method="POST">
							<input type="hidden" value="${Emp.employeeNo}" name="employeeNo" />
							<input type="hidden" value="${Emp.employeeName}"
								name="employeeName" /> <input type="hidden"
								value="${Emp.depNo}" name="selectDivisionNo" /> <input
								type="hidden" value="${Emp.authNo}" name="selectAuthorityNo" />
							<input type="image" src="images/icon.png">
						</form>
					</td>
				</tr>

			</c:forEach>
		</table>
		<%
			int pageno = (Integer) session.getAttribute("PAGENO");
			int nowpage = (Integer) session.getAttribute("SELECTPG");
		%>
		<div class="s_box">
			<%
				if (1 != nowpage) {
			%>
			<form name="backpage" action="EmployeeServlet" method="POST">
				<input type="hidden" name="pgno" value="<%=nowpage - 1%>">
			</form>
			<a href="EmployeeServlet"
				onclick="document.backpage.submit();return false;" class="pre">前</a>
			<%
				}
				if (pageno != 1) { //検索結果件数を持っているのか
					for (int i = 1; i < pageno + 1; i++) {
						if (nowpage == i) {
			%>
			<form name="selectpage<%=i%>" action="EmployeeServlet"
				method="POST">
				<input type="hidden" name="pgno" value="<%=i%>">
			</form>
			<a href="EmployeeServlet"
				onclick="document.selectpage<%=i%>.submit();return false;"
				class="now_button"><%=i%></a>
			<%
				} else {
			%>
			<form name="selectpage<%=i%>" action="EmployeeServlet"
				method="POST">
				<input type="hidden" name="pgno" value="<%=i%>">
			</form>
			<a href="EmployeeServlet"
				onclick="document.selectpage<%=i%>.submit();return false;"
				class="next_button"><%=i%></a>
			<%
				}
					}
				}
			%>
			<%
				if (pageno != nowpage) {
			%>
			<form name="nextpage" action="EmployeeServlet" method="POST">
				<input type="hidden" name="pgno" value="<%=nowpage + 1%>">
			</form>
			<a href="EmployeeServlet"
				onclick="document.nextpage.submit();return false;" class="next">次</a>
			<%
				}
			%>
		</div>
		<div class="clear"></div>
	</div>
	<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
	<div class="clear"></div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>
