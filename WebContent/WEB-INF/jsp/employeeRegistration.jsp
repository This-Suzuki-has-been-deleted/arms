<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<body>
	<form method="post" action="Registration~~">
		<table>
			<tr>
				<td>社員番号：</td>
				<td><input type="text" name="textCode" value=""></td>
			</tr>
			<tr>
				<td>社員名　：</td>
				<td><input type="text" name="textName" value=""></td>
			</tr>
			<c:if test="${0 == 0}" >
				<tr>
					<td>部署　　：</td>
					<td><select name="selectDivisionNo">
							<option value="選択肢0">部署を選択</option>
							<option value="選択肢1">選択肢1</option>
							<option value="選択肢2">選択肢2</option>
							<option value="選択肢3">選択肢3</option>
							<option value="選択肢4">選択肢4</option>
							<option value="選択肢5">選択肢5</option>
							<option value="選択肢6">選択肢6</option>
					</select></td>
				</tr>
				<tr>
					<td>権限　　：</td>
					<td><select name="selectAuthorityNo">
							<option value="選択肢0">権限を選択</option>
							<option value="選択肢1">選択肢1</option>
							<option value="選択肢2">選択肢2</option>
							<option value="選択肢3">選択肢3</option>
							<option value="選択肢4">選択肢4</option>
							<option value="選択肢5">選択肢5</option>
							<option value="選択肢6">選択肢6</option>
					</select></td>
				</tr>
			</c:if>
		</table>
	</form>
	<input type="submit" value="戦闘開始">
</body>

