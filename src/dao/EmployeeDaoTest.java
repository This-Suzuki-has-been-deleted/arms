package dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.EmployeeModel;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmployeeDaoTest {

	Connection conn = null;
	IDatabaseConnection connection = null;
	EmployeeDAO edao = new EmployeeDAO();
	@Before
	public void setUp() throws Exception {
		try {
			// JDBCドライバーをセット
			Class.forName("com.mysql.jdbc.Driver");
			// Connectionの取得
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/arms"
									+ "?verifyServerCertificate =false&useSSL=false&requireSSL = false",
							"root", "password");

			// IDatabaseConnectionの作成
			connection = new DatabaseConnection(conn);

		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		if (connection != null) {
			connection.close();
		}
		if (conn != null) {
			conn.close();
		}
	}




	public void  社員情報取得機能()throws Exception {

		EmployeeDAO edao = new EmployeeDAO();

		edao.findEmployee("aa12345678");

		try {
			// 期待値リストをxmlファイルから読み込む
			IDataSet expectedSet = new FlatXmlDataSetBuilder()
					.build(new FileInputStream(
							"C:\\Users\\user10\\git\\arms\\TestData\\findEmployee_test.xml"));

			// 抽出するSQLを作成
			String actualSql = "select * from testtable where EmployeeNo ='aa12345678'";

			// 作成日、更新日など照合の対象とならないデータです。
			// (注意：対象外のカラムが無くても空文字を引数として渡します)
			// String[] ignoreCols={""}; ←このような形。
			String[] ignoreCols = {""};

			// ここで対象となるデータとxmlが等しいかをチェックできます。
			Assertion.assertEqualsByQuery(expectedSet, // 期待値
					this.connection,// コネクション
					actualSql, // SQL文
					"testtable", // 対象テーブル
					ignoreCols // 比較しないカラム名の配列
					);
		} catch (IOException | DatabaseUnitException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			// DB切断
			if (connection != null) {
				connection.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void 社員情報検索() throws Exception {


		EmployeeDAO edao = new EmployeeDAO();

		List<EmployeeModel> actualList = edao.findByNameDep("aa12345678","01","小",1);

		//期待値リスト
		List<EmployeeModel> expectedList = new ArrayList<EmployeeModel>();

		EmployeeModel emodel1 = new EmployeeModel(
				"bb12345678",
				"小澤",
				"01",
				"999",
				"総務部",
				"一般"
				);
		EmployeeModel emodel2 = new EmployeeModel(
				"aa65374665",
				"小澤ラプンツェル",
				"01",
				"001",
				"総務部",
				"一般"
				);

		//リストに保存
		expectedList.add(emodel1);
		expectedList.add(emodel2);

		//【比較]取得数が同じであること。
		assertThat("ListSize："+actualList.size(),is("ListSize："+expectedList.size()));

		//【比較】取得内容が同じであること
		for(int i=0 ; actualList.size() > i ; i++){
			assertThat(actualList.get(i),
					is(SamePropertyValuesAs.samePropertyValuesAs(expectedList.get(i))));
		}



	}

	public void 全社員一覧() throws Exception {

		EmployeeDAO edao = new EmployeeDAO();

		edao.findByAll("高橋",1);

		try {
			// 期待値リストをxmlファイルから読み込む
			IDataSet expectedSet = new FlatXmlDataSetBuilder()
					.build(new FileInputStream(
							"C:\\Users\\user10\\git\\arms\\TestData\\findByAll_test.xml"));

			// 抽出するSQLを作成
			String actualSql = "select * from employee where EmployeeNo ='aa87654321'";

			// 作成日、更新日など照合の対象とならないデータです。
			// (注意：対象外のカラムが無くても空文字を引数として渡します)
			// String[] ignoreCols={""}; ←このような形。
			String[] ignoreCols = {""};

			// ここで対象となるデータとxmlが等しいかをチェックできます。
			Assertion.assertEqualsByQuery(expectedSet, // 期待値
					this.connection,// コネクション
					actualSql, // SQL文
					"testtable", // 対象テーブル
					ignoreCols // 比較しないカラム名の配列
					);
		} catch (IOException | DatabaseUnitException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			// DB切断
			if (connection != null) {
				connection.close();
			}
			if (conn != null) {
				conn.close();
			}
		}



	}


	public void 検索社員件数() throws Exception {

		EmployeeDAO edao = new EmployeeDAO();


		int counter = edao.CountEmp("02","石");

		assertEquals("正しくないです。",1,counter);
	}


	// 【テスト②】下記データの登録を行う。
	// 【期待値】事前登録した内容含め2件の結果と内容が一致すること
	@Test
	// テーブルのデータセットのテスト
	public void 社員情報追加機能() throws Exception {

		EmployeeModel emodel = new EmployeeModel();
		emodel.setEmployeeNo("aa87654321");
		emodel.setEmployeeName("test君");
		emodel.setAuthNo("001");
		emodel.setDepNo("01");
		emodel.setPassword("bd94dcda26fccb4e68d6a31f9b5aac0b571ae266d822620e901ef7ebe3a11d4f");
		edao.insertEmployee(emodel);

		try {
			// 期待値リストをxmlファイルから読み込む
			IDataSet expectedSet = new FlatXmlDataSetBuilder()
					.build(new FileInputStream(
							"C:\\Users\\user10\\git\\arms\\TestData\\insert_test.xml"));

			// 抽出するSQLを作成
			String actualSql = "select EmployeeNo,EmployeeDivisionNo,EmployeeAuthorityNo,EmployeeName from employee where EmployeeNo ='aa87654321'";

			// 作成日、更新日など照合の対象とならないデータです。
			// (注意：対象外のカラムが無くても空文字を引数として渡します)
			// String[] ignoreCols={""}; ←このような形。
			String[] ignoreCols = {""};

			// ここで対象となるデータとxmlが等しいかをチェックできます。
			Assertion.assertEqualsByQuery(expectedSet, // 期待値
					this.connection,// コネクション
					actualSql, // SQL文
					"testtable", // 対象テーブル
					ignoreCols // 比較しないカラム名の配列
					);
		} catch (IOException | DatabaseUnitException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			// DB切断
			if (connection != null) {
				connection.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

	public void 社員情報変更() throws SQLException {

		EmployeeModel emodel = new EmployeeModel();
		emodel.setEmployeeNo("aa87654321");
		emodel.setEmployeeName("testちゃん");
		emodel.setPassword("bd94dcda26fccb4e68d6a31f9b5aac0b571ae266d822620e901ef7ebe3a11d4f");
		emodel.setDelFlg(0);
		emodel.setAuthNo("02");
		emodel.setDepNo("002");

		edao.insertEmployee(emodel);

		try {
			// 期待値リストをxmlファイルから読み込む
			IDataSet expectedSet = new FlatXmlDataSetBuilder()
					.build(new FileInputStream(
							"C:\\Users\\user10\\git\\arms\\TestData\\update_test.xml"));

			// 抽出するSQLを作成
			String actualSql = "select * from employee where ID ='aa87654321'";

			// 作成日、更新日など照合の対象とならないデータです。
			// (注意：対象外のカラムが無くても空文字を引数として渡します)
			// String[] ignoreCols={""}; ←このような形。
			String[] ignoreCols = { "create_dt", "update_dt" };

			// ここで対象となるデータとxmlが等しいかをチェックできます。
			Assertion.assertEqualsByQuery(expectedSet, // 期待値
					this.connection,// コネクション
					actualSql, // SQL文
					"testtable", // 対象テーブル
					ignoreCols // 比較しないカラム名の配列
					);
		} catch (IOException | DatabaseUnitException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			// DB切断
			if (connection != null) {
				connection.close();
			}
			if (conn != null) {
				conn.close();
			}
		}


	}
}
