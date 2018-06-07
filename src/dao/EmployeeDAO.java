package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.EmployeeModel;
import model.FindEmpModel;



public class EmployeeDAO {
	Connection conn;
	PreparedStatement pStmt;
	conn = null;
	pStmt = null;

	Date date1 = new Date();
	int lim = 20;

	Class.forName("com.mysql.jdbc.Driver");




	public EmployeeModel findEmployee(String empno){

		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// SQLの実行
			String sql = "select * from employee Where EmployeeNo = ?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			EmployeeModel empmodel = new EmployeeModel();

			pStmt.setString(1,empno);		//(1,xxx)１個目のハテナ

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				int divisionno = rs.getInt("EmployeeDivisionNo");
				int authorityno = rs.getInt("EmployeeAuthorityNo");
				//Date enteringdate = rs.getDate("EmployeeEnteringDate");
				String password = rs.getString("employeePassword");

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepNo(divisionno);
				empmodel.setAuthNo(authorityno);
				empmodel.setPassword(password);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} catch (ClassNotFoundException e) {
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
		return empmodel;

	}
	public int CountEmp() {
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			String sql = "select count(*) AS Counter from employee  where EmployeeAuthorityNo != ? AND EmployeeNo != ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1,);		//	権限番号
			pStmt.setString(2,);		//ログイン中の社員番号

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();
				int counter = rs.getInt("Counter");

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 切断
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return counter;
	}
	public EmployeeModel findByEmployeeName(String empname) {
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// SQLの実行
			String sql = "select EmployeeNo,EmployeeName,DivisionName,AuthorityName "
					+ "from Employee AS e LEFT JOIN employeedivision AS ed ON(e.DivisionNo = ed.DivisionNo)"
					+ "LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority) "
					+ "where EmployeeName LIKE '%empname%' AND    権限番号 = '003' AND EmployeeNo != ログインNO)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			EmployeeModel empmodel = new EmployeeModel();

			pStmt.setString(1,empname);		//(1,xxx)１個目のハテナ

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				int divisionno = rs.getInt("EmployeeDivisionNo");
				int authorityno = rs.getInt("EmployeeAuthorityNo");
				//Date enteringdate = rs.getDate("EmployeeEnteringDate");
				String password = rs.getString("employeePassword");

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepNo(divisionno);
				empmodel.setAuthNo(authorityno);
				empmodel.setPassword(password);

				employeelist.add(empmodel);


			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} catch (ClassNotFoundException e) {
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
		return empmodel;

	}
	public EmployeeModel findByDepNo(Integer depno) {		//部署 = Dep
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// SQLの実行
			String sql = "select EmployeeNo,EmployeeName,DivisionName,AuthorityName "
					+ "from Employee AS e LEFT JOIN employeedivision AS ed ON(e.DivisionNo = ed.DivisionNo)"
					+ "LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority) "
					+ "where  DivisionNo = ? AND 権限番号 = '003' AND EmployeeNo != ログインNO)";		//ログイン情報

			pStmt.setInt(1,depno);		//(1,xxx)１個目のハテナ

			String cntsql = "select count(*) from wmployee where AND ? = '003' AND EmployeeNo != ?";

			pStmt.setInt(1,depno);
			pS

			PreparedStatement pStmt = conn.prepareStatement(sql);
			EmployeeModel empmodel = new EmployeeModel();



			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				int divisionno = rs.getInt("EmployeeDivisionNo");
				int authorityno = rs.getInt("EmployeeAuthorityNo");
				//Date enteringdate = rs.getDate("EmployeeEnteringDate");
				String password = rs.getString("employeePassword");

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepNo(divisionno);
				empmodel.setAuthNo(authorityno);
				empmodel.setPassword(password);


				employeelist.add(empmodel);


			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} catch (ClassNotFoundException e) {
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
		return empmodel;


	}
	public EmployeeModel findAll() {
		ArrayList<FindEmpModel> employeelist = new ArrayList<FindEmpModel>();

		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");


			// SQLの実行
			String sql = "select EmployeeNo,EmployeeName,DivisionName,AuthorityName "
					+ "from Employee AS e LEFT JOIN employeedivision AS ed ON(e.DivisionNo = ed.DivisionNo)"
					+ "LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority))";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {

				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				String divisionname = rs.getString("DivisionName");
				String authorityname = rs.getString("AuthorityName");		//SQLの値をgetでもってくる　上のsqlで宣言したもの

				FindEmpModel femodel = new FindEmpModel();
				//action_date,myname,myhp,mymp,enemyname,enemyhp,enemymp,result

				femodel.setEmployeeNo(employeeno);
				femodel.setEmployeeName(employeename);
				femodel.setDepName(divisionname);
				femodel.setAuthName(authorityname);

				employeelist.add(femodel);

			}
			return femodel;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} catch (ClassNotFoundException e) {
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
		//return hisarraylist;
		return tr;

	}
	public boolean InsertEmployee(EmployeeModel em) {
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/gameinfo"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// 自動コミットOFF
			conn.setAutoCommit(false);

			SimpleDateFormat date1 = new SimpleDateFormat("yyyy/mm/dd");

			// SQLの実行
			String sql = "insert into Employee(EmployeeNo,EmployeeDivisionNo,"
					+ "EmployeeAuthorityNo,EmployeeEnteringDate,EmployeeName,EmployeePassword)"
			+ "values(?,?,?,?,?,?,?);";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			pStmt.setString(1,em.getEmployeeNo());		//(1,xxx)１個目のハテナ
			pStmt.setInt(2,em.getDepNo());
			pStmt.setInt(3,em.getAuthNo());
			pStmt.setString(4,em.getEmployeeName());
			pStmt.setDate(5,date1);
			pStmt.setString(6,em.getEmployeeName());
			pStmt.setString(7,em.getPassword());

			// 結果の取得と出力
			if (pStmt.executeUpdate() > 0) {
				// コミット
				conn.commit();
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				// 切断
				pStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		}
		return true;
	}

	}
	public EmployeeModel updateEmployee(boolean ) {

	}
	public EmployeeModel deleteEmployee(String ) {

	}


}
