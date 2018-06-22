package model;

public class EmployeeModel {
	private String employeeNo;
	private String employeeName;
	private String password;
	private int delFlg;
	private AuthModel authModel = new AuthModel();
	private DepModel depModel = new DepModel();

	public EmployeeModel(String employeeno, String employeename, String depno,String authno,String depname,String authname) {

		setEmployeeNo(employeeno);
		setEmployeeName(employeename);
		setDepNo(depno);
		setAuthNo(authno);
		setDepName(depname);
		setAuthName(authname);
	}

	public EmployeeModel() {}

	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
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
	public int getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
	public AuthModel getAuthModel() {
		return authModel;
	}
	public void setAuthModel(AuthModel authModel) {
		this.authModel = authModel;
	}
	public DepModel getDepModel() {
		return depModel;
	}
	public void setDepModel(DepModel depModel) {
		this.depModel = depModel;
	}
	public String getAuthNo() {
		return authModel.getAuthNo();
	}
	public void setAuthNo(String authNo) {
		authModel.setAuthNo(authNo);
	}
	public String getAuthName() {
		return authModel.getAuthName();
	}
	public void setAuthName(String authName) {
		authModel.setAuthName(authName);
	}
	public String getDepNo() {
		return depModel.getDepNo();
	}
	public void setDepNo(String depNo) {
		depModel.setDepNo(depNo);
	}
	public String getDepName() {
		return depModel.getDepName();
	}
	public void setDepName(String depName) {
		depModel.setDepName(depName);
	}
}
