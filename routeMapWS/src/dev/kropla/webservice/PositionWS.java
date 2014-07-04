package dev.kropla.webservice;

import java.sql.Connection;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.google.gson.Gson;
import dev.kropla.dao.Database;
import dev.kropla.dto.DicStatuses;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.dto.OrdersObject;
import dev.kropla.dto.UserObject;
import dev.kropla.dto.VehiclePositionObjects;
import dev.kropla.model.ProjectManager;
import dev.kropla.tools.Configs;

@WebService(targetNamespace="http://kropladev.pl")
public class PositionWS {
	private String lastListOrdersStatus="active";
	private String lastListOrdersPeriod="lastWeek";	
	
	/**
	 * used  by ANDROID 
	 * @param userLogin
	 * @param lon
	 * @param lat
	 * @param updateRoutesPosition
	 * @param routeId
	 * @return
	 */
	@WebMethod
	public String insertGPSCoord2(String userLogin, String lon, String lat, String updateRoutesPosition, String routeId){
		int routeIdInt=Integer.parseInt(routeId);
		//System.out.println("WEBSERVICE insertGPSCoord2 STARTED!!!");
		String status="false";
		Database database = new Database();
		VehiclePositionObjects vehicleData=new VehiclePositionObjects();
		
		try{
		vehicleData.setVehicleName(userLogin);
		vehicleData.setLatitude(Double.parseDouble(lat));
		vehicleData.setLongitude(Double.parseDouble(lon));
		}catch(Exception e){
			e.printStackTrace();
		}
		ProjectManager projectManager = new ProjectManager();
		try {
			Connection connection = database.getConnection();
			projectManager.InsertVehiclePosition(connection,vehicleData);
			if(updateRoutesPosition.equals("true")){
				projectManager.InsertVehiclePositionRoute(connection,vehicleData,routeIdInt);
			}
			status="true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;	
	}
	
	@WebMethod
	public String insertNewRoute(String orderId){
		int orderIdInt=Integer.parseInt(orderId);
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		try {
			Connection connection = database.getConnection();
			return Integer.toString(projectManager.insertNewRoute(connection,orderIdInt));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";	
	}
	
	@WebMethod
	public String updateRoute(String orderId, String routeId, String status, String length){
		int orderIdInt=Integer.parseInt(orderId);
		int routeIdInt=Integer.parseInt(routeId);
		double lengthD = Double.parseDouble(length);
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		try {
			Connection connection = database.getConnection();
			return projectManager.updateRoute(connection,orderIdInt,routeIdInt,status,lengthD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OK";	
	}
	
	@WebMethod
	public String changeOrderStatus(String orderId, String statusId){
		int orderIdInt=Integer.parseInt(orderId);
		int statusIdInt=Integer.parseInt(statusId);
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		try {
			Connection connection = database.getConnection();
			return projectManager.changeOrderStatus(connection,orderIdInt,statusIdInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR";	
	}
	
	
	@WebMethod
	public String getVehiclesPositions(){
		System.out.println("WEBSERVICE STARTED!!!");

		String vehiclePositions;
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<VehiclePositionObjects> vehiclePositionData = null;
			vehiclePositionData = projectManager.GetAllVehiclePositions(database.getConnection());
			Gson gson = new Gson();
			vehiclePositions = gson.toJson(vehiclePositionData);
			vehiclePositions="{\"markers\":".concat(vehiclePositions).concat("}");
		} catch (Exception ex) {
			vehiclePositions="DB error";
		}
		return vehiclePositions;
	}
	
	@WebMethod
	public String getLastOrders(String statusSymbol){
		System.out.println("WEBSERVICE STARTED!!!");
		//String status="false";
	
		String ordersList;
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<OrdersObject> ordersData = null;
			ordersData = projectManager.getAllOrders(database.getConnection(),statusSymbol);
			Gson gson = new Gson();
			ordersList = gson.toJson(ordersData);
			ordersList="{\"orders\":".concat(ordersList).concat("}");
		} catch (Exception ex) {
			ordersList="DB error";
		}
		return ordersList;
	}

	@WebMethod
	public String getLastOrdersForUser(String userLogin,String statusSymbol){
		System.out.println("WEBSERVICE STARTED!!!");
		//String status="false";
	
		String ordersList;
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<OrdersObject> ordersData = null;
			ordersData = projectManager.getAllOrdersForUser(database.getConnection(),userLogin,statusSymbol);
			Gson gson = new Gson();
			ordersList = gson.toJson(ordersData);
			ordersList="{\"orders\":".concat(ordersList).concat("}");
			System.out.println("getLastOrdersForUser: "+ordersList);
		} catch (Exception ex) {
			ordersList="DB error";
		}
		return ordersList;
	}
		
	@WebMethod
	public void changeVehicleConfigStatus(String statusSymbol){
		Configs.filterStatusOption=statusSymbol;
	}
	
	@WebMethod
	public String logIntoApplication(String userLogin){
		//check if user account is in base
		boolean userIsInBase=false;
		String returnMessage="";
		
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			userIsInBase = projectManager.getDriverAccount(database.getConnection(),userLogin);

		} catch (Exception ex) {
			userIsInBase=false;
		}
		if (userIsInBase){
			returnMessage="OK";
		}else if (!userIsInBase){
			returnMessage="USER_NOT_IN_DB";
		}else {
			returnMessage="ERROR";
		}
		return returnMessage;
	}
	
	@WebMethod
	public String logIntoApplicationId(String userLogin){
		//check if user account is in base
		int userId=0;
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			userId = projectManager.getDriverAccountId(database.getConnection(),userLogin);

		} catch (Exception ex) {
			userId=-1;
		}
		return Integer.toString(userId);
	}
	
	/**
	 * used  by ANDROID 
	 * @param userAccountName
	 * @param userMailName
	 * @param userPhone
	 * @param apiRegKey
	 * @param userFName
	 * @param userLName
	 * @return
	 */
	@WebMethod
	public String registerNewAccount(String userAccountName, String userMailName, String userPhone, String apiRegKey, String userFName, String userLName){
		String result="";
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			UserObject newAccount=new UserObject();
			int userId=0;
			
			newAccount.setRdaMail(userMailName);
			newAccount.setRdaPhone(userPhone);
			newAccount.setRdaRegId(apiRegKey);
			newAccount.setRdaStatus("register");
			newAccount.setRdFirstName(userFName);
			newAccount.setRdLastName(userLName);
			newAccount.setRdLogin(userAccountName);
			
			
			userId = projectManager.registerUserAccount(database.getConnection(),newAccount);
			result=Integer.toString(userId);
		} catch (Exception ex) {
			ex.getMessage();
		}
		
		return result;
	}
	
