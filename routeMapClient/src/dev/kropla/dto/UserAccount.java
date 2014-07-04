package dev.kropla.dto;

public class UserAccount {
	//from rm_driver_account
	private String email;
	private String phone;
	private String regId;
	
	//from rm_drivers
	private int driversId;
	private String firstName;
	private String lastName;
	private String login;
	
	//from rm_vehicles
	private String name;
	private int vehicleId;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
/*	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}*/
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDriversId() {
		return driversId;
	}
	public void setDriversId(int driversId) {
		this.driversId = driversId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	@Override
	public String toString() {
		return "UserAccount [email=" + email + ", phone=" + phone + ", regId="
				+ regId + ", driversId=" + driversId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", login=" + login
				+ ", name=" + name + ", vehicleId=" + vehicleId + "]";
	}
	
	
}
