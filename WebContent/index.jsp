<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String value = (String)session.getAttribute("buttonvalue");
	if(value==null){
		value = "入ってないよ";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>メインメニュー</title>
</head>

<body>
<jsp:include page="WEB-INF/jsp/header.jsp" />
<div class="main">
	<form action="TimeStampServlet" method="POST">
		<input type="submit" value=<%=value %>>
	</form>
</div>
<jsp:include page="WEB-INF/jsp/navigation.jsp" />
<div class="clear"></div>
<jsp:include page="WEB-INF/jsp/footer.jsp" />

</body>
</html>