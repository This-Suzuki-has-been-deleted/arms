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
import dao.EmployeeDAO;

public class ChangeServletTest {

	static EmployeeDAO ed;
	static EmployeeModel em;
	static EmployeeModel next_emp;
	static LoginLogic ll;

	@BeforeClass
	public static void SetUp(){
		 ed = new EmployeeDAO();
		em = new EmployeeModel();
		ll = new LoginLogic();
		em.setEmployeeNo("aa12345678");
		em.setPassword(ll.passHash("admin1234"));
		ed.updateEmppass(em);
	}

	@AfterClass
	public static void tearDown(){
		ed = new EmployeeDAO();
		em = new EmployeeModel();
		ll = new LoginLogic();
		em.setEmployeeNo("aa12345678");
		em.setPassword(ll.passHash("admin1234"));
		ed.updateEmppass(em);
	}

	@Test
	public void infoChangerテスト() {
		// サーブレット生成
		ChangeServlet unitText = new ChangeServlet();

		// リクエスト、レスポンスを生成
		// アプリケーションスコープは今回は使わない
		// MockServletContext cnt = new MockServletContext();
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();

		// リクエストにデータ設定
		EmployeeModel employee = new EmployeeModel();
		employee.setEmployeeNo("aa12345678");
		employee.setDepNo("01");
		employee.setAuthNo("999");
		req.setParameter("employeeNo", "aa00000001");
		HttpSession session = req.getSession();
		session.setAttribute("Employee", employee);
		next_emp = new EmployeeModel();
		next_emp.setEmployeeNo("aa00000001");
		next_emp.setDepNo("01");
		next_emp.setAuthNo("001");
		session.setAttribute("ChangeEmployee", next_emp);

		req.setParameter("employeeName", "相沢");
		req.setParameter("selectDivisionNo", "02");
		req.setParameter("selectAuthorityNo", "002");

		// 実行！
		try {
			unitText.doPost(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 結果をチェック
		// パラメータの確認、取得
		assertThat("社員情報確認", is(session.getAttribute("pageTitle")));

		// セッションの中身確認・・・(今回は意味はないです。)
		// HttpSession session1 = req.getSession();
		// assertThat("ans エラー","11111",is(session1.getAttribute("ans")));

		// フォワード先確認（response.getForwardedUrl()）
		assertThat("WEB-INF/jsp/conf.jsp", is(resp.getForwardedUrl()));
	}

	@Test
	public void passChangerテスト() {
		// サーブレット生成
		ChangeServlet unitText = new ChangeServlet();

		// リクエスト、レスポンスを生成
		// アプリケーションスコープは今回は使わない
		// MockServletContext cnt = new MockServletContext();
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();

		// リクエストにデータ設定
		EmployeeModel employee = new EmployeeModel();
		employee.setEmployeeNo("aa12345678");
		employee.setDepNo("01");
		employee.setAuthNo("999");
		HttpSession session = req.getSession();
		session.setAttribute("Employee", employee);
		req.setParameter("pass", "admin1234");
		req.setParameter("nextPass", "admin5678");

		// 実行！
		try {
			unitText.doPost(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 結果をチェック
		// パラメータの確認、取得
		assertThat("パスワード変更しました。", is(session.getAttribute("Msg")));

		// エラーメッセージ内容確認
		assertThat(null,is(session.getAttribute("eMsg")));

		// フォワード先確認（response.getForwardedUrl()）
		assertThat("index.jsp", is(resp.getRedirectedUrl()));
	}
}
