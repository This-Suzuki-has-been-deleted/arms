package model;

public class DepModel {
	private String depNo;
	private String depName;
	private int depFlg;

	public String getDepNo() {
		return depNo;
	}
	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	/**
	 * depFlgを取得します。
	 * @return depFlg
	 */
	public int getDepFlg() {
	    return depFlg;
	}
	/**
	 * depFlgを設定します。
	 * @param depFlg depFlg
	 */
	public void setDepFlg(int depFlg) {
	    this.depFlg = depFlg;
	}

}
