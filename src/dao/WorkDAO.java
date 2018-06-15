package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.WorkTimeModel;

public class WorkDAO {

	// 打刻するメソッド
	public void insertWorkTime(WorkTimeModel wt){
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
		String sql = "insert into employeeworktime(employeeno,year,month,day,attendance) values(?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, wt.getEmployeeNo());
		pstmt.setInt(2, wt.getYear());
		pstmt.setInt(3, wt.getMonth());
		pstmt.setInt(4, wt.getDay());
		pstmt.setTimestamp(5, wt.getAttendance());

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

	public void updateWorkTime(WorkTimeModel wt){
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

		String sql = "update employeeworktime set worktimeflg=?,attendance=?,leaving=? where employeeno=? AND year=? AND month = ? AND day = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, wt.isWorkTimeFlg());
		pstmt.setTimestamp(2, wt.getAttendance());
		pstmt.setTimestamp(3, wt.getLeaving());
		pstmt.setString(4, wt.getEmployeeNo());
		pstmt.setInt(5, wt.getYear());
		pstmt.setInt(6, wt.getMonth());
		pstmt.setInt(7, wt.getDay());

		if (pstmt.executeUpdate() > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}

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

	public WorkTimeModel findWorkTime(String eno, int y, int m, int d){
		WorkTimeModel wt = new WorkTimeModel();

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
		String sql = "select * from employeeworktime where employeeno=? and year=? and month=? and day=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);
		pstmt.setInt(2, y);
		pstmt.setInt(3, m);
		pstmt.setInt(4, d);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			wt.setEmployeeNo(rs.getString("employeeNo"));
			wt.setYear(rs.getInt("year"));
			wt.setMonth(rs.getInt("month"));
			wt.setDay(rs.getInt("day"));
			wt.setWorkTimeFlg(rs.getInt("worktimeflg"));
			wt.setAttendance(rs.getTimestamp("attendance"));
			wt.setLeaving(rs.getTimestamp("leaving"));
			wt.weekDate(wt);
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

		return wt;
	}

	public List<WorkTimeModel> d_findByEmployeeNo(String eno){
		List<WorkTimeModel> list = new ArrayList<WorkTimeModel>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		// データベース接続
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/arms?verifyServerCertificate=false&useSSL=false&requireSSL=false","root", "password");


		// クラスのインスタンスを取得
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		// 自動コミットをオフ
		conn.setAutoCommit(false);
		String sql = "select * from employeeworktime where employeeno=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);

		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			WorkTimeModel wt = new WorkTimeModel();
			wt.setEmployeeNo(rs.getString("employeeno"));
			wt.setYear(rs.getInt("year"));
			wt.setMonth(rs.getInt("month"));
			wt.setDay(rs.getInt("day"));
			wt.setWorkTimeFlg(rs.getInt("worktimeflg"));
			wt.setAttendance(rs.getTimestamp("attendance"));
			wt.setLeaving(rs.getTimestamp("leaving"));
			wt.weekDate(wt);
			list.add(wt);
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

	public List<WorkTimeModel> d_findByEmployeeNoAndMonth(String eno,int y,int m){
		List<WorkTimeModel> list = new ArrayList<WorkTimeModel>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		// データベース接続
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/arms?verifyServerCertificate=false&useSSL=false&requireSSL=false","root", "password");


		// クラスのインスタンスを取得
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		// 自動コミットをオフ
		conn.setAutoCommit(false);
		String sql = "select * from employeeWorkTime where employeeno=? and year=? and month=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);
		pstmt.setInt(2, y);
		pstmt.setInt(3, m);

		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			WorkTimeModel wt = new WorkTimeModel();
			wt.setEmployeeNo(rs.getString("employeeno"));
			wt.setYear(rs.getInt("year"));
			wt.setMonth(rs.getInt("month"));
			wt.setDay(rs.getInt("day"));
			wt.setWorkTimeFlg(rs.getInt("worktimeflg"));
			wt.setAttendance(rs.getTimestamp("attendance"));
			wt.setLeaving(rs.getTimestamp("leaving"));
			wt.weekDate(wt);
			list.add(wt);
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
