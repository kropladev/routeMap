package dev.kropla.dto;

public class VehicleObjects {
	private int id;
	private String name;
	private String statusDescription;
	private String statusDescAdd;
	private String statusColor;
	
	public int getId() {
		return id;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getStatusDescAdd() {
		return statusDescAdd;
	}
	public void setStatusDescAdd(String statusDescAdd) {
		this.statusDescAdd = statusDescAdd;
	}
	public String getStatusColor() {
		return statusColor;
	}
	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
