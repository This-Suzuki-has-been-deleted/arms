package model;

import java.sql.Timestamp;



public class MonthlyModel {
	private String employeeNo;
	private int year;
	private int Month;
	private Timestamp m_workTime;
	private Timestamp m_overTime;
	private Timestamp m_nightTime;
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
	/**
	 * m_workTimeを取得します。
	 * @return m_workTime
	 */
	public Timestamp getM_workTime() {
	    return m_workTime;
	}
	/**
	 * m_workTimeを設定します。
	 * @param m_workTime m_workTime
	 */
	public void setM_workTime(Timestamp m_workTime) {
	    this.m_workTime = m_workTime;
	}
	/**
	 * m_overTimeを取得します。
	 * @return m_overTime
	 */
	public Timestamp getM_overTime() {
	    return m_overTime;
	}
	/**
	 * m_overTimeを設定します。
	 * @param m_overTime m_overTime
	 */
	public void setM_overTime(Timestamp m_overTime) {
	    this.m_overTime = m_overTime;
	}
	/**
	 * m_nightTimeを取得します。
	 * @return m_nightTime
	 */
	public Timestamp getM_nightTime() {
	    return m_nightTime;
	}
	/**
	 * m_nightTimeを設定します。
	 * @param timestamp m_nightTime
	 */
	public void setM_nightTime(Timestamp timestamp) {
	    this.m_nightTime = timestamp;
	}


}
