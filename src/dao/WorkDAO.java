package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.WorkTimeModel;

public class WorkDAO {

	// 打刻するメソッド
	public void insertWorkTime(WorkTimeModel wt) throws NamingException,
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
		String sql = "insert into employeeworktime(employeeno,year,month,day,attendance) values(?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, wt.getEmployeeNo());
		pstmt.setInt(2, wt.getYear());
		pstmt.setInt(3, wt.getMonth());
		pstmt.setInt(4, wt.getDay());
		pstmt.setDate(5, wt.getAttendance());

		if (pstmt.executeUpdate() > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}

		pstmt.close();
		conn.close();
	}

	public void updateWorkTime(WorkTimeModel wt) throws SQLException,
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

		String sql = "update employeeworktime set worktimeflg=?,attendance=?,leaving=? where employeeno=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setBoolean(1, wt.isWorkTimeFlg());
		pstmt.setDate(2, wt.getAttendance());
		pstmt.setDate(3, wt.getLeaving());
		pstmt.setString(4, wt.getEmployeeNo());

		if (pstmt.executeUpdate() > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}

		pstmt.close();
		conn.close();
	}

	public WorkTimeModel findWorkTime(String eno, int y, int m, int d)
			throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		WorkTimeModel wt = new WorkTimeModel();

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
		String sql = "select * from employeeworktime where employeeno=? and year=?,month=?,day=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, eno);
		pstmt.setInt(2, y);
		pstmt.setInt(3, m);
		pstmt.setInt(4, d);
		ResultSet rs = pstmt.executeQuery();

		wt.setEmployeeNo(rs.getString("employeeno"));
		wt.setYear(rs.getInt("year"));
		wt.setMonth(rs.getInt("month"));
		wt.setDay(rs.getInt("day"));
		wt.setWorkTimeFlg(rs.getBoolean("worktimeflg"));
		wt.setAttendance(rs.getTime("attendance"));
		wt.setLeaving(rs.getTime("leaving"));

		return wt;
	}

	public List<WorkTimeModel> d_findByEmployeeNo(String eno)
			throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		List<WorkTimeModel> list = new ArrayList<WorkTimeModel>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		// データベース接続
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
			wt.setWorkTimeFlg(rs.getBoolean("worktimeflg"));
			wt.setAttendance(rs.getTime("attendance"));
			wt.setLeaving(rs.getTime("leaving"));
			list.add(wt);
		}

		return list;
	}
}
