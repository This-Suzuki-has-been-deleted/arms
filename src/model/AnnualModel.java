package model;

import java.sql.Time;

public class AnnualModel {
	private String employeeNo;
	private int year;
	private Time y_workTime;
	private Time y_overTime;
	private Time y_nightTime;
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
	public Time getY_workTime() {
		return y_workTime;
	}
	public void setY_workTime(Time y_workTime) {
		this.y_workTime = y_workTime;
	}
	public Time getY_overTime() {
		return y_overTime;
	}
	public void setY_overTime(Time y_overTime) {
		this.y_overTime = y_overTime;
	}
	public Time getY_nightTime() {
		return y_nightTime;
	}
	public void setY_nightTime(Time y_nightTime) {
		this.y_nightTime = y_nightTime;
	}


}
