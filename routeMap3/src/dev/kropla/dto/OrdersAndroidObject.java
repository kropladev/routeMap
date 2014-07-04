package dev.kropla.dto;

public class OrdersAndroidObject {
	private int orderId;
	private String orderName;
	private String custName;
	private String custPhone;
	private String custFirstName;
	private String custLastName;
	private String orderStreet_from;
	private String orderStreet_goal;
	private String timeOrderStart;
	private String rtName;//order type
	private int rtId;//order type
	private int custId;
	private String orderStatusDesc;
	private int orderStatusId;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getCustFirstName() {
		return custFirstName;
	}
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	
	public String getRtName() {
		return rtName;
	}
	public void setRtName(String rtName) {
		this.rtName = rtName;
	}
	public int getRtId() {
		return rtId;
	}
	public void setRtId(int rtId) {
		this.rtId = rtId;
	}
	@Override
	public String toString() {
		return "OrdersAndroidObject [orderId=" + orderId + ", orderName="
				+ orderName + ", custName=" + custName + ", custPhone="
				+ custPhone + ", custFirstName=" + custFirstName
				+ ", custLastName=" + custLastName + ", orderStreet_from="
				+ orderStreet_from + ", orderStreet_goal=" + orderStreet_goal
				+ ", timeOrderStart=" + timeOrderStart + ", rtName=" + rtName
				+ ", rtId=" + rtId + ", custId=" + custId
				+ ", orderStatusDesc=" + orderStatusDesc + ", orderStatusId="
				+ orderStatusId + "]";
	}
	public String getOrderStreet_from() {
		return orderStreet_from;
	}
	public void setOrderStreet_from(String orderStreet_from) {
		this.orderStreet_from = orderStreet_from;
	}
	public String getOrderStreet_goal() {
		return orderStreet_goal;
	}
	public void setOrderStreet_goal(String orderStreet_goal) {
		this.orderStreet_goal = orderStreet_goal;
	}
	public String getTimeOrderStart() {
		return timeOrderStart;
	}
	public void setTimeOrderStart(String timeOrderStart) {
		this.timeOrderStart = timeOrderStart;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public int getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
}
