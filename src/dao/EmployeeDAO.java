package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Fight.History;
import model.AuthModel;
import model.DepModel;
import model.EmployeeModel;
import model.FindEmpModel;
import model.TotalResult;



public class EmployeeDAO {
	Connection conn;
	PreparedStatement pStmt;
	Date date1 = new Date();
	ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();

	public boolean findEmployee(String ) {
		conn = null;
		pStmt = null;

	}
	public EmployeeModel findByEmployeeName(String ) {

	}
	public EmployeeModel findByDepNo(Integer ) {		//部署 = Dep

	}
	public EmployeeModel findAll() {
		conn = null;
		pStmt = null;
		try {
			// 指定したクラスをロードする（・・と、DriverManagerに登録する）

			Class.forName("com.mysql.jdbc.Driver");

			// DBにコネクションを張る（接続させる）
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

				int employeeno = rs.getInt("EmployeeNo");
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


				//String resultmsg = "ひきわけ";

				if(his.getResult().equals("1")) {				//勝敗引き分けカウント＆セット
					tr.setEnemyVictory(tr.getEnemyVictory() + 1);
					//resultmsg = his.getEnemyName() + "のかち";
				}else if(his.getResult().equals("2")) {
					tr.setMyVictory(tr.getMyVictory() + 1);
					//resultmsg = his.getMyName() + "のかち";
				}else {
					tr.setDraw(tr.getDraw() + 1);
				}


			}
			tr.setHislist(hisarraylist);

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
			// 指定したクラスをロードする（・・と、DriverManagerに登録する）

			Class.forName("com.mysql.jdbc.Driver");

			// DBにコネクションを張る（接続させる）
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
