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

public class RegistrationServletTest {
	static MockHttpServletRequest req;
	static MockHttpServletResponse resp;
	@BeforeClass
	public static void setUp(){
		req = new MockHttpServletRequest();
		resp = new MockHttpServletResponse();
		HttpSession session = req.getSession();
		EmployeeModel em = new EmployeeModel();
		em.setEmployeeNo("aa12345678");
		em.setEmployeeName("高橋ゆうすけ");
		em.setPassword("ac9689e2272427085e35b9d3e3e8bed88cb3434828b43b86fc0596cad4c6e270");
		em.setDepNo("01");
		em.setDepName("総務部");
		em.setAuthNo("999");
		em.setAuthName("管理者");
		em.setDelFlg(0);
		session.setAttribute("Employee", em);

		System.out.println("テスト開始");
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("テスト終了");
	}

	@Test
	public void registrationTest(){
		HttpSession session = req.getSession();
		req.setParameter("textCode","aa00010001");
		req.setParameter("textName","高鷲ゆうすけ");
		req.setParameter("selectDivisionNo","01");
		req.setParameter("selectAuthorityNo","999");
		RegistrationServlet rs = new RegistrationServlet();

		try {
			rs.doPost(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String msg = (String)session.getAttribute("eMsg");

		if (msg != null) {
			System.out.println("ページ遷移先：/WEB-INF/jsp/employeeRegistration.jsp");
			assertThat("/WEB-INF/jsp/employeeRegistration.jsp",is(resp.getForwardedUrl()));
		}else{
			System.out.println("ページ遷移先：/WEB-INF/jsp/conf.jsp");
			assertThat("/WEB-INF/jsp/conf.jsp",is(resp.getForwardedUrl()));
		}
	}

}
