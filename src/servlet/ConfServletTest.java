package servlet;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

import model.EmployeeModel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import others.LoginLogic;

public class ConfServletTest {

	@BeforeClass
	public static void setUp(){
		System.out.println("ConfServletTest開始");
	}

	@AfterClass
	public static void tearDown(){
		System.out.println("ConfServletTest終了");
	}

	@Test
	public void RegistrationServletテスト() {
		// サーブレット生成
		ConfServlet unitText = new ConfServlet();

		// リクエスト、レスポンスを生成
		// アプリケーションスコープは今回は使わない
		// MockServletContext cnt = new MockServletContext();
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();

		// リクエストにデータ設定
		EmployeeModel employee = new EmployeeModel();
		LoginLogic ll = new LoginLogic();

		employee.setEmployeeNo("aa01200120");
		employee.setDepNo("03");
		employee.setAuthNo("999");
		employee.setEmployeeName("うずまきナルト");
		employee.setPassword(ll.passHash("naruto1111"));
		HttpSession session = req.getSession();
		session.setAttribute("pageFlg", "RegistrationServlet");
		session.setAttribute("employeeModel", employee);

		// 実行！
		try {
			unitText.doPost(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 結果をチェック
		// パラメータの確認、取得
		assertThat("RegistrationServlet", is(session.getAttribute("pageFlg")));

		// フォワード先確認（response.getForwardedUrl()）
		assertThat("EmployeeServlet", is(resp.getRedirectedUrl()));
	}
	
	@Test
	public void ChangeServletテスト() {
		// サーブレット生成
		ConfServlet unitText = new ConfServlet();

		// リクエスト、レスポンスを生成
		// アプリケーションスコープは今回は使わない
		// MockServletContext cnt = new MockServletContext();
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();

		// リクエストにデータ設定

		EmployeeModel employee = new EmployeeModel();
		employee.setEmployeeNo("aa00000001");
		employee.setDepNo("01");
		employee.setAuthNo("999");
		employee.setEmployeeName("うちはサスケ");
		HttpSession session = req.getSession();
		session.setAttribute("employeeModel", employee);
		session.setAttribute("pageFlg","ChangeServlet");
		// 実行！
		try {
			unitText.doPost(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 結果をチェック
		// パラメータの確認、取得
		assertThat("ChangeServlet", is(session.getAttribute("pageFlg")));

		// フォワード先確認（response.getForwardedUrl()）
		assertThat("EmployeeServlet", is(resp.getRedirectedUrl()));
	}


}
