<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%
	String value = (String)session.getAttribute("buttonvalue");
	String timeMsg="前回打刻時間";
	String date = "";
	if(value==null){
		value = "入ってないよ";
	}
	Date dates = (Date)session.getAttribute("index_date");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh時mm分");
	if(dates!=null){
		date = sdf.format(dates);
	}
	session.removeAttribute("pageTitle");
	session.removeAttribute("eMsg");


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
	<div class="timestamp_box">
		<p class="timestamp_title">勤怠登録</p>
		<div class=center_button>
		<%if(dates!=null){ %>
		<p class="pre_time"><%=timeMsg %></p>
		<p class="timestamp"><%=date %></p>
		<%} %>
		<form action="TimeStampServlet" method="POST">
			<input type="submit" value=<%=value %> class="time_button">
		</form>
		</div>
	</div>
</div>
<jsp:include page="WEB-INF/jsp/navigation.jsp" />
<div class="clear"></div>
<jsp:include page="WEB-INF/jsp/footer.jsp" />

</body>
</html>