package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import model.DepModel;
import model.EmployeeModel;

public class EmployeeDAO {
	Connection conn;
	PreparedStatement pStmt;

	int lim = 20;

	public EmployeeModel findEmployee(String empno) {

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

			pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, empno); // (1,xxx)１個目のハテナ

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

	// 社員名をキーに社員検索（重複チェック
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
					+ " LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority) "
					+ "where EmployeeName LIKE '%?%' AND DivisionNo <> '999' AND EmployeeNo <> ?)";

			pStmt = conn.prepareStatement(sql);
			EmployeeModel empmodel = new EmployeeModel();

			pStmt.setString(1, empname); // (1,xxx)１個目のハテナ
			pStmt.setString(2, empno);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				String divisionname = rs.getString("EmployeeDivisionName");
				String authorityno = rs.getString("EmployeeAuthorityNo");
				// Date enteringdate = rs.getDate("EmployeeEnteringDate");
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

	public String findByAuthName(String authno) {

		conn = null;
		pStmt = null;
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

	//全ての部署をリストに挿入して返す
	public ArrayList<DepModel> findAllByDepNo(String depno) {

		ArrayList<DepModel> deplist = new ArrayList<DepModel>();
		conn = null;
		pStmt = null;

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

		conn = null;
		pStmt = null;
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

	// 部署をリストに詰める
	public ArrayList<DepModel> findByDepNo(EmployeeModel emodel) { // 部署 = Dep

		conn = null;
		pStmt = null;
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

	/**
	 * 社員検索メソッド 検索した社員情報を持つリストを返す
	 **/
	public ArrayList<EmployeeModel> findByNameDep(String employee_no,String dep_no, String employee_name,int pageno) {
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
			String sql = "select E.EmployeeNo,E.EmployeeName,E.employeedivisionNo,E.employeeAuthorityNo,ED.DivisionName,EP.AuthorityName "
					+ "from Employee AS E LEFT JOIN employeedivision AS ED ON(E.employeeDivisionNo = ED.DivisionNo)"
					+ " LEFT JOIN employeeposition AS EP ON(E.employeeAuthorityNo = EP.employeeAuthorityNo) "
					+ " WHERE  E.employeeAuthorityNo <> '999' AND E.EmployeeNo <> ?";

			if (employee_name != "") {
				sql = sql +  " AND E.EmployeeName LIKE ?";		//SQLの％記号はpreparestatementだと変換されるため
				employee_name = "%" + employee_name + "%";		//% + + %をsetしてあげる
			}
			sql = sql + " AND E.employeedivisionNo = ?";

			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, employee_no);
			if (employee_name != "") {
				pStmt.setString(2, employee_name);
				pStmt.setString(3, dep_no);
			}else{
				pStmt.setString(2, dep_no);
			}

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				 empmodel = new EmployeeModel();
				String employeeno = rs.getString("E.EmployeeNo");
				String employeename = rs.getString("E.EmployeeName");
				String divisionno = rs.getString("E.employeeDivisionNo");
				String divisionname = rs.getString("ED.DivisionName");
				String authorityno = rs.getString("E.employeeAuthorityNo");
				String authorityname = rs.getString("EP.AuthorityName");

				empmodel.setEmployeeNo(employeeno);
				empmodel.setEmployeeName(employeename);
				empmodel.setDepNo(divisionno);
				empmodel.setDepName(divisionname);
				empmodel.setAuthNo(authorityno);
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

	/**
	 * 社員検索メソッド 検索結果の件数を算出する
	 **/
	public int CountEmp(String employee_no, String dep_no, String employee_name) {
		conn = null;
		pStmt = null;
		int counter = 0;

		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// SQLの実行
			String cntsql = "select Count(*) AS Counter FROM Employee WHERE employeeAuthorityNo <> '999'  AND EmployeeNo <> ?";

			pStmt = conn.prepareStatement(cntsql);

			pStmt.setString(1, employee_no);

			if (employee_name != "") {
				cntsql = cntsql +  " AND E.EmployeeName LIKE ?";
				employee_name = "%" + employee_name + "%";
				pStmt.setString(1, employee_name);
			}
			cntsql = cntsql + " AND E.DivisionNo = ?";
			if(employee_name != ""){
				pStmt.setString(1, dep_no);
			}else{
				pStmt.setString(1, dep_no);
			}
			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();
			rs.next();

			counter = rs.getInt("Counter");

			counter = counter / 20 + 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return counter;
		} finally {
			try {
				// 切断
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return counter;
			}
		}
		return counter;
	}

	// 社員情報全件表示（現在は使用しない）
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
					+ " LEFT JOIN employeeposition AS ep ON(e.EmployeeAuthority = ep.EmployeeAuthority))";

			pStmt = conn.prepareStatement(sql);

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {

				String employeeno = rs.getString("EmployeeNo");
				String employeename = rs.getString("EmployeeName");
				String divisionname = rs.getString("DivisionName");
				String authorityname = rs.getString("AuthorityName"); // SQLの値をgetでもってくる　上のsqlで宣言したもの

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
		// return tr;

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
			String sql = "insert into Employee(EmployeeNo,EmployeeDivisionNo,employeeAuthorityNo,EmployeeEnteringDate,EmployeeName,EmployeePassword)"
					+ "values(?,?,?,?,?,?);";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			pStmt.setString(1, empmodel.getEmployeeNo()); // (1,xxx)１個目のハテナ
			pStmt.setString(2, empmodel.getDepNo());
			pStmt.setString(3, empmodel.getAuthNo());
			pStmt.setDate(4, sqlDate);
			pStmt.setString(5, empmodel.getEmployeeName());
			pStmt.setString(6, empmodel.getPassword());

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
					+ " where EmployeeNo = ?";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			pStmt.setString(1, empmodel.getEmployeeName()); // (1,xxx)１個目のハテナ
			pStmt.setString(2, empmodel.getDepNo());
			pStmt.setString(3, empmodel.getAuthNo());
			pStmt.setString(4, empmodel.getEmployeeNo());

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
					+ " where EmployeeNo = ?";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			pStmt.setString(1, empmodel.getPassword()); // (1,xxx)１個目のハテナ
			pStmt.setString(2, empmodel.getEmployeeNo());

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
					+ " where EmployeeNo = ?";

			pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, empno); // (1,xxx)１個目のハテナ

			if (pStmt.executeUpdate() > 0) {
				// コミット
				conn.commit();
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// return false;

		} finally {
			try {
				// 切断
				pStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// return false;
			}

		}
		// return true;
		return true;

	}

}
