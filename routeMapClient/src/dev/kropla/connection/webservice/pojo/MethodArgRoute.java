package dev.kropla.connection.webservice.pojo;

public class MethodArgRoute {
	public static final String FIELDNAME_ORDERID = "orderId";
	public static final String FIELDNAME_ROUTEID = "routeId";
	public static final String FIELDNAME_STATUS = "status";
	public static final String FIELDNAME_LENGTH = "length";
	
	private int orderId;
	private int routeId;
	private String status;
	private double length;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}	
	
	
	
}
