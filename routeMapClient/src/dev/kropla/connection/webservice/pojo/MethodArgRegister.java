package dev.kropla.connection.webservice.pojo;

public class MethodArgRegister {
	public static final String FIELDNAME_USER_ACCOUNT_NAME = "userAccountName";
	public static final String FIELDNAME_USER_MAIL="userMailName";
	public static final String FIELDNAME_USER_PHONE = "userPhone";
	public static final String FIELDNAME_API_KEY = "apiRegKey";
	public static final String FIELDNAME_USER_FNAME = "userFName";
	public static final String FIELDNAME_USER_LNAME = "userLName";
	private String userAccountName;
	private String userMailName;
	private String userPhone;
	private String apiRegKey;
	private String userFName;
	private String userLName;
	
	
	public String getUserAccountName() {
		return userAccountName;
	}
	public void setUserAccountName(String userAccountName) {
		this.userAccountName = userAccountName;
	}
	public String getUserMailName() {
		return userMailName;
	}
	public void setUserMailName(String userMailName) {
		this.userMailName = userMailName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getApiRegKey() {
		return apiRegKey;
	}
	public void setApiRegKey(String apiRegKey) {
		this.apiRegKey = apiRegKey;
	}
	public String getUserFName() {
		return userFName;
	}
	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}
	public String getUserLName() {
		return userLName;
	}
	public void setUserLName(String userLName) {
		this.userLName = userLName;
	}
	@Override
	public String toString() {
		return "MethodArgRegister [userAccountName=" + userAccountName
				+ ", userMailName=" + userMailName + ", userPhone=" + userPhone
				+ ", apiRegKey=" + apiRegKey + ", userFName=" + userFName
				+ ", userLName=" + userLName + "]";
	}
	
	
}
