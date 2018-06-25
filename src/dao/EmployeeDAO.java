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




	/**
	 * 社員検索メソッド 検索した社員情報を持つリストを返す
	 **/
	public ArrayList<EmployeeModel> findByNameDep(String employee_no,String dep_no, String employee_name,int pageno) {
		conn = null;
		pStmt = null;
		int lim = 20;
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
					+ " WHERE  E.employeeAuthorityNo <> '999'";

			if (employee_name != "") {
				sql = sql +  " AND E.EmployeeName LIKE ?";		//SQLの％記号はpreparestatementだと変換されるため
				employee_name = "%" + employee_name + "%";		//% + + %をsetしてあげる
			}
			sql = sql + " AND E.employeedivisionNo = ?";
			sql = sql + " ORDER BY E.employeeDivisionNo,E.EmployeeNo LIMIT ?,?";

			int mathpageno = (lim * pageno) -20;

			pStmt = conn.prepareStatement(sql);
			if (employee_name != "") {
				pStmt.setString(1, employee_name);
				pStmt.setString(2, dep_no);
				pStmt.setInt(3,mathpageno);
				pStmt.setInt(4,lim);

			}else{
				pStmt.setString(1, dep_no);
				pStmt.setInt(2,mathpageno);
				pStmt.setInt(3,lim);
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

	//全社員一覧
	public ArrayList<EmployeeModel> findByAll(String employee_name,int pageno) {
		conn = null;
		pStmt = null;
		int lim = 20;
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
					+ " WHERE  E.employeeAuthorityNo <> '999'";

			if (employee_name != "") {
				sql = sql +  " AND E.EmployeeName LIKE ?";		//SQLの％記号はpreparestatementだと変換されるため
				employee_name = "%" + employee_name + "%";		//% + + %をsetしてあげる
			}
			sql = sql + " ORDER BY E.EmployeeNo,E.employeeDivisionNo LIMIT ?,?";

			int mathpageno = (lim * pageno) -20;

			pStmt = conn.prepareStatement(sql);
			if (employee_name != "") {
				pStmt.setString(1, employee_name);
				pStmt.setInt(2,mathpageno);
				pStmt.setInt(3,lim);

			}else{
				pStmt.setInt(1,mathpageno);
				pStmt.setInt(2,lim);
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
	public int CountEmp( String dep_no, String employee_name) {
		conn = null;
		pStmt = null;
		int counter = 0;
		int cnt = 1;

		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// SQLの実行
			String cntsql = "select Count(*) AS Counter FROM Employee WHERE employeeAuthorityNo <> '999'";

			if (employee_name != "") {
				cntsql = cntsql +  " AND EmployeeName LIKE ?";		//SQLの％記号はpreparestatementだと変換されるため
				employee_name = "%" + employee_name + "%";		//% + + %をsetしてあげる
			}


			if(dep_no.equals("00")) {
				pStmt = conn.prepareStatement(cntsql);
				//pStmt.setString(1, employee_no);
				if (employee_name != "") {
					pStmt.setString(1, employee_name);
				}
			}else {
				cntsql = cntsql + " AND employeedivisionNo = ?";
				pStmt = conn.prepareStatement(cntsql);
				if (employee_name != "") {
					pStmt.setString(1, employee_name);
					pStmt.setString(2, dep_no);
				}else{
					pStmt.setString(1, dep_no);
				}
			}

			// 結果の取得と出力
			ResultSet rs = pStmt.executeQuery();
			rs.next();
			counter = rs.getInt("Counter");

			if(counter <= 20){
				cnt = 1;
			}else{
				cnt = (int)counter / 20;
				if((counter % 20) != 0){
					cnt++;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 切断
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	public boolean insertEmployee(EmployeeModel empmodel) {

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
			String sql = "insert into Employee(EmployeeNo,employeeDivisionNo,employeeAuthorityNo,EmployeeName)"
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
			String sql = "update employee set EmployeeDivisionNo = ?,EmployeeAuthorityNo = ?,EmployeeName = ?"
					+ " where EmployeeNo = ?";

			pStmt = conn.prepareStatement(sql);

			// パラメータの設定
			 // (1,xxx)１個目のハテナ
			pStmt.setString(1, empmodel.getDepNo());
			pStmt.setString(2, empmodel.getAuthNo());
			pStmt.setString(3, empmodel.getEmployeeName());
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
