package model;

import java.sql.Date;

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
	 * @return attendance
	 */
	public Date getAttendance() {
	    return attendance;
	}
	/**
	 * attendanceを設定します。
	 * @param attendance attendance
	 */
	public void setAttendance(Date attendance) {
	    this.attendance = attendance;
	}
	/**
	 * leavingを取得します。
	 * @return leaving
	 */
	public Date getLeaving() {
	    return leaving;
	}
	/**
	 * leavingを設定します。
	 * @param leaving leaving
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
