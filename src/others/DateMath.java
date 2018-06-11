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
		if(diff<=0){
			diff=0;
		}
		return diff;
	}


	/**
	 * 深夜時間計算の時、22時以降に業務を開始した際を考慮した
	 * 二つの時間の差(分)を算出するメソッド 未実装です
	 * @param date1
	 * @param date2
	 * @param startTime
	 * @return
	 */
	public int nightTimeDiff(long date1,long date2,long startTime){

		int diff = (int)(date1 - date2) / (1000 * 60);
		if(diff<=0){
			diff=0;
		}
		return diff;
	}

	/**
	 * 呼び出された日の定時をlongで返すメソッド
	 * @param year
	 * @param month
	 * @param date
	 * @return fix
	 */
	public long fixedTime(int year,int month,int date){
		Calendar cal = Calendar.getInstance();
		long fix;

		cal.set(year, month, date,17,0,0);
		java.util.Date dates = cal.getTime();
		fix = dates.getTime();
		return fix;
	}

	/**
	 * 呼び出された日の深夜時間開始時刻をlongで返すメソッド
	 * @param year
	 * @param month
	 * @param date
	 * @return over
	 */
	public long overTime(int year,int month,int date){
		Calendar cal = Calendar.getInstance();
		long over;

		cal.set(year, month, date,22,0,0);
		java.util.Date dates = cal.getTime();
		over = dates.getTime();
		return over;
	}
}
