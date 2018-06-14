package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AnnualModel;
//年次
public class AnnualDAO {
	// 打刻するメソッド
	public void insertMonthlyTime(AnnualModel am){
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

		// 自動コミットをオフ
		conn.setAutoCommit(false);
		String sql = "insert into employeeannual(employeeno,year,yearworkingtime,yearoverworkingtime,yearnightworkingtime) values(?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, am.getEmployeeNo());
		pstmt.setInt(2, am.getYear());
		pstmt.setTimestamp(3, am.getY_workTime());
		pstmt.setTimestamp(4, am.getY_overTime());
		pstmt.setTimestamp(5, am.getY_nightTime());

		pstmt.executeUpdate();

		conn.commit();

		pstmt.close();
		conn.close();

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
	}

	public void updateMonthlyTime(AnnualModel am){
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

		// 自動コミットをオフ
		conn.setAutoCommit(false);

		String sql = "update employeeannual set yearworkingtime=?,yearoverworkingtime=?,yearnightworkingtime=? where employeeno=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setTimestamp(1, am.getY_workTime());
		pstmt.setTimestamp(2, am.getY_overTime());
		pstmt.setTimestamp(3, am.getY_nightTime());
		pstmt.setString(4, am.getEmployeeNo());

		pstmt.executeUpdate();

		conn.commit();

		pstmt.close();
		conn.close();
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
	}

	public AnnualModel findAnnualTime(String eno, int y){
		AnnualModel am = new AnnualModel();

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


		// 自動コミットをオフ
		conn.setAutoCommit(false);
		String sql = "select * from employeeannual where employeeno=? and year=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);
		pstmt.setInt(2, y);

		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			am.setEmployeeNo(rs.getString("employeeno"));
			am.setYear(rs.getInt("year"));
			am.setY_workTime(rs.getTimestamp("yearworkingtime"));
			am.setY_overTime(rs.getTimestamp("yearoverworkingtime"));
			am.setY_nightTime(rs.getTimestamp("yearnightworkingtime"));
		}
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

		return am;
	}

	public List<AnnualModel> m_findByEmployeeNo(String eno){
		List<AnnualModel> list = new ArrayList<AnnualModel>();

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

		// 自動コミットをオフ
		conn.setAutoCommit(false);
		String sql = "select * from employeeannual where employeeno=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			AnnualModel am = new AnnualModel();
			am.setEmployeeNo(rs.getString("employeeno"));
			am.setYear(rs.getInt("year"));
			am.setY_workTime(rs.getTimestamp("yearworkingtime"));
			am.setY_overTime(rs.getTimestamp("yearoverworkingtime"));
			am.setY_nightTime(rs.getTimestamp("yearnightworkingtime"));

			list.add(am);
		}
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

	public List<AnnualModel> findByYear(int year){
		List<AnnualModel> list = new ArrayList<AnnualModel>();

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

		// 自動コミットをオフ
		conn.setAutoCommit(false);
		String sql = "select * from employeeannual where year=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, year);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			AnnualModel am = new AnnualModel();
			am.setEmployeeNo(rs.getString("employeeno"));
			am.setYear(rs.getInt("year"));
			am.setY_workTime(rs.getTimestamp("yearworkingtime"));
			am.setY_overTime(rs.getTimestamp("yearoverworkingtime"));
			am.setY_nightTime(rs.getTimestamp("yearnightworkingtime"));

			list.add(am);
		}
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
