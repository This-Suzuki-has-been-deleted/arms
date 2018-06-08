package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.AnnualModel;
//年次
public class AnnualDAO {
	// 打刻するメソッド
	public void insertMonthlyTime(AnnualModel am) throws NamingException,
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
		String sql = "insert into employeeannual(employeeno,year,y_workingtime,y_overworkingtime,y_nightwokingtime) values(?,?,?,?,?) where employeeno=?";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, am.getEmployeeNo());
		pstmt.setInt(2, am.getYear());
		pstmt.setDate(3, am.getY_workTime());
		pstmt.setDate(4, am.getY_overTime());
		pstmt.setDate(5, am.getY_nightTime());
		pstmt.setString(6, am.getEmployeeNo());


		if (pstmt.executeUpdate() > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}

		pstmt.close();
		conn.close();
	}

	public void updateMonthlyTime(AnnualModel am) throws SQLException,
			InstantiationException, IllegalAccessException,
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

		String sql = "update employeemonthly set y_workingtime=?,y_overworkingtime=?,y_nightworkingtime=? where employeeno=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setDate(1, am.getY_workTime());
		pstmt.setDate(2, am.getY_overTime());
		pstmt.setDate(3, am.getY_nightTime());
		pstmt.setString(4, am.getEmployeeNo());

		if (pstmt.executeUpdate() > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}

		pstmt.close();
		conn.close();
	}

	public AnnualModel findMonthlyTime(String eno, int y)
			throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		AnnualModel am = new AnnualModel();

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
		String sql = "select * from employeeannual where employeeno=? and year=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);
		pstmt.setInt(2, y);

		ResultSet rs = pstmt.executeQuery();

		am.setEmployeeNo(rs.getString("employeeno"));
		am.setYear(rs.getInt("year"));
		am.setY_workTime(rs.getDate("y_workingtime"));
		am.setY_overTime(rs.getDate("y_overworkingtime"));
		am.setY_nightTime(rs.getDate("y_nightworkingtime"));

		return am;
	}

	public List<AnnualModel> m_findByEmployeeNo(String eno)
			throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		List<AnnualModel> list = new ArrayList<AnnualModel>();

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
		String sql = "select * from employeeannual where employeeno=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			AnnualModel am = new AnnualModel();
			am.setEmployeeNo(rs.getString("employeeno"));
			am.setYear(rs.getInt("year"));
			am.setY_workTime(rs.getDate("y_workingtime"));
			am.setY_overTime(rs.getDate("y_overworkingtime"));
			am.setY_nightTime(rs.getDate("y_nightworkingtime"));

			list.add(am);
		}

		return list;
	}
}
