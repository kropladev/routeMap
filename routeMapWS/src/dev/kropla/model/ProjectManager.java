package dev.kropla.model;

import java.sql.Connection;
import java.util.ArrayList;
import dev.kropla.dao.Project;
import dev.kropla.dto.DicStatuses;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.dto.OrdersObject;
import dev.kropla.dto.UserObject;
import dev.kropla.dto.VehiclePositionObjects;

public class ProjectManager {
	
	public void InsertVehiclePositionWithStatus(Connection connection,
			VehiclePositionObjects vehicleData) {
		try {
			Project project = new Project();
			project.insertVehiclePositionData(connection,vehicleData);
			
		} catch (Exception e) {
		 System.out.println("insert error");
		}
	}

	public ArrayList<VehiclePositionObjects> GetAllVehiclePositions(
			Connection connection) {
		 ArrayList<VehiclePositionObjects> vehiclesPositions = null;
			try {
				//log.debug("<ProjectManager> object START");
				// Here you can validate before connecting DAO class, eg. User
				// session condition
				Project project = new Project();
				vehiclesPositions = project.GetAllVehiclesPositions(connection);
				//log.debug("<ProjectManager> object FINISH");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		return vehiclesPositions;
	}

	public ArrayList<OrdersObject> getAllOrders(Connection connection, String lastListOrdersStatus)throws Exception {
		ArrayList<OrdersObject> ordersData = null;
		try {
			Project project = new Project();
			ordersData = project.getOrders(connection,lastListOrdersStatus);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ordersData;
	}

	public boolean getDriverAccount(Connection connection, String userLogin) {
		boolean userIsInBase=false;
		try {
			Project project = new Project();
			userIsInBase = project.driverAccountExists(connection,userLogin);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return userIsInBase;
	}
	
	public int getDriverAccountId(Connection connection, String userLogin) {
		int userId=0;
		try {
			Project project = new Project();
			userId = project.driverAccountExistsId(connection,userLogin);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return userId;
	}

	public int registerUserAccount(Connection connection,
			UserObject newAccount) {
		int userId=0;
		try {
			Project project = new Project();
			userId = project.registerNewAccount(connection,newAccount);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
		return userId;
	}

	public String changeVehicleStatus(Connection connection,
			String userAccountLogin, int status) {
		String result="";
		try {
			Project project = new Project();
			result=project.changeVehicleStatus(connection,userAccountLogin,status);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	public ArrayList<OrdersObject> getAllOrdersForUser(
			Connection connection, String userLogin, String statusSymbol) {
		ArrayList<OrdersObject> ordersData = null;
		try {
			Project project = new Project();
			ordersData = project.getOrders(connection,userLogin,statusSymbol);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ordersData;
	}

	public void InsertVehiclePosition(Connection connection,
			VehiclePositionObjects vehicleData) {
try {
			Project project = new Project();
			project.insertVehiclePositionData2(connection,vehicleData);	
		} catch (Exception e) {
		 System.out.println("insert error");
		}
	}
	
	public void InsertVehiclePositionRoute(Connection connection,
			VehiclePositionObjects vehicleData, int routeId) {
		try {
			Project project = new Project();
			project.insertVehiclePositionDataRoute(connection,vehicleData, routeId);	
		} catch (Exception e) {
		 System.out.println("insert error");
		}
	}
	
	public int insertNewRoute(Connection connection, int orderId ) {
		try {
			Project project = new Project();
			return project.insertNewRoute(connection, orderId);	
		} catch (Exception e) {
		 System.out.println("insert error");
		}
		return 0;
	}
	
	public String updateRoute(Connection connection, int orderIdInt,
			int routeIdInt, String status, double length) {
		try {
			Project project = new Project();
			return project.updateRoute(connection, orderIdInt,routeIdInt,length,status);
		} catch (Exception e) {
			System.out.println("insert error");
		}
		return "ERROR";
	}
	
	public String changeOrderStatus(Connection connection, int orderIdInt,
			int statusId) {
		try {
			Project project = new Project();
			return project.changeOrderStatus(connection, orderIdInt,statusId);
		} catch (Exception e) {
			System.out.println("insert error");
		}
		return "ERROR";
	}
	
	public OrdersAndroidObject orderDataAndroid(Connection connection,
			OrdersAndroidObject orderObj) {
		Project project = new Project();
		try {
			return project.orderDataAndroid(connection, orderObj);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public ArrayList<DicStatuses> getDicStatuses(Connection connection,String statusType) throws Exception {
		 ArrayList<DicStatuses> dicStatuses = null;
			try {
				Project project = new Project();
				dicStatuses = project.getDicStatuses(connection, statusType);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		return dicStatuses;
	}

	public ArrayList<OrdersObject> GetAllOrdersByDateRange(Connection connection, String lastListOrdersStatus,String lastListOrdersPeriod) throws Exception {
		ArrayList<OrdersObject> ordersData = null;
		try {
			Project project = new Project();
			ordersData = project.GetOrdersByDateRange(connection,lastListOrdersStatus, lastListOrdersPeriod);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ordersData;
	}
	
	public String changeOrdersData(Connection connection, OrdersObject order) throws Exception {
		//ArrayList<OrdersObject> ordersData = null;
		try {
			Project project = new Project();
			return project.changeOrdersData(connection,order);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "ERROR";
	}

	public String removeOrder(Connection connection, int orderId) {
		try {
			Project project = new Project();
			return project.removeOrder(connection,orderId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "ERROR";
	}

	public ArrayList<UserObject> getListOfDrivers(Connection connection) {
		try {
			Project project = new Project();
			return project.getListOfDrivers(connection);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public String updateDriver(Connection connection, UserObject driver) {
		try {
			Project project = new Project();
			return project.updateDriver(connection,driver);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public String removeDriver(Connection connection, int vehicleId) {
		try {
			Project project = new Project();
			return project.removeDriver(connection,vehicleId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "ERROR";
	}
	
}