<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員情報変更</title>
</head>
<body>

	<form action="" method="post">

		<input type="text" name="employeeNo">

		部署 ：<select name="selectDivisionNo">
			<option value="選択肢0">部署を選択</option>
			<option value="選択肢1">選択肢1</option>
			<option value="選択肢2">選択肢2</option>
			<option value="選択肢3">選択肢3</option>
			<option value="選択肢4">選択肢4</option>
			<option value="選択肢5">選択肢5</option>
			<option value="選択肢6">選択肢6</option>
		</select>
		<c:if test="${Employee.AuthNo = '003' } ">
			権限  ：<select name="selectAuthorityNo">
				<option value="選択肢0">権限を選択</option>
				<option value="選択肢1">選択肢1</option>
				<option value="選択肢2">選択肢2</option>
				<option value="選択肢3">選択肢3</option>
				<option value="選択肢4">選択肢4</option>
			</select>
		</c:if>

		<input type="reset" value="戻る">
		<input type="submit" value="確認">
	</form>

</body>
</html>