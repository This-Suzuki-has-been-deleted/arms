<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:choose>
<c:when test="${flg = "rs" }"><title>登録確認</title></c:when>
<c:when test="${flg = "ch" }"><title>変更確認</title></c:when>
</c:choose>
</head>
<body>
<c:choose>
<c:when test="${flg = "rs" }">]
<p>社員番号：${employeeModel.employeeNo }</p>
<p>社員名：${employeeModel.employeeName }</p>
<p></p>
<p></p>

</c:when>
<c:when test="${flg = "ch" }">


</c:when>
</c:choose>

</body>
</html>