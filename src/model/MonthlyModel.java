package model;

import java.util.Date;

public class MonthlyModel {
	private String employeeNo;
	private int year;
	private int Month;
	private Date m_workTime;
	private Date m_overTime;
	private Date m_nightTime;
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
	public Date getM_workTime() {
	    return m_workTime;
	}
	/**
	 * m_workTimeを設定します。
	 * @param m_workTime m_workTime
	 */
	public void setM_workTime(Date m_workTime) {
	    this.m_workTime = m_workTime;
	}
	/**
	 * m_overTimeを取得します。
	 * @return m_overTime
	 */
	public Date getM_overTime() {
	    return m_overTime;
	}
	/**
	 * m_overTimeを設定します。
	 * @param m_overTime m_overTime
	 */
	public void setM_overTime(Date m_overTime) {
	    this.m_overTime = m_overTime;
	}
	/**
	 * m_nightTimeを取得します。
	 * @return m_nightTime
	 */
	public Date getM_nightTime() {
	    return m_nightTime;
	}
	/**
	 * m_nightTimeを設定します。
	 * @param m_nightTime m_nightTime
	 */
	public void setM_nightTime(Date m_nightTime) {
	    this.m_nightTime = m_nightTime;
	}


}
