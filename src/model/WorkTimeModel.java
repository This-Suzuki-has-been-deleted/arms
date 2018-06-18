package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class WorkTimeModel {
	private String employeeNo;
	private int year;
	private int month;
	private int day;
	private int workTimeFlg;
	private Timestamp attendance;
	private Timestamp leaving;
	private int workFlg;
	private String week;
	private int workTimeH;
	private int workTimeM;
	private int overTimeH;
	private int overTimeM;
	private int nightTimeH;
	private int nightTimeM;

	/**
	 * 年月日に対応する曜日を取得し、受け取ったWorkTimeModelにセットするメソッド
	 *
	 * @param wt
	 */
	public void weekDate(WorkTimeModel wt) {
		Calendar cal = Calendar.getInstance();
		int y = wt.getYear();
		int m = wt.getMonth();
		int d = wt.getDay()-2;

		cal.set(y, m, d);

		System.out.println(cal.get(Calendar.DAY_OF_WEEK));

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: // Calendar.SUNDAY:1 （値。意味はない）
			// 日曜日
			this.setWeek("日曜日");
			break;
		case Calendar.MONDAY: // Calendar.MONDAY:2
			// 月曜日
			this.setWeek("月曜日");
			break;
		case Calendar.TUESDAY: // Calendar.TUESDAY:3
			// 火曜日
			this.setWeek("火曜日");
			break;
		case Calendar.WEDNESDAY: // Calendar.WEDNESDAY:4
			// 水曜日
			this.setWeek("水曜日");
			break;
		case Calendar.THURSDAY: // Calendar.THURSDAY:5
			// 木曜日
			this.setWeek("木曜日");
			break;
		case Calendar.FRIDAY: // Calendar.FRIDAY:6
			// 金曜日
			this.setWeek("金曜日");
			break;
		case Calendar.SATURDAY: // Calendar.SATURDAY:7
			// 土曜日
			this.setWeek("土曜日");
			break;
		}
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int isWorkTimeFlg() {
		return workTimeFlg;
	}

	public void setWorkTimeFlg(int workTimeFlg) {
		this.workTimeFlg = workTimeFlg;
	}

	/**
	 * attendanceを取得します。
	 *
	 * @return attendance
	 */
	public Timestamp getAttendance() {
		return attendance;
	}

	/**
	 * attendanceを設定します。
	 *
	 * @param attendance
	 *            attendance
	 */
	public void setAttendance(Timestamp attendance) {
		this.attendance = attendance;
	}

	/**
	 * leavingを取得します。
	 *
	 * @return leaving
	 */
	public Timestamp getLeaving() {
		return leaving;
	}

	/**
	 * leavingを設定します。
	 *
	 * @param leaving
	 *            leaving
	 */
	public void setLeaving(Timestamp leaving) {
		this.leaving = leaving;
	}

	public int isWorkFlg() {
		return workFlg;
	}

	public void setWorkFlg(int workFlg) {
		this.workFlg = workFlg;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public int getWorkTimeH() {
		return workTimeH;
	}

	public void setWorkTimeH(int workTimeH) {
		this.workTimeH = workTimeH;
	}

	public int getWorkTimeM() {
		return workTimeM;
	}

	public void setWorkTimeM(int workTimeM) {
		this.workTimeM = workTimeM;
	}

	public int getOverTimeH() {
		return overTimeH;
	}

	public void setOverTimeH(int overTimeH) {
		this.overTimeH = overTimeH;
	}

	public int getOverTimeM() {
		return overTimeM;
	}

	public void setOverTimeM(int overTimeM) {
		this.overTimeM = overTimeM;
	}

	public int getNightTimeH() {
		return nightTimeH;
	}

	public void setNightTimeH(int nightTimeH) {
		this.nightTimeH = nightTimeH;
	}

	public int getNightTimeM() {
		return nightTimeM;
	}

	public void setNightTimeM(int nightTimeM) {
		this.nightTimeM = nightTimeM;
	}
}
