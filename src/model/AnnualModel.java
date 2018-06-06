package model;

import java.util.Date;

public class AnnualModel {
	private String employeeNo;
	private int year;
	private Date y_workTime;
	private Date y_overTime;
	private Date y_nightTime;
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
	/**
	 * y_workTimeを取得します。
	 * @return y_workTime
	 */
	public Date getY_workTime() {
	    return y_workTime;
	}
	/**
	 * y_workTimeを設定します。
	 * @param y_workTime y_workTime
	 */
	public void setY_workTime(Date y_workTime) {
	    this.y_workTime = y_workTime;
	}
	/**
	 * y_overTimeを取得します。
	 * @return y_overTime
	 */
	public Date getY_overTime() {
	    return y_overTime;
	}
	/**
	 * y_overTimeを設定します。
	 * @param y_overTime y_overTime
	 */
	public void setY_overTime(Date y_overTime) {
	    this.y_overTime = y_overTime;
	}
	/**
	 * y_nightTimeを取得します。
	 * @return y_nightTime
	 */
	public Date getY_nightTime() {
	    return y_nightTime;
	}
	/**
	 * y_nightTimeを設定します。
	 * @param y_nightTime y_nightTime
	 */
	public void setY_nightTime(Date y_nightTime) {
	    this.y_nightTime = y_nightTime;
	}

}