	@WebMethod
	public String changeVehicleStatus(String userAccountLogin, String status){
		String result="";
		try{
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		result=projectManager.changeVehicleStatus(database.getConnection(),userAccountLogin, Integer.parseInt(status));
		} catch (Exception ex) {
			ex.getMessage();
		}
		
		return result;
	}
	
	@WebMethod
	public String getOrderDataById(String orderId){
		Gson gson = new Gson();
		//OrdersAndroidObject = gson.fromJson(userAccStr, UserAccount.class);
		OrdersAndroidObject orderObj= new OrdersAndroidObject();
		orderObj.setOrderId(Integer.parseInt(orderId));
		try{
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		orderObj=projectManager.orderDataAndroid(database.getConnection(), orderObj);
		} catch (Exception ex) {
			ex.getMessage();
		}
		return gson.toJson(orderObj);
	}
	
	/**
	 * deprecated
	 * used  by ANDROID 
	 * @param vehicleId
	 * @param vehicleStatus
	 * @param lon
	 * @param lat
	 * @return
	 */
/*	@WebMethod
	public String insertGPSCoord(int vehicleId, int vehicleStatus, String lon, String lat){
		System.out.println("WEBSERVICE STARTED!!!");
		String status="false";
		Database database = new Database();
		VehiclePositionObjects vehicleData=new VehiclePositionObjects();
		vehicleData.setVehicleId(vehicleId);
		vehicleData.setLatitude(Double.parseDouble(lat));
		vehicleData.setLongitude(Double.parseDouble(lon));
		vehicleData.setStatusId(vehicleStatus);
		System.out.println("WEBSERVICE 2");
		System.out.println("WS: vehicleId="+vehicleData.getVehicleId());
		System.out.println("WS: vehicleLat="+vehicleData.getLatitude());
		System.out.println("WS: vehicleLon="+vehicleData.getLongitude());
		System.out.println("WS: vehicleStatus="+vehicleData.getStatusId());
		ProjectManager projectManager = new ProjectManager();
		try {
			Connection connection = database.Get_Connection();
			projectManager.InsertVehiclePositionWithStatus(connection,vehicleData);
			status="true";
			System.out.println("WS: Connection OK");
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("WS: END");
		return status;	
	}*/
	@WebMethod
	public String getDicStatuses(String statusType){
		String dicStatuses;
		
		try{
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<DicStatuses> vehiclesData = null;
			vehiclesData = projectManager.getDicStatuses(database.getConnection(), statusType);
			
			Gson gson = new Gson();			
			dicStatuses = gson.toJson(vehiclesData);
			dicStatuses="{\"StatusList\":".concat(dicStatuses).concat("}");
		} catch (Exception ex) {
			dicStatuses="DB error";
			ex.printStackTrace();
		}
		return dicStatuses;
	}
	
