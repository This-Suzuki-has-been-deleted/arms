package model;

import java.sql.Time;

public class WorkTimeModel {
	private String employeeNo;
	private int year;
	private int month;
	private int day;
	private boolean workTimeFlg;
	private Time attendance;
	private Time leaving;
	private boolean workFlg;
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
	public Time getAttendance() {
		return attendance;
	}
	public void setAttendance(Time attendance) {
		this.attendance = attendance;
	}
	public Time getLeaving() {
		return leaving;
	}
	public void setLeaving(Time leaving) {
		this.leaving = leaving;
	}
	public boolean isWorkFlg() {
		return workFlg;
	}
	public void setWorkFlg(boolean workFlg) {
		this.workFlg = workFlg;
	}


}
