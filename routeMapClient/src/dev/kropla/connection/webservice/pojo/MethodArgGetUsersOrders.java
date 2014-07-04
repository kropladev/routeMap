package dev.kropla.connection.webservice.pojo;

public class MethodArgGetUsersOrders {
	public static final String FIELDNAME_USER_LOGIN = "userLogin";
	public static final String FIELDNAME_STATUS_SYMBOL = "statusSymbol";
	private String statusSymbol;
	private String userLogin;
	public String getStatusSymbol() {
		return statusSymbol;
	}
	public void setStatusSymbol(String statusSymbol) {
		this.statusSymbol = statusSymbol;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	
	@Override
	public String toString() {
		return "MethodArgGetUsersOrders [statusSymbol=" + statusSymbol
				+ ", userLogin=" + userLogin + "]";
	}
	
}
