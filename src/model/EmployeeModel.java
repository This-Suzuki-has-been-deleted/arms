package model;

public class EmployeeModel {
	private String employeeNo;
	private int depNo;
	private int authNo;
	private String employeeName;
	private String password;
	private boolean delFlg;
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public int getDepNo() {
		return depNo;
	}
	public void setDepNo(int depNo) {
		this.depNo = depNo;
	}
	public int getAuthNo() {
		return authNo;
	}
	public void setAuthNo(int authNo) {
		this.authNo = authNo;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isDelFlg() {
		return delFlg;
	}
	public void setDelFlg(boolean delFlg) {
		this.delFlg = delFlg;
	}


}
