<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/conf.css" rel="stylesheet" type="text/css" />
<c:choose>
<c:when test="${pageFlg == 'ChangeServlet' }"><title>変更確認</title></c:when>
<c:when test="${pageFlg == 'RegistrationServlet' }"><title>登録確認</title></c:when>

</c:choose>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<div class="main">
<form action="ConfServlet" method="post">

<c:if test="${pageFlg == 'RegistrationServlet' }">
	<p class="conf_top">社員番号：${employeeModel.employeeNo }</p>
</c:if>

<c:choose>
	<c:when test="${pageFlg == 'ChangeServlet' }">
		<p class="conf_top">社員名：${employeeModel.employeeName }</p>
	</c:when>
	<c:otherwise>
		<p>社員名：${employeeModel.employeeName }</p>
	</c:otherwise>
</c:choose>



	<p>部署名:${employeeModel.depName}</p>
	<c:if test="${Employee.authNo == '003' || Employee.authNo == '999'}">
	<p>権限名:${employeeModel.authName }</p>
	</c:if>

<div class="conf_center">
	<input type="button" value="戻る" class="conf_button" onclick="history.back()">
	<input type="submit" value="確認" class="conf_button">
</div>
</form>
</div>
<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
<div class="clear"></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>