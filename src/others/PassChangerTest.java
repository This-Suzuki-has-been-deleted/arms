package others;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

import model.EmployeeModel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class PassChangerTest {
	static MockHttpServletRequest req;
	static MockHttpServletResponse resp;

	@BeforeClass
	public static void SetUp() {
		req = new MockHttpServletRequest();
		resp = new MockHttpServletResponse();
		HttpSession session = req.getSession();
		EmployeeModel em = new EmployeeModel();
		em.setEmployeeNo("aa00000001");
		em.setEmployeeName("高橋ゆうすけ");
		em.setPassword("bd94dcda26fccb4e68d6a31f9b5aac0b571ae266d822620e901ef7ebe3a11d4f");
		em.setDepNo("01");
		em.setDepName("総務部");
		em.setAuthNo("001");
		em.setAuthName("管理者");
		em.setDelFlg(0);

		session.setAttribute("em", em);
		System.out.println("テスト開始");
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("テスト終了");
	}

	@Test
	public void indexOutTest() {
		HttpSession session = req.getSession();
		EmployeeModel em = new EmployeeModel();
		em = (EmployeeModel) session.getAttribute("em");
		PassChanger pc = new PassChanger();
		try {
			pc.indexOut(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (em != null) {
			if (em.getPassword()
					.equals("bd94dcda26fccb4e68d6a31f9b5aac0b571ae266d822620e901ef7ebe3a11d4f")) {
				// session.setAttribute("pageTitle", "初期パスワード変更");
				assertThat("/WEB-INF/jsp/passChange.jsp",is(resp.getForwardedUrl()));
			}
		} else {
			assertThat("/WEB-INF/jsp/login.jsp", is(resp.getForwardedUrl()));
		}
	}
}