	@WebMethod
	public String getListOfOrders( String status, String periodArg){
		String objectList;
		if (status!=null){this.lastListOrdersStatus=status;}
		if(periodArg!=null){this.lastListOrdersPeriod=periodArg;}
		try{
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<OrdersObject> objectsData = null;
			objectsData = projectManager.GetAllOrdersByDateRange(database.getConnection(),this.lastListOrdersStatus,this.lastListOrdersPeriod);
			Gson gson = new Gson();
			System.out.println(gson.toJson(objectsData));
			//logger.debug(gson.toJson(vehiclesData));
			objectList = gson.toJson(objectsData);
			objectList="{\"Orders\":".concat(objectList).concat("}");
		} catch (Exception ex) {
			objectList="DB error";
			ex.printStackTrace();
		}
		return objectList;
	}
	
	@WebMethod
	public String changeOrdersData( int orderId, String roaTimeOrderStart,String orderName,String custStreet_from,String driverName,int orderStatusId){
		OrdersObject order = new OrdersObject();
		order.setOrderId(orderId);
		order.setRoaTimeOrderStart(roaTimeOrderStart);
		order.setOrderName(orderName);
		order.setCustStreet_from(custStreet_from);
		order.setDriverName(driverName);
		System.out.println("PositionWS.changeOrdersData driverName"+driverName);
		order.setOrderStatusId(orderStatusId);
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		
		try {
			return projectManager.changeOrdersData(database.getConnection(), order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
	}
	
	@WebMethod
	public String removeOrder( int orderId){
		//OrdersObject order = new OrdersObject();
		//order.setOrderId(orderId);
		System.out.println(" ===> removing data:"+orderId);
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		try {
			return projectManager.removeOrder(database.getConnection(), orderId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
	}
	
	
	@WebMethod
	public String removeDriver( int vehicleId){
		//OrdersObject order = new OrdersObject();
		//order.setOrderId(orderId);
		System.out.println(" ===> removing data:"+vehicleId);
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		try {
			return projectManager.removeDriver(database.getConnection(), vehicleId);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "ERROR";
	}
	
	@WebMethod
	public String getListOfDrivers(){
		String  objectList="DbERROR";
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		try {
			ArrayList<UserObject> objectsData = null;
			objectsData= projectManager.getListOfDrivers(database.getConnection());
			Gson gson = new Gson();
			System.out.println(gson.toJson(objectsData));
			//logger.debug(gson.toJson(vehiclesData));
			objectList = gson.toJson(objectsData);
			objectList="{\"Drivers\":".concat(objectList).concat("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectList;
	}
	@WebMethod
	public String updateDriver(int vehicleId,int rdaId, String firstName, String lastName, String login, String mail, String phone, int accStatusId ){
		String  status="DbERROR";
		System.out.println("Runing PositionWS.updateDriver with params:: vehicleId:"+vehicleId
				+"|rdaId:"+rdaId+"|firstName:"+ firstName+"|lastName:"+ lastName+"|login:"+ login
				+"|mail:"+ mail+"|phone:"+ phone+"|accStatusId:"+ accStatusId);
		UserObject driver = new UserObject();
		Database database = new Database();
		ProjectManager projectManager = new ProjectManager();
		try {
			driver.setRdaId(rdaId);
			driver.setRdaMail(mail);
			driver.setRdaPhone(phone);
			//driver.setRdaStatus(accStatus);
			driver.setRdaStatusId(accStatusId);
			driver.setRdFirstName(firstName);
			driver.setRdLastName(lastName);
			//driver.setRdid(rdId);
			driver.setRdLogin(login);
			driver.setRvId(vehicleId);	
			status=projectManager.updateDriver(database.getConnection(),driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
