package dev.kropla.controllers;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class OrderFormController {

	private String orderAddressFrom;
	private String orderName;
	private String orderType;
	private String orderAddressTo;
	private String driverName;
	private int driverId;
	@Size (min=5, max=12, message="Wrong size of value(min=5 max=12 chars)")
	@Pattern (regexp="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message="Wrong time format")
	private String orderTimeStart;
	private String orderCustName;
	@Size (min=5, max=12, message="Wrong size of value(min=5 max=12 chars)")
	@Pattern (regexp="[0-9 -+\t]", message="Wrong phone format")
	private String orderPhone;
	private String orderCustFirstName;
	private String orderCustLastName;
	private String longitudeS;
	private String latitudeS;
	
	
	public String getLongitudeS() {
		return longitudeS;
	}
	public void setLongitudeS(String longitudeS) {
		this.longitudeS = longitudeS;
	}
	public String getLatitudeS() {
		return latitudeS;
	}
	public void setLatitudeS(String latitudeS) {
		this.latitudeS = latitudeS;
	}
	public String getOrderAddressFrom() {
		return orderAddressFrom;
	}
	public void setOrderAddressFrom(String orderAddressFrom) {
		this.orderAddressFrom = orderAddressFrom;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderAddressTo() {
		return orderAddressTo;
	}
	public void setOrderAddressTo(String orderAddressTo) {
		this.orderAddressTo = orderAddressTo;
	}
	public String getOrderTimeStart() {
		return orderTimeStart;
	}
	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}
	public String getOrderCustName() {
		return orderCustName;
	}
	public void setOrderCustName(String orderCustName) {
		this.orderCustName = orderCustName;
	}
	public String getOrderPhone() {
		return orderPhone;
	}
	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}
	public String getOrderCustFirstName() {
		return orderCustFirstName;
	}
	public void setOrderCustFirstName(String orderCustFirstName) {
		this.orderCustFirstName = orderCustFirstName;
	}
	public String getOrderCustLastName() {
		return orderCustLastName;
	}
	public void setOrderCustLastName(String orderCustLastName) {
		this.orderCustLastName = orderCustLastName;
	}


	@Override
	public String toString() {
		return "OrderFormController [orderAddressFrom=" + orderAddressFrom
				+ ", orderName=" + orderName + ", orderType=" + orderType
				+ ", orderAddressTo=" + orderAddressTo + ", driverName="
				+ driverName + ", driverId=" + driverId + ", orderTimeStart="
				+ orderTimeStart + ", orderCustName=" + orderCustName
				+ ", orderPhone=" + orderPhone + ", orderCustFirstName="
				+ orderCustFirstName + ", orderCustLastName="
				+ orderCustLastName + ", longitudeS=" + longitudeS
				+ ", latitudeS=" + latitudeS + "]";
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
}
