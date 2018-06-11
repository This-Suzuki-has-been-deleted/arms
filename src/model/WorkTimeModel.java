package model;

import java.sql.Date;
import java.util.Calendar;

public class WorkTimeModel {
	private String employeeNo;
	private int year;
	private int month;
	private int day;
	private boolean workTimeFlg;
	private Date attendance;
	private Date leaving;
	private boolean workFlg;
	private String week;

	/**
	 * 年月日に対応する曜日を取得し、受け取ったWorkTimeModelにセットするメソッド
	 *
	 * @param wt
	 */
	public void weekDate(WorkTimeModel wt) {
		Calendar cal = Calendar.getInstance();
		int y = wt.getYear();
		int m = wt.getMonth();
		int d = wt.getDay();

		cal.set(y, m, d);
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

	public boolean isWorkTimeFlg() {
		return workTimeFlg;
	}

	public void setWorkTimeFlg(boolean workTimeFlg) {
		this.workTimeFlg = workTimeFlg;
	}

	/**
	 * attendanceを取得します。
	 *
	 * @return attendance
	 */
	public Date getAttendance() {
		return attendance;
	}

	/**
	 * attendanceを設定します。
	 *
	 * @param attendance
	 *            attendance
	 */
	public void setAttendance(Date attendance) {
		this.attendance = attendance;
	}

	/**
	 * leavingを取得します。
	 *
	 * @return leaving
	 */
	public Date getLeaving() {
		return leaving;
	}

	/**
	 * leavingを設定します。
	 *
	 * @param leaving
	 *            leaving
	 */
	public void setLeaving(Date leaving) {
		this.leaving = leaving;
	}

	public boolean isWorkFlg() {
		return workFlg;
	}

	public void setWorkFlg(boolean workFlg) {
		this.workFlg = workFlg;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

}
