package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.MonthlyModel;

//月次
public class MonthlyDAO {
	// 打刻するメソッド
	public void insertMonthlyTime(MonthlyModel mm) throws NamingException,
			SQLException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		// データベース接続
		conn = DriverManager
				.getConnection(
						"jdbc:mysql://localhost:3306/arms?verifyServerCertificate=false&useSSL=false&requireSSL=false",
						"root", "password");

		// クラスのインスタンスを取得
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		// 自動コミットをオフ
		conn.setAutoCommit(false);
		String sql = "insert into employeeworktime(employeeno,year,month,m_workingtime,m_overworkingtime,m_nightwokingtime) values(?,?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, mm.getEmployeeNo());
		pstmt.setInt(2, mm.getYear());
		pstmt.setInt(3, mm.getMonth());
		pstmt.setTimestamp(4, mm.getM_workTime());
		pstmt.setTimestamp(5, mm.getM_overTime());
		pstmt.setTimestamp(6, mm.getM_nightTime());

		if (pstmt.executeUpdate() > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}

		pstmt.close();
		conn.close();
	}

	public void updateMonthlyTime(MonthlyModel mm) {
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

			String sql = "update employeemonthly set m_workingtime=?,m_overworkingtime=?,m_nightworkingtime=? where employeeno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, mm.getM_workTime());
			pstmt.setTimestamp(2, mm.getM_overTime());
			pstmt.setTimestamp(3, mm.getM_nightTime());
			pstmt.setString(4, mm.getEmployeeNo());

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

	public MonthlyModel findMonthlyTime(String eno, int y, int m) {
		MonthlyModel mm = new MonthlyModel();

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
			String sql = "select * from employeemonthly where employeeno=? and year=? and month=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, eno);
			pstmt.setInt(2, y);
			pstmt.setInt(3, m);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				mm.setEmployeeNo(rs.getString("employeeno"));
				mm.setYear(rs.getInt("year"));
				mm.setMonth(rs.getInt("month"));
				mm.setM_workTime(rs.getTimestamp("m_workingtime"));
				mm.setM_overTime(rs.getTimestamp("m_overworkingtime"));
				mm.setM_nightTime(rs.getTimestamp("m_nightworkingtime"));
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

		return mm;
	}

	public List<MonthlyModel> m_findByEmployeeNo(String eno) {
		List<MonthlyModel> list = new ArrayList<MonthlyModel>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		// データベース接続
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo?verifyServerCertificate=false&useSSL=false&requireSSL=false",
							"root", "password");

			// クラスのインスタンスを取得
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// 自動コミットをオフ
			conn.setAutoCommit(false);
			String sql = "select * from employeemonthly where employeeno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, eno);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MonthlyModel mm = new MonthlyModel();
				mm.setEmployeeNo(rs.getString("employeeno"));
				mm.setYear(rs.getInt("year"));
				mm.setMonth(rs.getInt("month"));
				mm.setM_workTime(rs.getTimestamp("m_workingtime"));
				mm.setM_overTime(rs.getTimestamp("m_overworkingtime"));
				mm.setM_nightTime(rs.getTimestamp("m_nightworkingtime"));

				list.add(mm);
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

	public List<MonthlyModel> findByMonthAndYear(int year, int month) {
		List<MonthlyModel> list = new ArrayList<MonthlyModel>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		// データベース接続
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo?verifyServerCertificate=false&useSSL=false&requireSSL=false",
							"root", "password");

			// クラスのインスタンスを取得
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// 自動コミットをオフ
			conn.setAutoCommit(false);
			String sql = "select * from employeemonthly where year=? and month=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MonthlyModel mm = new MonthlyModel();
				mm.setEmployeeNo(rs.getString("employeeno"));
				mm.setYear(rs.getInt("year"));
				mm.setMonth(rs.getInt("month"));
				mm.setM_workTime(rs.getTimestamp("m_workingtime"));
				mm.setM_overTime(rs.getTimestamp("m_overworkingtime"));
				mm.setM_nightTime(rs.getTimestamp("m_nightworkingtime"));

				list.add(mm);
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
