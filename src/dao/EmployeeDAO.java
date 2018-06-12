package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import model.EmployeeModel;



public class EmployeeDAO {
	Connection conn;
	PreparedStatement pStmt;


	int lim = 20;

	public EmployeeModel findEmployee(String empno){

		EmployeeModel empmodel = new EmployeeModel();

		try {
			conn = null;
			pStmt = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			String sql = "select * from employee Where EmployeeNo = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);


			pStmt.setString(1,empno);		//(1,xxx)１個目のハテナ

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				String divisionno = rs.getString("EmployeeDivisionNo");
				String authorityno = rs.getString("EmployeeAuthorityNo");
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
	public int CountEmp(String divno,String empno) {

		conn = null;
		pStmt = null;

		int counter = 0;
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			String sql = "select count(*) AS Counter from employee  where EmployeeAuthorityNo != ? AND EmployeeNo != ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1,divno);		//	権限番号
			pStmt.setString(2,empno);		//ログイン中の社員番号

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();
			counter = rs.getInt("Counter");

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return counter;
	}
	public ArrayList<EmployeeModel> findByEmployeeName(String empname,String empno) {

		conn = null;
		pStmt = null;
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();

		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// SQLの実行
			String sql = "select EmployeeNo,EmployeeName,DivisionName,AuthorityName "
					+ "from Employee AS e LEFT JOIN employeedivision AS ed ON(e.DivisionNo = ed.DivisionNo)"
					+ "LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority) "
					+ "where EmployeeName LIKE '%?%' AND DivisionNo != '999' AND EmployeeNo != ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			EmployeeModel empmodel = new EmployeeModel();

			pStmt.setString(1,empname);		//(1,xxx)１個目のハテナ
			pStmt.setString(2,empno);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				String divisionname = rs.getString("EmployeeDivisionName");
				String authorityno = rs.getString("EmployeeAuthorityNo");
				//Date enteringdate = rs.getDate("EmployeeEnteringDate");
				String password = rs.getString("employeePassword");

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepName(divisionname);
				empmodel.setAuthNo(authorityno);
				empmodel.setPassword(password);

				employeelist.add(empmodel);

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
		return employeelist;

	}
	public ArrayList<EmployeeModel> findByDepNo(String loginno,String dep_no,String authno) {		//部署 = Dep

		conn = null;
		pStmt = null;
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// SQLの実行
			String sql = "select EmployeeNo,EmployeeName,DivisionName,AuthorityName "
					+ "from Employee AS e LEFT JOIN employeedivision AS ed ON(e.DivisionNo = ed.DivisionNo)"
					+ "LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority) "
					+ "where  DivisionNo = ? AND ? = '003' AND EmployeeNo != ?)";		//ログイン情報

			pStmt.setString(1,dep_no);		//(1,xxx)１個目のハテナ
			pStmt.setString(2,authno);
			pStmt.setString(3,loginno);

			PreparedStatement pStmt = conn.prepareStatement(sql);
			EmployeeModel empmodel = new EmployeeModel();

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				String divisionname = rs.getString("DivisionName");
				String authorityname = rs.getString("AuthorityName");

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepName(divisionname);
				empmodel.setAuthName(authorityname);

				employeelist.add(empmodel);
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
		return employeelist;
	}
	public ArrayList<EmployeeModel>findByNameDep(String employee_no,String dep_no, String employee_name,int pageno,int cnt) {
		conn = null;
		pStmt = null;
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();
		EmployeeModel empmodel = new EmployeeModel();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// SQLの実行
			String sql = "select EmployeeNo,EmployeeName,DivisionName,AuthorityName "
					+ "from Employee AS e LEFT JOIN employeedivision AS ed ON(e.DivisionNo = ed.DivisionNo)"
					+ "LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority) "
					+ "WHERE  AutorityNo = '999' AND EmployeeNo = ?)";

			pStmt.setString(1,employee_no);

			if(employee_name != "") {
				sql = sql + " AND EmployeeName LIKE '%' + ? + '%'";
				pStmt.setString(1,employee_name);
			}
			sql = sql + " AND DivisionNo = ?";
			pStmt.setString(1,dep_no);
			sql = sql + "ORDER BY DivisionNo,EmployeeNo LIMIT lim * ?-20,lim";
			pStmt.setInt(1,pageno);

			//Like文
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				String divisionname = rs.getString("DivisionName");
				String authorityname = rs.getString("AuthorityName");

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepName(divisionname);
				empmodel.setAuthName(authorityname);

				employeelist.add(empmodel);
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
		return employeelist;
	}
	public ArrayList<EmployeeModel> findAll() {

		conn = null;
		pStmt = null;
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();
		EmployeeModel empmodel = new EmployeeModel();
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
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

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepName(divisionname);
				empmodel.setAuthName(authorityname);

				employeelist.add(empmodel);

			}
			return employeelist;

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
		//return tr;

	}
	public boolean InsertEmployee(EmployeeModel empmodel) {

		conn = null;
		pStmt = null;
		Date sqlDate = new Date(Calendar.getInstance().getTimeInMillis());
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// 自動コミットOFF
			conn.setAutoCommit(false);


			// SQLの実行
			String sql = "insert into Employee(EmployeeNo,EmployeeDivisionNo,EmployeeAuthorityNo,EmployeeEnteringDate,EmployeeName,EmployeePassword)"
			+ "values(?,?,?,?,?,?);";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			pStmt.setString(1,empmodel.getEmployeeNo());		//(1,xxx)１個目のハテナ
			pStmt.setString(2,empmodel.getDepNo());
			pStmt.setString(3,empmodel.getAuthNo());
			pStmt.setDate(5,sqlDate);
			pStmt.setString(6,empmodel.getEmployeeName());
			pStmt.setString(7,empmodel.getPassword());

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
	public boolean updateEmployee(EmployeeModel empmodel) {

		conn = null;
		pStmt = null;
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// 自動コミットOFF
			conn.setAutoCommit(false);

			// SQLの実行
			String sql = "update employee set EmployeeDivisionNo = ?,EmployeeAuthrityNo = ?,EmployeeName = ?"
					+ "where EmployeeNo = ?";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			pStmt.setString(1,empmodel.getEmployeeName());		//(1,xxx)１個目のハテナ
			pStmt.setString(2,empmodel.getDepNo());
			pStmt.setString(3,empmodel.getAuthNo());
			pStmt.setString(4,empmodel.getEmployeeNo());

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
	public boolean updateEmppass(EmployeeModel empmodel) {

		conn = null;
		pStmt = null;
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");
			// 自動コミットOFF
			conn.setAutoCommit(false);
			// SQLの実行
			String sql = "update employee set EmployeePassword = ?"
					+ "where EmployeeNo = ?";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			pStmt.setString(1,empmodel.getPassword());		//(1,xxx)１個目のハテナ
			pStmt.setString(2,empmodel.getEmployeeNo());

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
	public boolean deleteEmployee(String empno) {

		conn = null;
		pStmt = null;



		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// 自動コミットOFF
			conn.setAutoCommit(false);

			// SQLの実行
			String sql = "update employee set delflg = 0"
					+ "where EmployeeNo = ?";

			pStmt = conn.prepareStatement(sql);

			pStmt.setString(1,empno);		//(1,xxx)１個目のハテナ

			if (pStmt.executeUpdate() > 0) {
				// コミット
				conn.commit();
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			//return false;

		} finally {
			try {
				// 切断
				pStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				//return false;
			}

		}
		//return true;
		return true;


	}


}
