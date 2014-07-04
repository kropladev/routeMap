package dev.kropla.connection.webservice.pojo;

public class MethodArgOrder {
	public static final String FIELDNAME_ORDER_ID = "orderId";
	public static final String FIELDNAME_STATUS_ID = "statusId";
	private String orderId;
	private int statusId;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
