package others;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateMath {

	/**
	 * 日付に対する分の加算を行うメソッド
	 *
	 * @param workTime
	 * @param minute
	 * @return resultDate
	 */
	public Timestamp addMinute(Date workTime, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(workTime);
		cal.add(Calendar.MINUTE, minute);
		Date temp = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dates = sdf.format(temp);
		Timestamp resultDate = Timestamp.valueOf(dates);
		return resultDate;
	}

	/**
	 * 二つの日付の差(分)を算出するメソッド
	 *
	 * @param date1
	 * @param date2
	 * @return diff
	 */
	public int diff(long date1, long date2) {
		long diff = (date1 - date2) / (1000 * 60);
		if (diff <= 0) {
			diff = 0;
		}
		int dif = (int)diff;
		return dif;
	}

	/**
	 * 呼び出された日の定時をlongで返すメソッド
	 *
	 * @param year
	 * @param month
	 * @param date
	 * @return fix
	 */
	public long fixedTime(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		long fix;

		cal.clear(Calendar.MILLISECOND);
		cal.set(year, month-1, date, 18, 0, 0);
		java.util.Date dates = cal.getTime();
		fix = dates.getTime();
		return fix;
	}

	/**
	 * 呼び出された日の深夜時間開始時刻をlongで返すメソッド
	 *
	 * @param year
	 * @param month
	 * @param date
	 * @return over
	 */
	public long overTime(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		long over;

		cal.clear(Calendar.MILLISECOND);
		cal.set(year, month-1, date, 22, 0, 0);
		java.util.Date dates = cal.getTime();
		over = dates.getTime();
		return over;
	}

	/**
	 * 引数の文字列からから"年月日時分"を除去し、
	 * 既定のフォーマットに治すメソッド
	 *
	 * @param date
	 * @return date
	 */
	public String replaceDate(String date){
		date = date.replace("年", "-");
		date = date.replace("月", "-");
		date = date.replace("日", "");
		date = date.replace("時", ":");
		date = date.replace("分", ":00");

		return date;
	}
}
