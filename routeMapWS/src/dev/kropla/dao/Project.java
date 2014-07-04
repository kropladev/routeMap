package dev.kropla.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dev.kropla.dto.DicStatuses;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.dto.OrdersObject;
import dev.kropla.dto.UserObject;
import dev.kropla.dto.VehiclePositionObjects;
import dev.kropla.tools.Configs;

public class Project {
	public void insertVehiclePositionData(Connection connection,
			VehiclePositionObjects vehicleData) {
		int newPkId=-1;
		PreparedStatement ps=null;
		try {
		ps = connection.prepareStatement("INSERT INTO rm_positions(rp_latitude,rp_longitude) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
		ps.setDouble(1, vehicleData.getLatitude());
		ps.setDouble(2, vehicleData.getLongitude());		
		//ps.setString(1, message);
		int retRows = ps.executeUpdate();

		if (retRows > 0) {
			ResultSet rs=ps.getGeneratedKeys();
			//int autoIncValue = -1;

			if(rs.next()) 
			{
				newPkId = rs.getInt(1);
				ps = connection.prepareStatement("UPDATE rm_vehicles set rv_last_position=?, rv_last_status=? WHERE rv_id=?");
				ps.setInt(1, newPkId);
				ps.setInt(2, vehicleData.getStatusId());
				ps.setInt(3, vehicleData.getVehicleId());
				ps.executeUpdate();
			       /*You can get more generated keys if they are generated in your code*/
			}
			
		} else {
			System.out.println("No Insert performed");
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void insertVehiclePositionData2(Connection connection,
			VehiclePositionObjects vehicleData) {
		int newPkId=-1;
		PreparedStatement ps=null;
		try {
		ps = connection.prepareStatement("INSERT INTO rm_positions(rp_latitude,rp_longitude) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
		ps.setDouble(1, vehicleData.getLatitude());
		ps.setDouble(2, vehicleData.getLongitude());		
		//ps.setString(1, message);
		int retRows;	
		retRows = ps.executeUpdate();
		if (retRows > 0) {
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next()) 
			{
				newPkId = rs.getInt(1);
				ps = connection.prepareStatement("UPDATE rm_vehicles set rv_last_position=? WHERE rv_name=?");
				ps.setInt(1, newPkId);
				ps.setString(2, vehicleData.getVehicleName());
				ps.executeUpdate();
			       /*You can get more generated keys if they are generated in your code*/
			}	
		} else {
			System.out.println("No Insert performed");
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
	}

	public void insertVehiclePositionDataRoute(Connection connection,
			VehiclePositionObjects vehicleData, int routeId) {
		PreparedStatement ps=null;
		try {
			ps = connection.prepareStatement("INSERT INTO rm_route_positions(rr_lat,rr_lon,rr_timestamp,rm_routes_rr_id) VALUES(?,?,CURRENT_TIMESTAMP,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, vehicleData.getLatitude());
			ps.setDouble(2, vehicleData.getLongitude());
			ps.setInt(3, routeId);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	public int insertNewRoute(Connection connection, int orderId) {
		int newPkId=0;
		PreparedStatement ps=null;
		try {
			System.out.println("routeMapWS.Project.insertNewRoute orderId:"+orderId);
			 ps = connection.prepareStatement("INSERT INTO rm_routes(rr_status,rr_start_time,rr_length) VALUES(?,CURRENT_TIMESTAMP,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, "start");
			ps.setDouble(2, 0);
			int retRows;	
			retRows = ps.executeUpdate();
			if (retRows > 0) {
				ResultSet rs=ps.getGeneratedKeys();
				if(rs.next()) 
				{
					newPkId = rs.getInt(1);
					ps = connection.prepareStatement("UPDATE rm_orders SET rm_routes_rr_id=? WHERE ro_id=?");
					ps.setInt(1, newPkId);
					ps.setInt(2, orderId);
					ps.executeUpdate();
				}
			}else {
				System.out.println("No Insert performed");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return newPkId;
	}
	
	public String updateRoute(Connection connection, int orderIdInt,
			int routeIdInt,double length, String status) {
		PreparedStatement ps=null;
		try {
			System.out.println("routeMapWS.Project.updateRoute orderIdInt:"+orderIdInt);
			System.out.println("routeMapWS.Project.updateRoute routeIdInt:"+routeIdInt);
			System.out.println("routeMapWS.Project.updateRoute length:"+length);
			System.out.println("routeMapWS.Project.updateRoute status:"+status);
			ps =connection.prepareStatement("UPDATE rm_routes SET rr_status=?,rr_length=?,rr_finish_time=CURRENT_TIMESTAMP WHERE rr_id=?");
			ps.setString(1, status);
			ps.setDouble(2, length);
			ps.setInt(3, routeIdInt);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return "OK";
	}
	
	public String changeOrderStatus(Connection connection, int orderIdInt,
			int statusId) {
		PreparedStatement ps=null;
		try {
			System.out.println("routeMapWS.Project.changeOrderStatus orderIdInt:"+orderIdInt);
			System.out.println("routeMapWS.Project.changeOrderStatus statusId:"+statusId);
			ps =connection.prepareStatement("UPDATE rm_orders SET rm_status=? WHERE ro_id=?");
			ps.setInt(1, statusId);
			ps.setInt(2, orderIdInt);
			ps.executeUpdate();
			
			if(statusId == 9 ||statusId == 10){
				ps =connection.prepareStatement("UPDATE rm_orders_negoc SET ron_status=? WHERE ron_order_id=? AND ron_end_date IS NULL");
				ps.setInt(1, (statusId==9?3:2));
				ps.setInt(2, orderIdInt);
				ps.executeUpdate();
			}
			if(statusId == 9){
				ps =connection.prepareStatement("UPDATE rm_orders_add SET roa_time_finish=CURRENT_TIMESTAMP WHERE roa_id=(SELECT rm_orders_add_roa_id FROM rm_orders WHERE ro_id=?)");
				ps.setInt(1, orderIdInt);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return "OK";
	}
	
	public ArrayList<VehiclePositionObjects> GetAllVehiclesPositions(
			Connection connection) {
		PreparedStatement ps=null;
		 ArrayList<VehiclePositionObjects> vehicleData = new  ArrayList<VehiclePositionObjects>();
		try {
			if (Configs.filterStatusOption.isEmpty()){
				ps= connection.prepareStatement(DbQueries.S_VEHICLE_MARKERS_ALL);
			}else{
				String query=DbQueries.S_VEHICLE_MARKERS_ALL_FILTER2+Configs.filterStatusOption+")";
				System.out.println("Configs.filterStatusOption ="+Configs.filterStatusOption);
				ps= connection.prepareStatement(query);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VehiclePositionObjects vehiclePositionObject = new VehiclePositionObjects();
				vehiclePositionObject.setVehicleId(rs.getInt("rv_id"));
				vehiclePositionObject.setVehicleName(rs.getString("rv_name"));
				vehiclePositionObject.setLatitude(rs.getDouble("rp_latitude"));
				vehiclePositionObject.setLongitude(rs.getDouble("rp_longitude"));
				vehiclePositionObject.setStatusColor(rs.getString("rds_stat_color"));
				vehicleData.add(vehiclePositionObject);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return vehicleData;
	}
	
	public ArrayList<OrdersObject> getOrders(Connection connection, String lastListOrdersStatus) {
		ArrayList<OrdersObject> ordersData = new  ArrayList<OrdersObject>();
		PreparedStatement ps=null;
		try {
			//log.debug("<Project> object START");
			ps = connection.prepareStatement(DbQueries.S_ORDERS_DATA);
			ps.setString(1,lastListOrdersStatus );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//rds_stat_id,rds_stat_desc, rds_stat_desc_add,rds_stat_color
				OrdersObject order = new OrdersObject();
				order.setOrderId(rs.getInt("orderId"));
				order.setOrderName(rs.getString("orderName"));
				order.setCustName(rs.getString("custName"));
				order.setCustPhone(rs.getString("custPhone"));
				order.setCustFirstName(rs.getString("custFirstName"));
				order.setCustLastName(rs.getString("custLastName"));
				order.setCustStreet(rs.getString("custStreet"));
				order.setCustAddressAdd(rs.getString("custAddressAdd"));
				order.setCustApartment(rs.getString("custApartment"));
				order.setCustStreet_from(rs.getString("custStreet_from"));
				order.setCustAddressAdd_from(rs.getString("custAddressAdd_from"));
				order.setCustApartment_from(rs.getString("custApartment_from"));
				order.setCustStreet_to(rs.getString("custStreet_to"));
				order.setCustAddressAdd_to(rs.getString("custAddressAdd_to"));
				order.setCustApartment_to(rs.getString("custApartment_to"));
				order.setRoaTimeLate(rs.getString("roa_time_late"));
				order.setRoaTimeOrder(rs.getString("roa_time_order"));
				order.setRoaTimeOrderStart(rs.getString("roa_time_order_start"));
				order.setRoaTimeRealStart(rs.getString("roa_time_real_start"));
				order.setRoaTimeFinish(rs.getString("roa_time_finish"));
				order.setRtName(rs.getString("rt_name"));
				order.setDriverName(rs.getString("rv_name"));
				order.setStatus(rs.getString("rds_stat_desc"));

				
				ordersData.add(order);
			}
			//log.debug("<Project> object FINISH");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
	return ordersData;
	}

	public ArrayList<OrdersObject> getOrders(Connection connection,
			String userLogin, String statusSymbol) {
		PreparedStatement ps=null;
		ArrayList<OrdersObject> ordersData = new  ArrayList<OrdersObject>();
		try {
			//log.debug("<Project> object START");
			ps = connection.prepareStatement(DbQueries.S_ORDERS_DATA_USER);
			ps.setString(1,statusSymbol );
			ps.setString(2,userLogin );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//rds_stat_id,rds_stat_desc, rds_stat_desc_add,rds_stat_color
				OrdersObject order = new OrdersObject();
				order.setOrderId(rs.getInt("orderId"));
				order.setRoaTimeOrder(rs.getString("roa_time_order"));
				order.setCustName(rs.getString("custName"));
				order.setCustStreet_from(rs.getString("custStreet_from"));
				order.setRoaTimeOrderStart(rs.getString("roa_time_order_start"));
				order.setOrderName(rs.getString("orderName"));
				order.setStatus(rs.getString("rds_stat_desc"));
				order.setRtName(rs.getString("rt_name"));
				order.setOrderStatusId(rs.getInt("rds_id"));
				
				ordersData.add(order);
			}
			//log.debug("<Project> object FINISH");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
	return ordersData;
	}
	
	public boolean driverAccountExists(Connection connection, String userLogin) {
		int accountIdInTable=0;
		PreparedStatement ps=null;
		try {	
			ps= connection.prepareStatement(DbQueries.S_USER_ACCOUNT_EXISTS);
			ps.setString(1,userLogin );
			System.out.println("driverAccountExists: checking user:"+userLogin);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()){
				accountIdInTable=rs.getInt("rd_id");
				System.out.println("driverAccountExists: for user:"+userLogin+" accountIdInTable:"+ accountIdInTable);
			}
		
		} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(e);
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		
		if(accountIdInTable!=0){
			return true;
		}
		
		return false;
	}

	public int driverAccountExistsId(Connection connection, String userLogin) {
		int accountIdInTable=0;
		PreparedStatement ps=null;
		try {	
			ps= connection.prepareStatement(DbQueries.S_USER_ACCOUNT_EXISTS);
			ps.setString(1,userLogin );
			System.out.println("driverAccountExists: checking user:"+userLogin);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()){
				accountIdInTable=rs.getInt("rd_id");
				System.out.println("driverAccountExists: for user:"+userLogin+" accountIdInTable:"+ accountIdInTable);
			}
		
		} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(e);
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return accountIdInTable;
	}
	
	public int registerNewAccount(Connection connection, UserObject newAccount) {
		int userId=0;
		PreparedStatement ps=null;
		int retRows,newPkVehicle = 0, newPkDriver=0;
		ResultSet rs;
		
		try{
		//insert new vehicle
		ps =connection.prepareStatement(DbQueries.I_NEW_VEHICLE,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, newAccount.getRdLogin());
		ps.setInt(2, 4);
		retRows=ps.executeUpdate();
		
		if (retRows<=0){
			return 0;
		}else{
			rs=ps.getGeneratedKeys();
			if(rs.next()) 
			{
				newPkVehicle = rs.getInt(1);
			}
		}

		//Insert new driver
				ps =connection.prepareStatement(DbQueries.I_NEW_DRIVER,Statement.RETURN_GENERATED_KEYS);
				//rd_first_name,rd_last_name, rd_login, rm_vehicles_rv_id
				ps.setString(1, newAccount.getRdFirstName());
				ps.setString(2, newAccount.getRdLastName());
				ps.setString(3, newAccount.getRdLogin());
				ps.setInt(4, newPkVehicle);
				retRows=ps.executeUpdate();
				
				if (retRows<=0){
					return 0;
				}else{
					rs=ps.getGeneratedKeys();
					if(rs.next()) 
					{
						newPkDriver = rs.getInt(1);
					}
				}
				
				//Insert new driver account
				//message = request.getParameter("message");
				ps =connection.prepareStatement(DbQueries.I_NEW_DRIVER_ACCOUNT,Statement.RETURN_GENERATED_KEYS);
				//rda_mail,rda_phone, rda_reg_id, rm_drivers_rd_id, rda_status
				ps.setString(1, newAccount.getRdaMail());
				ps.setString(2, newAccount.getRdaPhone());
				//ps.setNull(3, java.sql.Types.NULL ); //address
				ps.setString(3, newAccount.getRdaRegId());
				ps.setInt(4, newPkDriver);
				ps.setString(5, newAccount.getRdaStatus());
				retRows=ps.executeUpdate();
				
				if (retRows<=0){
					return 0;
				}else{
					rs=ps.getGeneratedKeys();
					if(rs.next()) 
					{
						userId = rs.getInt(1);
					}
				}
				
				}catch(Exception ex){
					ex.getMessage();
					return 0;
				}finally {
					try{
						if(ps!=null){
							ps.close();
						}
						if(connection!=null){
							connection.close();
						}
					}catch(SQLException e){
						System.out.println(e.getMessage());
					}
				}
		
		return userId;
	}
	
	public String changeVehicleStatus(Connection connection,
			String userAccountLogin, int status) {
		String result="";
		PreparedStatement ps=null;
		System.out.println("Project.changeVehicleStatus userAccountLogin:"+userAccountLogin+"| status:"+status);
		try {
			String query=DbQueries.U_VEHICLE_STATUS;
			if(status==1){
				query=DbQueries.U_VEHICLE_STATUS2;
			}
			ps = connection.prepareStatement(query);
			ps.setInt(1,status );
			ps.setString(2,userAccountLogin );
			int retRows = ps.executeUpdate();
			if (retRows > 0) {
				result="OK";
			}
		} catch (SQLException e) {
			result=e.getMessage().toString();
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	public String changeOrdersData(Connection connection, OrdersObject order) {
		String result="";
		PreparedStatement ps=null;
		try {
			ps = connection.prepareStatement(DbQueries.U_ORDER_DATA);
			//ps.setString(1,order.getRoaTimeOrderStart());
			ps.setString(1, order.getCustStreet_from());

			ps.setInt(2, order.getOrderId());
			int retRows = ps.executeUpdate();
			if (retRows > 0) {
				result="OK";
			}
			
			ps = connection.prepareStatement(DbQueries.U_ORDER_DATA2);
			ps.setString(1,order.getRoaTimeOrderStart());
			ps.setInt(2, order.getOrderId());
			retRows = ps.executeUpdate();
			if (retRows > 0) {
				result="OK";
			}

			ps = connection.prepareStatement(DbQueries.U_ORDER_DATA3);
			ps.setString(1, order.getOrderName());
			ps.setInt(2, order.getOrderStatusId());
			ps.setInt(3, order.getOrderId());
			retRows = ps.executeUpdate();
			if (retRows > 0) {
				result="OK";
			}
			if(order.getDriverName()!=null && order.getDriverName().length()>0){
				ps = connection.prepareStatement(DbQueries.U_ORDER_DATA4);
				ps.setString(2, order.getDriverName());
				ps.setInt(1, order.getOrderId());
				retRows = ps.executeUpdate();
				if (retRows > 0) {
					result="OK";
				}
				
				ps = connection.prepareStatement(DbQueries.I_ORDER_DATA5);
				ps.setString(2, order.getDriverName());
				ps.setInt(1, order.getOrderId());
				retRows = ps.executeUpdate();
				if (retRows > 0) {
					result="OK";
				}
			}
			
		} catch (SQLException e) {
			result=e.getMessage().toString();
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	
	//************ANDROID
	
	public OrdersAndroidObject orderDataAndroid(Connection connection,
			OrdersAndroidObject orderObj) {
		PreparedStatement ps=null;
		try{
			ps = connection.prepareStatement(DbQueries.S_V_ORDERS_DATA_ANDROID);
			ps.setInt(1,orderObj.getOrderId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//log.debug("S_V_ORDERS_DATA_ANDROID:" +rs.getString("orderStreet_from"));
				System.out.println("S_V_ORDERS_DATA_ANDROID:" +rs.getString("orderStreet_from"));
				orderObj.setCustFirstName(rs.getString("custFirstName"));
				orderObj.setCustId(rs.getInt("custId"));
				orderObj.setCustLastName(rs.getString("custLastName"));
				orderObj.setCustName(rs.getString("custName"));
				orderObj.setCustPhone(rs.getString("custPhone"));
				orderObj.setOrderId(rs.getInt("orderId"));
				orderObj.setOrderName(rs.getString("orderName"));
				orderObj.setOrderStreet_from(rs.getString("orderStreet_from"));
				orderObj.setOrderStreet_goal(rs.getString("orderStreet_goal"));
				orderObj.setRtId(rs.getInt("orderTypeId"));
				orderObj.setRtName(rs.getString("orderTypeDesc"));
				orderObj.setTimeOrderStart(rs.getString("timeOrderStart"));
				orderObj.setOrderStatusDesc(rs.getString("orderStatusDesc"));
				orderObj.setOrderStatusId(rs.getInt("orderStatusId"));
			}
			return orderObj;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	public ArrayList<DicStatuses> getDicStatuses(Connection connection, String statusType) {
		 ArrayList<DicStatuses> dicStatuses = new  ArrayList<DicStatuses>();
		 PreparedStatement ps=null;
		 try {
				ps = connection.prepareStatement(DbQueries.S_STATUSES_ALL);
				ps.setString(1, statusType);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					DicStatuses dicStatus = new DicStatuses();
					dicStatus.setStatusId(rs.getString("rds_id"));
					dicStatus.setStatusColor(rs.getString("rds_stat_color"));
					dicStatus.setStatusDescAdd(rs.getString("rds_stat_desc_add"));
					dicStatus.setStatusDesc(rs.getString("rds_stat_desc"));
					dicStatuses.add(dicStatus);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				try{
					if(ps!=null){
						ps.close();
					}
					if(connection!=null){
						connection.close();
					}
				}catch(SQLException e){
					System.out.println(e.getMessage());
				}
			}
		return dicStatuses;
	}

	public ArrayList<OrdersObject> GetOrdersByDateRange(Connection connection,String lastListOrdersStatus, String lastListOrdersPeriod) {
		ArrayList<OrdersObject> ordersData = new  ArrayList<OrdersObject>();
		System.out.println(lastListOrdersStatus + "|"+lastListOrdersPeriod);
		PreparedStatement ps=null;
		try {
			String query= DbQueries.S_ORDERS_DATA;
			if(lastListOrdersPeriod!=null){
				if(lastListOrdersPeriod.contains("today")){query+=DbQueries.S_ORDERS_DATA_RANGE_BY_DATE_TODAY;}
				else if(lastListOrdersPeriod.contains("yesterday")){query+=DbQueries.S_ORDERS_DATA_RANGE_BY_DATE_YESTERDAY;}
				else if(lastListOrdersPeriod.contains("last3days")){query+=DbQueries.S_ORDERS_DATA_RANGE_BY_DATE_LAST3DAYS;}
				else if(lastListOrdersPeriod.contains("lastweek")){query+=DbQueries.S_ORDERS_DATA_RANGE_BY_DATE_LASTWEEK;}
				else if(lastListOrdersPeriod.contains("lastmonth")){query+=DbQueries.S_ORDERS_DATA_RANGE_BY_DATE_LASTMONTh;}
				else if(lastListOrdersPeriod.contains("nextweek")){query+=DbQueries.S_ORDERS_DATA_RANGE_BY_DATE_NEXTWEEK;}
				else if(lastListOrdersPeriod.contains("futureall")){query+=DbQueries.S_ORDERS_DATA_RANGE_BY_DATE_FUTUREALL;}
			}
			System.out.println(query);
			query+=DbQueries.S_ORDERS_DATA2;
			ps = connection.prepareStatement(query);
			ps.setString(1,lastListOrdersStatus );
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//rds_stat_id,rds_stat_desc, rds_stat_desc_add,rds_stat_color
				OrdersObject order = new OrdersObject();
				order.setOrderId(rs.getInt("orderId"));
				order.setOrderName(rs.getString("orderName"));
				order.setCustName(rs.getString("custName"));
				order.setCustPhone(rs.getString("custPhone"));
				order.setCustFirstName(rs.getString("custFirstName"));
				order.setCustLastName(rs.getString("custLastName"));
				order.setCustStreet(rs.getString("custStreet"));
				order.setCustAddressAdd(rs.getString("custAddressAdd"));
				order.setCustApartment(rs.getString("custApartment"));
				order.setCustStreet_from(rs.getString("custStreet_from"));
				order.setCustAddressAdd_from(rs.getString("custAddressAdd_from"));
				order.setCustApartment_from(rs.getString("custApartment_from"));
				order.setCustStreet_to(rs.getString("custStreet_to"));
				order.setCustAddressAdd_to(rs.getString("custAddressAdd_to"));
				order.setCustApartment_to(rs.getString("custApartment_to"));
				order.setRoaTimeLate(rs.getString("roa_time_late"));
				order.setRoaTimeOrder(rs.getString("roa_time_order"));
				order.setRoaTimeOrderStart(rs.getString("roa_time_order_start"));
				order.setRoaTimeRealStart(rs.getString("roa_time_real_start"));
				order.setRoaTimeFinish(rs.getString("roa_time_finish"));
				order.setRtName(rs.getString("rt_name"));
				order.setDriverName(rs.getString("rv_name"));
				order.setStatus(rs.getString("rds_stat_desc"));
		
				ordersData.add(order);
			}		
		} catch (Exception e) {
			System.out.println("TO TEN BLAD "+ e.getMessage());
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
	return ordersData;
	}

	public String removeOrder(Connection connection, int orderId) {
		PreparedStatement ps=null;
		try {
			String query= DbQueries.D_ORDER;
			ps = connection.prepareStatement(query);
			ps.setInt(1,orderId );
			ps.executeUpdate();
			System.out.println(" ===> removing data2:"+orderId);
		} catch (Exception e) {
			System.out.println("TO TEN BLAD "+ e.getMessage());
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	public ArrayList<UserObject> getListOfDrivers(Connection connection) {
		ArrayList<UserObject> drivers=new ArrayList<UserObject>();
		PreparedStatement ps=null;
		try {
			String query= DbQueries.S_V_DRIVER_ACC_DATA;
			ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//rds_stat_id,rds_stat_desc, rds_stat_desc_add,rds_stat_color
				UserObject user = new UserObject();
				user.setRdaId(rs.getInt("rda_id"));
				user.setRdaMail(rs.getString("rda_mail"));
				user.setRdaPhone(rs.getString("rda_phone"));
				user.setRdaRegId(rs.getString("rda_reg_id"));
				user.setRdaStatus(rs.getString("rda_status"));
				user.setRdaStatusId(rs.getInt("rda_status_id"));
				user.setRdFirstName(rs.getString("rd_first_name"));
				user.setRdid(rs.getInt("rd_id"));
				user.setRdLastName(rs.getString("rd_last_name"));
				user.setRdLogin(rs.getString("rd_login"));
				user.setRdsStatDescAdd(rs.getString("rds_stat_desc_add"));
				user.setRdsStatusId(rs.getInt("rv_last_status"));
				//user.setRmDriversRdId(rs.getInt("rd_id"));
				user.setRvId(rs.getInt("rv_id"));
				drivers.add(user);
			}
				
			//return drivers;	
			
		} catch (Exception e) {
			System.out.println("TO TEN BLAD "+ e.getMessage());
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return drivers;
	}

	public String updateDriver(Connection connection, UserObject driver) {
		String result="";
		PreparedStatement ps=null;
		try {
			//UPDATE v_driver_account_data SET rda_mail=?, rda_phone=?, rda_status_id=?, rda_status=(SELECT rs.rds_stat_desc_add FROM rm_dic_statuses rs WHERE rs.rds_id=?) WHERE rv_id=? 
			ps = connection.prepareStatement(DbQueries.U_DRIVER_ACCOUNT_V);
			ps.setString(1, driver.getRdaMail());
			ps.setString(2, driver.getRdaPhone());
			ps.setInt(3,driver.getRdaStatusId());
			ps.setInt(4,driver.getRdaStatusId());
			ps.setInt(5,driver.getRvId());
			int retRows = ps.executeUpdate();
			if (retRows > 0) {
				result="OK";
				System.out.println(result);
			}
			//UPDATE v_driver_account_data SET rd_first_name=?, rd_last_name=?, rd_login =? WHERE dad.rv_id=? 
			ps = connection.prepareStatement(DbQueries.U_DRIVERS);
			ps.setString(1, driver.getRdFirstName());
			ps.setString(2, driver.getRdLastName());
			ps.setString(3,driver.getRdLogin());
			ps.setInt(4,driver.getRvId());
			retRows = ps.executeUpdate();
			if (retRows > 0) {
				result="OK";
				System.out.println(result);
			}
			
			// UPDATE v_driver_account_data SET rv_name=? WHERE rv_id=? 
			ps = connection.prepareStatement(DbQueries.U_VEHICLES_V);
			ps.setString(1, driver.getRdLogin());
			ps.setInt(2, driver.getRvId());
			retRows = ps.executeUpdate();
			if (retRows > 0) {
				result="OK";
				System.out.println(result);
			}
		} catch (SQLException e) {
			result=e.getMessage().toString();
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	public String removeDriver(Connection connection, int vehicleId) {
		PreparedStatement ps=null;
		try {
			String query= DbQueries.D_DRIVERS;
			ps = connection.prepareStatement(query);
			ps.setInt(1,vehicleId );
			ps.executeUpdate();
			System.out.println(" ===> removing data2:"+vehicleId);
		} catch (Exception e) {
			System.out.println("TO TEN BLAD "+ e.getMessage());
			e.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
}
