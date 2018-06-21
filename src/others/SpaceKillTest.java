package others;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpaceKillTest {
	static SpaceKill sk;
	@BeforeClass
	public static void SetUp (){
		sk = new SpaceKill();
		System.out.println("テスト開始");
	}

	@AfterClass
	public static void tearDown(){
		System.out.println("テスト終了");
	}
	@Test
	public void spaceCheck(){
		assertEquals("スペース消えてない！","", sk.stringSpaceKill("　 "));
	}
}
