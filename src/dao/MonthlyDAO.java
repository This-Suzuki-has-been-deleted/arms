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
							"jdbc:mysql://localhost:3306/arms?verifyServerCertificate=false&useSSL=false&requireSSL=false","root", "password");

			// クラスのインスタンスを取得
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// 自動コミットをオフ
			conn.setAutoCommit(false);
			String sql = "insert into employeeworktime(employeeno,year,month,m_workingtime,m_overworkingtime,m_nightwokingtime) values(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, mm.getEmployeeNo());
			pstmt.setInt(2, mm.getYear());
			pstmt.setInt(3, mm.getMonth());

			if (pstmt.executeUpdate() > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}

			pstmt.close();
			conn.close();
		}

		public void updateMonthlyTime(MonthlyModel mm) throws SQLException,
				InstantiationException, IllegalAccessException,
				ClassNotFoundException {
			Connection conn = null;
			PreparedStatement pstmt = null;

			// データベース接続
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo?verifyServerCertificate=false&useSSL=false&requireSSL=false",
							"root", "password");

			// クラスのインスタンスを取得
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// 自動コミットをオフ
			conn.setAutoCommit(false);

			String sql = "update employeeworktime set worktimeflg=?,attendance=?,leaving=? where employeeno=?";
			pstmt = conn.prepareStatement(sql);


			if (pstmt.executeUpdate() > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}

			pstmt.close();
			conn.close();
		}

		public MonthlyModel findMonthlyTime(String eno, int y, int m)
				throws SQLException, InstantiationException,
				IllegalAccessException, ClassNotFoundException {
			MonthlyModel mm = new MonthlyModel();

			Connection conn = null;
			PreparedStatement pstmt = null;

			// データベース接続
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo?verifyServerCertificate=false&useSSL=false&requireSSL=false",
							"root", "password");

			// クラスのインスタンスを取得
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// 自動コミットをオフ
			conn.setAutoCommit(false);
			String sql = "select * from employeeworktime where employeeno=? and year=?,month=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, eno);
			pstmt.setInt(2, y);
			pstmt.setInt(3, m);
			ResultSet rs = pstmt.executeQuery();

			mm.setEmployeeNo(rs.getString("employeeno"));
			mm.setYear(rs.getInt("year"));
			mm.setMonth(rs.getInt("month"));


			return mm;
		}

		public List<MonthlyModel> m_findByEmployeeNo(String eno)
				throws SQLException, InstantiationException,
				IllegalAccessException, ClassNotFoundException {
			List<MonthlyModel> list = new ArrayList<MonthlyModel>();

			Connection conn = null;
			PreparedStatement pstmt = null;
			// データベース接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gameinfo?verifyServerCertificate=false&useSSL=false&requireSSL=false","root", "password");

			// クラスのインスタンスを取得
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// 自動コミットをオフ
			conn.setAutoCommit(false);
			String sql = "select * from employeeworktime where employeeno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, eno);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				MonthlyModel mm = new MonthlyModel();
				mm.setEmployeeNo(rs.getString("employeeno"));
				mm.setYear(rs.getInt("year"));
				mm.setMonth(rs.getInt("month"));

				list.add(mm);
			}

			return list;
		}
}
