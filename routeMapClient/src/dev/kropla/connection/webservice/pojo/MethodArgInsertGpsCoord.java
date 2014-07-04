package dev.kropla.connection.webservice.pojo;

public class MethodArgInsertGpsCoord {
	public static final String FIELDNAME_LAT = "lat";
	public static final String FIELDNAME_LON = "lon";
	public static final String FIELDNAME_VEHICLENAME = "userLogin";
	public static final String FIELDNAME_VEHICLE_ID = "vehicleId";
	public static final String FIELDNAME_VEHICLE_STATUS = "vehicleStatus";
	public static final String FIELDNAME_UPDATE_ROUTES_POSITION = "updateRoutesPosition";
	public static final String FIELDNAME_ROUTE_ID="routeId";
	
	private Double lon;
	private Double lat;
	private int vehicleId;
	private int statusId;
	private String vehicleName;
	private boolean updateRoutesPosition;
	private int routeId;
	
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public boolean isUpdateRoutesPosition() {
		return updateRoutesPosition;
	}
	public void setUpdateRoutesPosition(boolean updateRoutesPosition) {
		this.updateRoutesPosition = updateRoutesPosition;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

}
