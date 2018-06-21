package others;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDateMath {

	static DateMath dateMath;

	@BeforeClass
	//事前処理
	public static void setUp(){
		System.out.println("DateMathテスト開始");
        dateMath = new DateMath();
	}

	@AfterClass
	//事後処理
	public static void tearDown(){
		System.out.println("DateMathテスト終了");
	}

	@Test
	//TimeStampに指定した分数を加算出来るかテスト
	public void testAddMinute() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = sdf.parse("2018/01/01 00:00:00");
		Timestamp ts = Timestamp.valueOf("2018-01-01 00:10:00.000000");
		assertEquals("加算出来ていない",ts,dateMath.addMinute(date, 10));
	}
	@Test
	//正しく差が算出されるかテスト
	public void testDiff() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date temp_date1 = sdf.parse("2018/01/01 00:00:00");
		Date temp_date2 = sdf.parse("2018/01/01 00:10:00");
		long date1 = temp_date1.getTime();
		long date2 = temp_date2.getTime();
		assertEquals("差が正しく出ていない",10,dateMath.diff(date1, date2));
	}

	@Test
	//正しく指定した日の18時が出るか
	public void testFixedTime(){
		long stamp = 1514797200L;
		assertEquals("18時ではない",stamp,dateMath.fixedTime(2018,1,1));
	}

	@Test
	//正しく指定した日の22時が出るか
	public void testOverTime(){
		long stamp = 1514811600L;
		assertEquals("22時ではない",stamp,dateMath.overTime(2018, 1, 1));
	}
	@Test
	//年月日時分が取り除かれているか
	public void testReplaceDate(){
		String date = "2018年01月01日 00時00分";
		assertEquals("取り除かれていない","2018-01-01 00:00:00",dateMath.replaceDate(date));
	}

}
