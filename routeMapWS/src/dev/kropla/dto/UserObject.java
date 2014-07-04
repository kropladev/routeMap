package dev.kropla.dto;

public class UserObject {
	//rm_driver_account table
	private int rdaId; //account id - podawane do logowania
	private int rmDriversRdId;
	private String rdaMail;
	private String rdaPhone;
	private String rdaRegId;
	private String rdaStatus;
	private String rdsStatDescAdd;
	private int rdaStatusId;
	private int rdsStatusId;
	//rm_drivers table
	private int rdid;
	private String rdFirstName;
	private String rdLastName;
	private String rdLogin;
	private String rmVehiclesRvId;
	private int rvId;
	
	//private VehicleObjects vehicle;

	public int getRdaId() {
		return rdaId;
	}

	public void setRdaId(int rdaId) {
		this.rdaId = rdaId;
	}

	public int getRmDriversRdId() {
		return rmDriversRdId;
	}

	public void setRmDriversRdId(int rmDriversRdId) {
		this.rmDriversRdId = rmDriversRdId;
	}

	public String getRdaMail() {
		return rdaMail;
	}

	public void setRdaMail(String rdaMail) {
		this.rdaMail = rdaMail;
	}

	public String getRdaPhone() {
		return rdaPhone;
	}

	public void setRdaPhone(String rdaPhone) {
		this.rdaPhone = rdaPhone;
	}

	public String getRdaRegId() {
		return rdaRegId;
	}

	public void setRdaRegId(String rdaRegId) {
		this.rdaRegId = rdaRegId;
	}

	public String getRdaStatus() {
		return rdaStatus;
	}

	public void setRdaStatus(String rdaStatus) {
		this.rdaStatus = rdaStatus;
	}

	public int getRdid() {
		return rdid;
	}

	public void setRdid(int rdid) {
		this.rdid = rdid;
	}

	public String getRdFirstName() {
		return rdFirstName;
	}

	public void setRdFirstName(String rdFirstName) {
		this.rdFirstName = rdFirstName;
	}

	public String getRdLastName() {
		return rdLastName;
	}

	public void setRdLastName(String rdLastName) {
		this.rdLastName = rdLastName;
	}

	public String getRdLogin() {
		return rdLogin;
	}

	public void setRdLogin(String rdLogin) {
		this.rdLogin = rdLogin;
	}

	public String getRmVehiclesRvId() {
		return rmVehiclesRvId;
	}

	public void setRmVehiclesRvId(String rmVehiclesRvId) {
		this.rmVehiclesRvId = rmVehiclesRvId;
	}

	public String getRdsStatDescAdd() {
		return rdsStatDescAdd;
	}

	public void setRdsStatDescAdd(String rdsStatDescAdd) {
		this.rdsStatDescAdd = rdsStatDescAdd;
	}

	public int getRdaStatusId() {
		return rdaStatusId;
	}

	public void setRdaStatusId(int rdaStatusId) {
		this.rdaStatusId = rdaStatusId;
	}

	public int getRdsStatusId() {
		return rdsStatusId;
	}

	public void setRdsStatusId(int rdsStatusId) {
		this.rdsStatusId = rdsStatusId;
	}

	public int getRvId() {
		return rvId;
	}

	public void setRvId(int rvId) {
		this.rvId = rvId;
	}
}
