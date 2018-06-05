package model;

import java.sql.Time;

public class MonthlyModel {
	private String employeeNo;
	private int year;
	private int Month;
	private Time m_workTime;
	private Time m_overTime;
	private Time m_nightTime;
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
		return Month;
	}
	public void setMonth(int month) {
		Month = month;
	}
	public Time getM_workTime() {
		return m_workTime;
	}
	public void setM_workTime(Time m_workTime) {
		this.m_workTime = m_workTime;
	}
	public Time getM_overTime() {
		return m_overTime;
	}
	public void setM_overTime(Time m_overTime) {
		this.m_overTime = m_overTime;
	}
	public Time getM_nightTime() {
		return m_nightTime;
	}
	public void setM_nightTime(Time m_nightTime) {
		this.m_nightTime = m_nightTime;
	}


}
