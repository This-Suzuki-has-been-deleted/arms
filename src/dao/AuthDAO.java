package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AuthModel;

public class AuthDAO {
	public List<AuthModel> findAuthAll() {
		List<AuthModel> list = new ArrayList<AuthModel>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// データベース接続
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms?verifyServerCertificate=false&useSSL=false&requireSSL=false",
							"root", "password");
			// クラスのインスタンスを取得
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String sql = "select * from employeeposition";
			pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				AuthModel am = new AuthModel();
				am.setAuthNo(rs.getInt("employeeAuthorityNo"));
				am.setAuthName(rs.getString("authorityName"));
				list.add(am);
			}

		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return list;
	}
}
