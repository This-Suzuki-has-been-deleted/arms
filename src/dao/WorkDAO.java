package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NamingException;

import model.WorkTimeModel;

public class WorkDAO {

	public void insertWorkTime(WorkTimeModel wt) throws NamingException, SQLException{
		Connection conn=null;
		PreparedStatement pstmt=null;

		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gameinfo?verifyServerCertificate=false&useSSL=false&requireSSL=false","root","password");
	}
}
