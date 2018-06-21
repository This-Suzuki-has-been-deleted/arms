package others;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class LoginLogicTest {

	static LoginLogic loginlogic;

	@BeforeClass
	public static void SetUp (){
		loginlogic = new LoginLogic();
		System.out.println("テスト開始");
	}

	@AfterClass
	public static void tearDown(){
		System.out.println("テスト終了");
	}

	@Test
	public void PassHashCheck(){
		assertNotNull("結果がnullになってる", loginlogic.passHash("abcd1234"));
	}

	@Test
	// 評価するメソッドの戻り値がtrueかどうか
	public void loginCheck(){
		assertTrue("IDとPW一致してない",loginlogic.login("aa12345678",loginlogic.passHash("admin1234")));
	}
}
