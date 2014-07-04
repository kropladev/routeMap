package dev.kropla.connection.webservice.pojo;

public class MethodArgChangeStatus {
	public static final String FIELDNAME_LOGIN="userAccountLogin";
	public static final String FIELDNAME_STATUS="status";
	
	private String userAccountLogin;
	private String status;
	
	public String getUserAccountLogin() {
		return userAccountLogin;
	}
	public void setUserAccountLogin(String userAccountLogin) {
		this.userAccountLogin = userAccountLogin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
