package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DepModel;
import model.EmployeeModel;

public class DepDAO {
	public List<DepModel> findDepAll(){
		List<DepModel> list = new ArrayList<DepModel>();

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

		String sql = "select * from employeedivision";
		pstmt = conn.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			DepModel dep = new DepModel();
			dep.setDepName(rs.getString("divisionname"));
			dep.setDepNo(rs.getString("divisionno"));
			dep.setDepFlg(rs.getInt("divisionflg"));
			list.add(dep);
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

	//全ての部署をリストに挿入して返す
	public ArrayList<DepModel> findAllByDepNo(String depno) {

		ArrayList<DepModel> deplist = new ArrayList<DepModel>();
		Connection conn = null;
		PreparedStatement pStmt = null;

		String divisionname = "";
		String divisionno;
		DepModel dmodel = new DepModel();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// SQLの実行
			String sql = "select divisionno,divisionName "
					+ "from employeedivision WHERE divisionNo <> ?"; // 部署

			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, depno);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				divisionno = rs.getString("divisionNo");
				divisionname = rs.getString("divisionName");

				dmodel.setDepName(divisionname);
				dmodel.setDepNo(divisionno);
				deplist.add(dmodel);
				dmodel = new DepModel();
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
		return deplist;

	}
	public String findByDepName(String depno) { // 部署 = Dep

		Connection conn = null;
		PreparedStatement pStmt = null;
		String divisionname = "";
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// SQLの実行
			String sql = "select divisionName "
					+ "from employeedivision WHERE divisionNo = ?"; // 部署

			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, depno);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				divisionname = rs.getString("divisionName");
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
		return divisionname;
	}

	// ログイン中ユーザの所属部署以外をリストに詰める
	public ArrayList<DepModel> findByDepNo(EmployeeModel emodel) { // 部署 = Dep

		Connection conn = null;
		PreparedStatement pStmt = null;
		ArrayList<DepModel> deplist = new ArrayList<DepModel>();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// SQLの実行
			String sql = "select DivisionNo,DivisionName"
					+ " from employeedivision WHERE DivisionNo <> ?"; // 部署

			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, emodel.getDepNo());

			DepModel dmodel = new DepModel();

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String divisionno = rs.getString("DivisionNo");
				String divisionname = rs.getString("DivisionName");

				dmodel.setDepNo(divisionno);
				dmodel.setDepName(divisionname);

				deplist.add(dmodel);
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
		return deplist;
	}

}
