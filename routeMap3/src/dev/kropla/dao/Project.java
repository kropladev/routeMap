package dev.kropla.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import dev.kropla.controllers.OrderFormController;
import dev.kropla.controllers.UserAccount;
import dev.kropla.dto.DicStatuses;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.dto.OrdersObject;
import dev.kropla.dto.ShortStats;
import dev.kropla.dto.VehicleDetailsObject;
import dev.kropla.dto.VehicleObjects;
import dev.kropla.dto.VehiclePositionObjects;
import dev.kropla.tools.Configs;
import dev.kropla.tools.Geotools;
import static dev.kropla.model.ProjectManager.log;

public class Project {

	public ArrayList<VehicleObjects> GetVehicles(Connection connection)throws Exception {
		ArrayList <VehicleObjects> vehicleData = new ArrayList<VehicleObjects>();
		PreparedStatement ps=null;
		try {
			
			if (Configs.filterStatusOption.isEmpty()){
				ps= connection.prepareStatement(DbQueries.S_VEHICLE_DATA2);
			}else{
				log.debug("Configs.filterStatusOption ="+Configs.filterStatusOption);
				String query=DbQueries.S_VEHICLE_DATA_FILTER3_1+Configs.filterStatusOption+DbQueries.S_VEHICLE_DATA_FILTER2_2;
				log.debug("GetVehicles query:"+query);
				ps= connection.prepareStatement(query);
				
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VehicleObjects vehicleObject = new VehicleObjects();
				vehicleObject.setId(rs.getInt("rv_id"));
				vehicleObject.setName(rs.getString("rv_name"));
				vehicleObject.setStatusDescAdd(rs.getString("rds_stat_desc_add"));
				vehicleObject.setStatusDescription(rs.getString("rds_stat_desc"));
				vehicleObject.setStatusColor(rs.getString("rds_stat_color"));

				vehicleData.add(vehicleObject);
			}
			//log.debug("<Project> object FINISH");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		return vehicleData;
	}

	public VehicleDetailsObject GetVehicleDetailInfo(
			Connection connection, int vehicleId) {
		VehicleDetailsObject vehicleDetails = new VehicleDetailsObject();
		PreparedStatement ps=null;
		try {
			ps = connection.prepareStatement(DbQueries.S_SINGLE_VEHICLE_DATA);
			ps.setString(1,Integer.toString(vehicleId) );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//rv_id, rv_name, rv_last_login_time, rp_lattitiude,rp_longitiude , rds_stat_desc, rds_stat_color
				vehicleDetails.setVehicleId(rs.getInt("rv_id"));
				vehicleDetails.setVehicleName(rs.getString("rv_name"));
				vehicleDetails.setLastLoginTime(rs.getString("rv_last_login_time"));
				vehicleDetails.setLatitude(rs.getDouble("rp_latitude"));
				vehicleDetails.setLongitude(rs.getDouble("rp_longitude"));
				vehicleDetails.setStatusColor(rs.getString("rds_stat_color"));
				vehicleDetails.setStatusDescription(rs.getString("rds_stat_desc_add"));
			}			
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		return vehicleDetails;
	}

	public  ArrayList<VehiclePositionObjects> GetAllVehiclesPositions(
			Connection connection) {
		 ArrayList<VehiclePositionObjects> vehicleData = new  ArrayList<VehiclePositionObjects>();
		 PreparedStatement ps=null;
		try {
			//PreparedStatement ps;
			if (Configs.filterStatusOption.isEmpty()){
				ps= connection.prepareStatement(DbQueries.S_VEHICLE_MARKERS_ALL);
			}else{
				String query=DbQueries.S_VEHICLE_MARKERS_ALL_FILTER2+Configs.filterStatusOption+")";
				log.debug("Configs.filterStatusOption ="+Configs.filterStatusOption);
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
			log.error(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		return vehicleData;
	}

	/**
	 *  deprecated - zla wartosc zwracanego charset
	 * @param connection
	 * @param statusType
	 * @return
	 */
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
				log.error(e.getMessage());
			}finally {
				try{
					if(ps!=null){
						ps.close();
					}
					if(connection!=null){
						connection.close();
					}
				}catch(SQLException e){
					log.error(e.getMessage());
				}
			}
		return dicStatuses;
	}

	public int InsertOrder(Connection connection,
			OrderFormController orderForm) throws Exception {
		String message = null;
		int retRows,newPkCustomer = 0, newPkAddressFrom=0, newPkAddressTo=0,newPkOrdeesAdd=0, newOrderPk=0;
		ResultSet rs;
		PreparedStatement ps=null;
		try {
			ps =connection.prepareStatement(DbQueries.I_NEW_ORDER_CUSTOMER,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, orderForm.getOrderCustName());
			ps.setString(2, orderForm.getOrderPhone());
			ps.setNull(3, java.sql.Types.NULL ); //address
			ps.setString(4, orderForm.getOrderCustFirstName());
			ps.setString(5, orderForm.getOrderCustLastName());
			retRows=ps.executeUpdate();	
			if (retRows<=0){
				return 0;
			}else{
				rs=ps.getGeneratedKeys();
				if(rs.next()) 
				{
					newPkCustomer = rs.getInt(1);
				}
			}
			
			//address from
			ps =connection.prepareStatement(DbQueries.I_NEW_ADDRESS,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, orderForm.getOrderAddressFrom());
			ps.setString(2, null);
			ps.setString(3, null); //address
			retRows=ps.executeUpdate();
			if (retRows<=0){
				return 0;
			}else{
				rs=ps.getGeneratedKeys();
				if(rs.next()) 
				{
					newPkAddressFrom = rs.getInt(1);
				}
			}
			
			//address to
			ps =connection.prepareStatement(DbQueries.I_NEW_ADDRESS,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, orderForm.getOrderAddressTo());
			ps.setString(2, null);
			ps.setString(3, null); //address
			retRows=ps.executeUpdate();
			if (retRows<=0){
				return 0;
			}else{
				rs=ps.getGeneratedKeys();
				if(rs.next()) 
				{
					newPkAddressTo = rs.getInt(1);
				}
			}
			
			ps =connection.prepareStatement(DbQueries.I_NEW_ORDERS_ADD,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, orderForm.getOrderTimeStart());
			retRows=ps.executeUpdate();
			if (retRows<=0){
				return 0;
			}else{
				rs=ps.getGeneratedKeys();
				if(rs.next()) 
				{
					newPkOrdeesAdd = rs.getInt(1);
				}
			}
					
			ps = connection.prepareStatement(DbQueries.I_NEW_ORDER,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, "test"); //name
			ps.setInt(2, newPkCustomer); //customer
			ps.setNull(3, java.sql.Types.INTEGER ); //route
			ps.setInt(4, newPkOrdeesAdd); //order_add
			ps.setInt(5, newPkAddressFrom); //address from
			ps.setInt(6, newPkAddressTo); //address to
			ps.setInt(7, Integer.parseInt(orderForm.getOrderType())); //order type
			
			retRows = ps.executeUpdate();
			if (retRows > 0) {
				rs=ps.getGeneratedKeys();
				if(rs.next()) 
				{
					newOrderPk = rs.getInt(1);
					return newOrderPk;
				}
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		return 0;
	}

	public ArrayList<VehiclePositionObjects> GetVehiclesPositions(
			Connection connection, String status, String position) {
		 ArrayList<VehiclePositionObjects> vehicleData = new  ArrayList<VehiclePositionObjects>();
		 PreparedStatement ps=null;
		 try {
			int statusPk = 0;
			ps = connection.prepareStatement(DbQueries.S_STATUS_ID);
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				statusPk = rs.getInt("rds_id");
			}
			
			if (statusPk > 0) {
				String query = DbQueries.S_VEHICLE_MARKERS_FILTER;
				log.debug("S_VEHICLE_MARKERS_FILTER =" + query);
				ps = connection.prepareStatement(query);
				ps.setInt(1, statusPk);
				
				rs = ps.executeQuery();
				while (rs.next()) {
					double latA,lonA,latB,lonB,dist;
					latA=rs.getDouble("rp_latitude");
					lonA=rs.getDouble("rp_longitude");
					try {
						String [] words=position.split(":");
						latB=Double.parseDouble(words[0]);
						lonB=Double.parseDouble(words[1]);
						dist=(Geotools.gps2m(latA, lonA, latB, lonB));
					}catch (NumberFormatException ex){
						log.error(ex.getMessage());
						latB=0;
						lonB=0;
						dist=0;
					}
					//format wynikowego double
					VehiclePositionObjects vehiclePositionObject = new VehiclePositionObjects();
					vehiclePositionObject.setVehicleId(rs.getInt("rv_id"));
					vehiclePositionObject.setVehicleName(rs.getString("rv_name"));
					vehiclePositionObject.setLatitude(latA);
					vehiclePositionObject.setLongitude(lonA);
					vehiclePositionObject.setStatusColor(rs.getString("rds_stat_color"));
					vehiclePositionObject.setDistance(dist);
					vehicleData.add(vehiclePositionObject);
				}
			}else{
				log.debug("statusPk ="+statusPk);
			}		
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		return vehicleData;
	}

	public String insertNewUserAccount(Connection connection,
			UserAccount account) throws Exception {
		int retRows,newPkVehicle = 0, newPkDriver=0;
		ResultSet rs;
		PreparedStatement ps=null;
		try{
		//insert new vehicle
		ps =connection.prepareStatement(DbQueries.I_NEW_VEHICLE,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, account.getName());
		ps.setInt(2, 4);
		retRows=ps.executeUpdate();
		
		if (retRows<=0){
			return null;
		}else{
			rs=ps.getGeneratedKeys();
			if(rs.next()) 
			{
				newPkVehicle = rs.getInt(1);
			}
		}
		
		//Insert new driver
		ps =connection.prepareStatement(DbQueries.I_NEW_DRIVER,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, account.getFirstName());
		ps.setString(2, account.getLastName());
		ps.setString(3, account.getName());
		ps.setInt(4, newPkVehicle);
		retRows=ps.executeUpdate();
		
		if (retRows<=0){
			return null;
		}else{
			rs=ps.getGeneratedKeys();
			if(rs.next()) 
			{
				newPkDriver = rs.getInt(1);
			}
		}
		
		//Insert new driver account
		ps =connection.prepareStatement(DbQueries.I_NEW_DRIVER_ACCOUNT,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, account.getEmail());
		ps.setString(2, account.getPhone());
		ps.setString(3, account.getRegId());
		ps.setInt(4, newPkDriver);
		ps.setString(5, "register");
		retRows=ps.executeUpdate();
		}catch(Exception ex){
			throw ex;
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		
		return null;
	}

	public ArrayList<OrdersObject> GetOrders(Connection connection, String lastListOrdersStatus) {
		ArrayList<OrdersObject> ordersData = new  ArrayList<OrdersObject>();
		PreparedStatement ps=null;
		try {
			
			log.debug("<Project> GetOrders START");
			ps = connection.prepareStatement(DbQueries.S_ORDERS_DATA);
			ps.setString(1,lastListOrdersStatus );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
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
			};			
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
	return ordersData;
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
			log.error("TO TEN BLAD "+ e.getMessage());
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
				log.error(e.getMessage());
			}
		}
	return ordersData;
	}

	@SuppressWarnings("resource")
	public String insertRegId(Connection connection, UserAccount account) throws SQLException {
		String result="OK";
		PreparedStatement ps = null;
		Boolean driverAccountRecordExists=false;
		try{
			//check if record for driverId exists
			try{
				System.out.println("checking if driver account exists");
				ps=connection.prepareStatement(DbQueries.S_DRIVER_ACCOUNT);
				ps.setInt(1,account.getDriversId());
				ResultSet rsTemp=ps.executeQuery();
				while (rsTemp.next()){
					System.out.println(rsTemp.getString("rda_mail"));
					System.out.println(rsTemp.getString("rda_phone"));
					System.out.println(rsTemp.getString("rda_reg_id"));
					System.out.println(rsTemp.getString("rda_status"));
					driverAccountRecordExists=true;
				}			
			}catch (Exception ex){
				System.err.println("exception during check if Driver account (rm_driver_account) exists for driverId:"+account.getDriversId());
				//ex.printStackTrace();
				result="ERROR_1";
			}
			
			if (driverAccountRecordExists){
				//update with proper REG_ID
				System.out.println("account exists, update with proper REG_ID");
				try{
					ps=connection.prepareStatement(DbQueries.U_DRIVER_ACCOUNT_REG_ID);
					ps.setString(1,account.getRegId());
					ps.setInt(2,account.getDriversId());			
					ps.executeUpdate();
				}catch(Exception ex){
					ex.printStackTrace();
					result="ERROR_2";
				}			
			}else{
				//Insert new record
				System.out.println("account not exists, insert new acc record");
				try{
					ps =connection.prepareStatement(DbQueries.I_NEW_DRIVER_ACCOUNT,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, account.getEmail());
					ps.setString(2, account.getPhone());
					ps.setString(3, account.getRegId());
					ps.setInt(4, account.getDriversId());
					ps.setString(5, "register");
					ps.executeUpdate();
					connection.commit();
				}catch(Exception ex){
					result="ERROR_3";
				}
			}
		}catch (Exception exMain){
			
		}finally { 
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	return result;
	}
	
	public UserAccount userAccountInfo(Connection connection,int accountId) {
		log.debug("<Project> userAccountInfo START");
		UserAccount accountOut=new UserAccount();
		PreparedStatement ps=null;
		try{
			ps = connection.prepareStatement(DbQueries.S_DRIVER_ACCOUNT_DATA);
			ps.setInt(1,accountId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accountOut.setDriversId(rs.getInt("rd_id"));
				accountOut.setEmail(rs.getString("rda_mail"));
				accountOut.setFirstName(rs.getString("rd_first_name"));
				accountOut.setLastName(rs.getString("rd_last_name"));
				accountOut.setLogin(rs.getString("rd_login"));
				accountOut.setName(rs.getString("rv_name"));
				accountOut.setPhone(rs.getString("rda_phone"));
				accountOut.setRegId(rs.getString("rda_reg_id"));
				accountOut.setVehicleId(rs.getInt("rv_id"));
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		return accountOut;
	}

	/***
	 * TO REMOVE
	 * @param connection
	 * @param orderObj
	 * @return
	 */
	public OrdersAndroidObject orderDataAndroid(Connection connection,
			OrdersAndroidObject orderObj) {
		PreparedStatement ps=null;
		try{
			ps = connection.prepareStatement(DbQueries.S_V_ORDERS_DATA_ANDROID);
			ps.setInt(1,orderObj.getOrderId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				log.debug("S_V_ORDERS_DATA_ANDROID:" +rs.getString("orderStreet_from"));
				System.out.println("S_V_ORDERS_DATA_ANDROID:" +rs.getString("orderStreet_from"));
				orderObj.setCustFirstName(rs.getString("custFirstName"));
				orderObj.setCustId(rs.getInt("custId"));
				orderObj.setCustLastName(rs.getString("custLastName"));
				orderObj.setCustName(rs.getString("custName"));
				orderObj.setCustPhone(rs.getString("custPhone"));
				orderObj.setOrderId(rs.getInt("orderId"));
				orderObj.setOrderName(rs.getString("orderName"));
				orderObj.setOrderStreet_from(rs.getString("orderStreet_from")+"test");
				orderObj.setOrderStreet_goal(rs.getString("orderStreet_goal"));
				orderObj.setRtId(rs.getInt("orderTypeId"));
				orderObj.setRtName(rs.getString("orderTypeDesc"));
				orderObj.setTimeOrderStart(rs.getString("timeOrderStart"));
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
				log.error(e.getMessage());
			}
		}
		return null;
	}

	public String confirmOrder(Connection connection, int orderId, int vehicleId, int status) {
		int retRows;
		PreparedStatement ps=null;
	
		try{
			if (status==1){
				ps =connection.prepareStatement(DbQueries.I_NEW_ORDER_VEHICLE_RELATION,Statement.RETURN_GENERATED_KEYS);
				ps.setInt(2, orderId);
				ps.setInt(1, vehicleId);
				retRows=ps.executeUpdate();
			
				if (retRows<=0){
					return "ERROR";
				}
			}
			ps =connection.prepareStatement(DbQueries.U_ORDER_NEGOC);
			ps.setInt(1, orderId);
			ps.setInt(2, vehicleId);
			ps.executeUpdate();
			
			ps =connection.prepareStatement(DbQueries.I_NEW_ORDER_NEGOC,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, orderId);
			ps.setInt(2, vehicleId);
			ps.setInt(3, status);
			retRows=ps.executeUpdate();
		
			if (retRows<=0){
				return "ERROR2";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			try{
				if(ps!=null){
					ps.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(SQLException e){
				log.error(e.getMessage());
			}
		}
		return "OK";
	}

	public String getRegIdForUserByVehicleId(Connection connection,
			int vehicleId) {
		PreparedStatement ps=null;
		try {
			ps =connection.prepareStatement(DbQueries.S_GET_REGID_BY_VEHICLEID);
			ps.setInt(1, vehicleId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString("rda_reg_id");
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
				log.error(e.getMessage());
			}
		}
		return "ERROR";
	}

	public ShortStats countShortStats(Connection connection) {
		ShortStats stat= new ShortStats();
		PreparedStatement ps=null;
		try {
			//log.debug("Project.countShortStats start");
			//liczba kierowcow w podziale na active/register/locked -konta
			ps =connection.prepareStatement(DbQueries.S_SHORT_STATS1);
			ResultSet rs = ps.executeQuery();
			//log.debug("Project.countShortStats after first Query");
			int recCount=0;
			int recCountAll=0;
			String colName="";
			//ustaw zero - gdyby nie bylo wynikow
			stat.setDriversRegistered(recCount);
			stat.setDriversActive(recCount);
			//log.debug("Project.countShortStats after before rs.next");
			while (rs.next()) {
				recCount=rs.getInt("counter");
				colName=rs.getString("accountStatus");
				//log.debug("Project.countShortStats recCount:"+recCount);
				//log.debug("Project.countShortStats colName:"+colName);
				recCountAll+=recCount;
				if (colName.equals("rejestracja")){
					stat.setDriversRegistered(recCount);
				}else if (colName.equals("aktywne")){
					stat.setDriversActive(recCount);
				}
			}
			stat.setDriverVehiclesAll(recCountAll);
			//log.debug("Project.countShortStats stat.getDriverVehiclesAll:"+stat.getDriverVehiclesAll());
			//liczba kierowcow wg obecnego statusu wykonywanej pracy rejestracja/wolna/wylogowana
			ps =connection.prepareStatement(DbQueries.S_SHORT_STATS2);
			rs = ps.executeQuery();
			int recCount2=0;
			String colName2="";
			stat.setDriversOnRoute(recCount2);
			stat.setDriversFree(recCount2);
			
			while (rs.next()) {
				recCount2=rs.getInt("counter");
				colName2=rs.getString("driverStatus");
				
				if (colName2.equals("w trasie")){
					stat.setDriversOnRoute(recCount2);
				}else if (colName2.equals("wolna")){
					stat.setDriversFree(recCount2);
				}
			}
			log.debug("Project.countShortStats stat.getDriversFree:"+stat.getDriversFree());
			
			ps =connection.prepareStatement(DbQueries.S_SHORT_STATS3);
			rs = ps.executeQuery();
			int recCount3=0;
			int recCount3All=0;
			int recCount3Todo=0;
			int recCount3Active=0;
			//int recCount
			stat.setOrdersDoneAll(recCount3);
			stat.setOrdersNegoc(recCount3);
			stat.setOrdersRejected(recCount3);
			String colName3="";
			int colId3=0;
			//log.debug("Project.countShortStats before rs.next");
			while (rs.next()) {
				recCount3=rs.getInt("counter");
				colName3=rs.getString("orderStatus");
				colId3=rs.getInt("id");
				//log.debug("Project.countShortStats recCount:"+recCount3);
				//log.debug("Project.countShortStats orderStatus:"+colName3);
				if(colName3!=null){
					if (colId3==9){
						stat.setOrdersDoneAll(recCount3);
					}else if (colId3==5){
						recCount3Todo+=recCount3;
					}else if (colId3==6){
						stat.setOrdersNegoc(recCount3);
						recCount3Todo+=recCount3;
					}else if (colId3==7){
						recCount3Todo+=recCount3;
					}else if (colId3==8){
						recCount3Active+=recCount3;
					}else if (colId3==10){
						stat.setOrdersRejected(recCount3);
					}else if (colId3==12){
						recCount3Active+=recCount3;
					}else if (colId3==13){
						recCount3Active+=recCount3;
					}else if (colId3==11){
					}
				}
				recCount3All+=recCount3;
			}
			stat.setOrdersAll(recCount3All);
			stat.setOrdersToDo(recCount3Todo);
			stat.setOrdersActive(recCount3Active);
			//log.debug("Project.countShortStats stat.getOrdersActive:"+stat.getOrdersActive());
			//dzisiejsze zamowienia
			ps =connection.prepareStatement(DbQueries.S_SHORT_STATS4);
			rs = ps.executeQuery();
			int recCount4=0;
			int recCount4All=0;
			int recId4=0;
			String colName4="";
			while (rs.next()) {
				recCount4=rs.getInt("counter");
				colName4=rs.getString("todayOrderStatus");
				recId4=rs.getInt("id");
				recCount4All+=recCount4;
			}
			stat.setOrdersTodayAll(recCount4All);
			//log.debug("Project.countShortStats stat.getOrdersTodayAll:"+stat.getOrdersTodayAll());
			//log.debug("Project.countShortStats end");
		} catch (SQLException e) {
			log.error("ERRRRRRR");
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
				log.error(e.getMessage());
			}
		}
		return stat;
	}
}
