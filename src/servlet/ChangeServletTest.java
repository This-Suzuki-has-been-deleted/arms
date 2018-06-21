package servlet;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import model.EmployeeModel;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

public class ChangeServletTest {

	@Test
	public void infoChangerテスト() {
		// サーブレット生成
		ChangeServlet unitText = new ChangeServlet();

		// リクエスト、レスポンスを生成
		// アプリケーションスコープは今回は使わない
		// MockServletContext cnt = new MockServletContext();
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();

		// リクエストにデータ設定
		EmployeeModel employee = new EmployeeModel();
		employee.setEmployeeNo("aa12345678");
		employee.setDepNo("01");
		employee.setAuthNo("999");
		session.setAttribute("Employee", employee);
		req.setParameter("employeeNo", "aa00000001");

		// もし、セッションに設定する必要があれば・・・(今回は意味はないです)
		// HttpSession session = req.getSession();
		// session.setAttribute("ans","11111");

		// 実行！
		try {
			unitText.doPost(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 結果をチェック
		// パラメータの確認、取得
		assertThat("（成功しました）", is(req.getAttribute("msg")));

		// セッションの中身確認・・・(今回は意味はないです。)
		// HttpSession session1 = req.getSession();
		// assertThat("ans エラー","11111",is(session1.getAttribute("ans")));

		// フォワード先確認（response.getForwardedUrl()）
		assertThat("WEB-INF/jsp/infoChange.jsp", is(resp.getForwardedUrl()));
	}
}
