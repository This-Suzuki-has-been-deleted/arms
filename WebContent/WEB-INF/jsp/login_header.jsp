<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="gray">
	<div class="title">
		<a href="index.jsp">
			<img src="images/ARMS.png" alt="title"border="0">
		</a>
	</div>
	<div class="clock">
		<p id="RealtimeClockArea">
	</div>
</div>
<div class="clear"></div>

<script>
	function showClock1() {
		var nowTime = new Date();
		var nowHour = nowTime.getHours();
		var nowMin = nowTime.getMinutes();
		var nowSec = nowTime.getSeconds();

	    if (nowHour < 10) nowHour = "0" + nowHour;
	    if (nowMin < 10) nowMin = "0" + nowMin;
	    if (nowSec < 10) nowSec = "0" + nowSec;

		var msg = nowHour + ":" + nowMin + ":" + nowSec;
		document.getElementById("RealtimeClockArea").innerHTML = msg;
	}
	setInterval('showClock1()', 1000);
</script>