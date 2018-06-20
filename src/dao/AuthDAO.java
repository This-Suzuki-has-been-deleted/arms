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
				am.setAuthNo(rs.getString("employeeAuthorityNo"));
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

	public List<AuthModel> findAllByAuthNo(String authNo) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<AuthModel> authlist = new ArrayList<AuthModel>();
		Connection conn = null;
		PreparedStatement pStmt = null;

		String authname = "";
		String authno;
		AuthModel amodel = new AuthModel();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// SQLの実行
			String sql = "select employeeauthorityno,authorityName "
					+ "from employeeposition WHERE employeeauthorityno <> ? AND employeeauthorityno <> 999 "; // 部署

			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, authNo);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				authno = rs.getString("employeeAuthorityNo");
				authname = rs.getString("authorityName");

				amodel.setAuthName(authname);
				amodel.setAuthNo(authno);
				authlist.add(amodel);
				amodel = new AuthModel();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} finally {
			try {
				// 切断
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return authlist;
	}
	public String findByAuthName(String authno) {

		Connection conn = null;
		PreparedStatement pStmt = null;
		String authname = "";
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// SQLの実行
			String sql = "select authorityName "
					+ "from employeeposition WHERE employeeAuthorityNo = ?"; // 権限

			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, authno);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				authname = rs.getString("authorityName");

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} finally {
			try {
				// 切断
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return authname;
	}
}
