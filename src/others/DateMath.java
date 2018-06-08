package others;

import java.sql.Date;
import java.util.Calendar;

public class DateMath {

	/**
	 * 日付に対する分の加算を行うメソッド
	 * @param date
	 * @param minute
	 * @return resultDate
	 */
	public Date addMinute(Date date,int minute){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		java.util.Date temp = cal.getTime();
		String dates = temp.toString();
		Date resultDate = java.sql.Date.valueOf(dates);
		return resultDate;
	}

	/**
	 * 二つの日付の差(分)を算出するメソッド
	 * @param date1
	 * @param date2
	 * @return diff
	 */
	public int diff(long date1,long date2){
		int diff = (int)(date1 - date2) / (1000 * 60);
		return diff;
	}
}
