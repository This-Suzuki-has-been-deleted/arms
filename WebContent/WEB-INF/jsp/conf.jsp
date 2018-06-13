<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:choose>
<c:when test="${pageFlg = 'RegistrationServlet' }"><title>登録確認</title></c:when>
<c:when test="${pageFlg = 'ChangeServlet' }"><title>変更確認</title></c:when>
</c:choose>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<div class="main">
<form action="ConfServlet" method="post">
<c:if test="${pageFlg == 'RegistrationServlet' }">
	<p>社員番号：${employeeModel.employeeNo }</p>
</c:if>
	<p>社員名：${employeeModel.employeeName }</p>
	<c:if test="${employeeModel.authNo == '003' or employeeModel.authNo == '999' or pageFlg == 'ChangeServlet' }">
	<p>部署名:${employeeModel.depName}</p>
	<p>権限名:${employeeModel.authName }</p>
	</c:if>
<input type="submit" value="確認">
</form>
</div>
<jsp:include page="/WEB-INF/jsp/navigation.jsp" />
<div class="clear"></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp" />

</body>
</html>