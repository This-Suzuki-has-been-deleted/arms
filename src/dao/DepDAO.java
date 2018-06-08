package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepDAO {
	public List<String> findDepName(){
		List<String> list = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		// データベース接続
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms?verifyServerCertificate=false&useSSL=false&requireSSL=false",
							"root", "password");
		// クラスのインスタンスを取得
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		String sql = "select divisionname from employeedivision";
		pstmt = conn.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			list.add(rs.getString("divisionname"));
		}

		// 自動コミットをオフ
		conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return list;
	}
}
