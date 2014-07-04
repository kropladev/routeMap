package dev.kropla.model;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import dev.kropla.controllers.OrderFormController;
import dev.kropla.controllers.UserAccount;
import dev.kropla.dao.Project;
import dev.kropla.dto.DicStatuses;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.dto.OrdersObject;
import dev.kropla.dto.ShortStats;
import dev.kropla.dto.VehicleDetailsObject;
import dev.kropla.dto.VehicleObjects;
import dev.kropla.dto.VehiclePositionObjects;

public class ProjectManager {
	public static final Logger log = (Logger) LoggerFactory.getLogger("PROJECT_DB");
	public ArrayList<VehicleObjects> GetVehicles(Connection connection)throws Exception {
		ArrayList<VehicleObjects> vehicles = null;
		try {
			Project project = new Project();
			vehicles = project.GetVehicles(connection);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return vehicles;
	}

	public VehicleDetailsObject GetVehicleDetailInfo(
			Connection connection, int vehicleId) throws Exception {
		VehicleDetailsObject vehicle = null;
		try {
			Project project = new Project();
			vehicle = project.GetVehicleDetailInfo(connection, vehicleId);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return vehicle;
	}

	public  ArrayList<VehiclePositionObjects> GetAllVehiclePositions(
			Connection connection) throws Exception {
		 ArrayList<VehiclePositionObjects> vehiclesPositions = null;
		try {
			Project project = new Project();
			vehiclesPositions = project.GetAllVehiclesPositions(connection);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return vehiclesPositions;
	}

	/**
	 * deprecated - zla wartosc zwracanego charset
	 * @param connection
	 * @param statusType
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DicStatuses> getDicStatuses(Connection connection,String statusType) throws Exception {
		 ArrayList<DicStatuses> dicStatuses = null;
			try {
				Project project = new Project();
				dicStatuses = project.getDicStatuses(connection, statusType);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		return dicStatuses;
	}

	public int insertNewOrder(Connection connection, OrderFormController orderForm) throws Exception {	
		Project project = new Project();
		try {
			return project.InsertOrder(connection, orderForm);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return 0;
	}

	public ArrayList<VehiclePositionObjects> GetVehiclePositions(
			Connection connection, String status, String position) throws Exception {
		ArrayList<VehiclePositionObjects> vehiclesPositions = null;
		try {
			Project project = new Project();
			vehiclesPositions = project.GetVehiclesPositions(connection, status,position);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return vehiclesPositions;
	}

	public String insertNewUserAccount(Connection connection,
			UserAccount account) {
		Project project = new Project();
		try {
			return project.insertNewUserAccount(connection, account);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	public ArrayList<OrdersObject> GetAllOrders(Connection connection, String lastListOrdersStatus)throws Exception {
		ArrayList<OrdersObject> ordersData = null;
		try {
			Project project = new Project();
			ordersData = project.GetOrders(connection,lastListOrdersStatus);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return ordersData;
	}
	
	public ArrayList<OrdersObject> GetAllOrdersByDateRange(Connection connection, String lastListOrdersStatus,String lastListOrdersPeriod)throws Exception {
		ArrayList<OrdersObject> ordersData = null;
		try {
			Project project = new Project();
			ordersData = project.GetOrdersByDateRange(connection,lastListOrdersStatus, lastListOrdersPeriod);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return ordersData;
	}

	public String registerGCM(Connection connection, UserAccount account) {
		Project project = new Project();
		try {
			return project.insertRegId(connection, account);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	public UserAccount userAccountInfo(Connection connection,
			int accountId) {
		Project project = new Project();
		try {
			return project.userAccountInfo(connection, accountId);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	public OrdersAndroidObject orderDataAndroid(Connection connection,
			OrdersAndroidObject orderObj) {
		Project project = new Project();
		try {
			return project.orderDataAndroid(connection, orderObj);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	public String confirmOrder(Connection connection, int orderId,
			int vehicleId, int status) {
		Project project = new Project();
		try {
			return project.confirmOrder(connection, orderId,vehicleId, status);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	public String getRegIdForUserByVehicleId(Connection connection,
			int vehicleId) {
		Project project = new Project();
		try {
			return project.getRegIdForUserByVehicleId(connection, vehicleId);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
		
	}

	public ShortStats countShortStats(Connection connection) {
		Project project = new Project();
		try {
			log.debug("ProjectManager.countShortStats start");
			return  project.countShortStats(connection);
	//		log.debug("ProjectManager.countShortStats end");
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}
}