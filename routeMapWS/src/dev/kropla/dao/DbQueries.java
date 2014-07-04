package dev.kropla.dao;

public class DbQueries {
	
	public static final String D_ORDER = "DELETE FROM rm_orders WHERE ro_id=?";
	public static final String D_DRIVERS = "DELETE FROM rm_vehicles WHERE rv_id=?";
	
	//public static final String D_NEGOC = "DELETE FROM rm_orders WHERE ro_id=?"
	public static final String S_V_DRIVER_ACC_DATA=
			" SELECT rda_id,rda_mail,rda_phone,rda_reg_id,rda_status,rda_status_id,rd_id,rd_first_name,rd_last_name,rd_login,rv_id,rv_name,rv_last_status,rv_last_login_time,rv_last_position,rds_stat_desc_add,rds_stat_desc,rds_stat_color " +
			" FROM v_driver_account_data ORDER BY rd_login ";
	
	public static final String S_ORDERS_DATA=
			"SELECT * FROM orders_data_by_status_groups "+
			"WHERE rds_id IN( SELECT ros_group_status_id FROM rm_orders_status_groups WHERE ros_group_symbol=? ) ";
	
	public static final String S_ORDERS_DATA_USER = 
			"SELECT orderId,roa_time_order,custName,custStreet_from,roa_time_order_start,orderName,rds_stat_desc,rt_name,rds_stat_desc, rds_id " +
			" FROM orders_data_by_status_groups "+
			" WHERE rds_id IN( SELECT ros_group_status_id FROM rm_orders_status_groups WHERE ros_group_symbol=? ) " +
			 " AND rv_name = ? ";

	public static final String S_VEHICLE_MARKERS_ALL= 
			"select rv_id, rv_name, rp_longitude, rp_latitude, rds_stat_color " +
			"FROM v_vehicle_position_markers";

	public static final String S_VEHICLE_MARKERS_ALL_FILTER2=
	"select rv_id, rv_name, rp_longitude, rp_latitude, rds_stat_color " +
	"FROM v_vehicle_position_markers " +
	"WHERE rds_id IN (";

	public static final String S_USER_ACCOUNT_EXISTS = 
			"select rd_id " +
			"FROM  rm_driver_account, rm_drivers " +
			" WHERE rm_drivers_rd_id=rd_id " +
			" AND rd_login=? ";

	public static final String I_NEW_DRIVER_ACCOUNT= 
			"INSERT INTO rm_driver_account(rda_mail,rda_phone, rda_reg_id, rm_drivers_rd_id, rda_status) VALUES(?,?,?,?,?) ";

	public static final String I_NEW_DRIVER=
			"INSERT INTO rm_drivers(rd_first_name,rd_last_name, rd_login, rm_vehicles_rv_id) VALUES(?,?,?,?) ";
	
	public static final String I_NEW_VEHICLE=
			"INSERT INTO rm_vehicles(rv_name,rv_last_status, rv_last_login_time) VALUES(?,?,CURRENT_TIMESTAMP) ";
	
	public static final String U_VEHICLE_STATUS=
			" UPDATE rm_vehicles SET rv_last_status=? WHERE rv_name=? ";

	public static final String U_VEHICLE_STATUS2=
			" UPDATE rm_vehicles SET rv_last_status=?,rv_last_login_time=(CURRENT_TIMESTAMP) WHERE rv_name=? ";
	
	public static final String U_ORDER_DATA=
			" UPDATE orders_data_by_status_groups SET custStreet_from=? WHERE orderId=? ";
	
	public static final String U_DRIVER_ACCOUNT_V=
			" UPDATE v_driver_account_data  SET rda_mail=?, rda_phone=?, rda_status_id=?, " +
			" rda_status=(SELECT rs.rds_stat_desc_add FROM rm_dic_statuses rs WHERE rs.rds_id=?) " +
			" WHERE rv_id=? "; 
	
	public static final String U_DRIVERS_V= 
			" UPDATE v_driver_account_data  SET rd_first_name=?, rd_last_name=?, rd_login =? " +
			" WHERE rv_id=?  ";
	
	public static final String U_VEHICLES_V= 
			" UPDATE v_driver_account_data  SET rv_name=? " +
			" WHERE rv_id=? ";

	public static final String U_DRIVER_ACCOUNT=
			" UPDATE rm_driver_account  SET rda_mail=?, rda_phone=?, rda_status_id=?, " +
			" rda_status=(SELECT rs.rds_stat_desc_add FROM rm_dic_statuses rs WHERE rs.rds_id=?) " +
			" WHERE rda_id=? ";
	
	public static final String U_DRIVERS= 
			" UPDATE rm_drivers  SET rd_first_name=?, rd_last_name=?, rd_login =? " +
			" WHERE rm_vehicles_rv_id=? ";
	

	public static final String U_VEHICLES= 
			" UPDATE rm_vehicles  SET rv_name=? " +
			" WHERE rv_id=? ";
	
	public static final String U_ORDER_DATA2=
			" UPDATE rm_orders_add SET roa_time_order_start=? WHERE roa_id= (SELECT rm_orders_add_roa_id FROM rm_orders WHERE ro_id=?) "; 
	
	public static final String U_ORDER_DATA3=
			" UPDATE rm_orders SET ro_name=?,rm_status=? WHERE ro_id=? ";
	
	public static final String U_ORDER_DATA4=
			" UPDATE rm_orders_negoc SET ron_end_date=CURRENT_TIMESTAMP WHERE ron_order_id=? and ron_vehicle_id = (Select rv_id FROM rm_vehicles WHERE rv_name=?) ";
	public static final String I_ORDER_DATA5=
			" INSERT INTO rm_orders_negoc (ron_create_time,ron_order_id, ron_vehicle_id) VALUES (CURRENT_TIMESTAMP,?,(Select rv_id FROM rm_vehicles WHERE rv_name=?))  ";
	
	public static final String S_V_ORDERS_DATA_ANDROID=
			  "SELECT orderId, orderName,custName,custPhone,custFirstName," +
			  "custLastName, orderStreet_from,orderStreet_goal, DATE_FORMAT(timeOrderStart,'%Y-%m-%d %H:%i')  timeOrderStart, orderTypeDesc,orderTypeId, custId, " +
			  "orderStatusDesc, orderStatusId "+
			" FROM v_orders_data_android " +
			" WHERE orderId=? ";

	public static final String S_STATUSES_ALL = "select rds_id,rds_stat_desc, rds_stat_desc_add,rds_stat_color " +
			"FROM rm_dic_statuses WHERE rds_stat_type=?";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_TODAY=
			" AND  DATE_FORMAT(roa_time_order_start,'%Y-%m-%d') = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d') ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_YESTERDAY=
			" AND DATE_FORMAT(roa_time_order_start,'%Y-%m-%d') = DATE_FORMAT(ADDDATE( NOW(),INTERVAL -1 DAY), '%Y-%m-%d') ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_LAST3DAYS=
			" AND  DATE_FORMAT(roa_time_order_start,'%Y-%m-%d') BETWEEN DATE_FORMAT(ADDDATE( NOW(),INTERVAL -3 DAY), '%Y-%m-%d') AND DATE_FORMAT(NOW(), '%Y-%m-%d') ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_LASTWEEK=
			" AND  DATE_FORMAT(roa_time_order_start,'%Y-%m-%d') BETWEEN  DATE_FORMAT(ADDDATE( NOW(),INTERVAL -7 DAY), '%Y-%m-%d') AND DATE_FORMAT(NOW(), '%Y-%m-%d')  ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_LASTMONTh=
			" AND  DATE_FORMAT(roa_time_order_start,'%Y-%m-%d') BETWEEN  DATE_FORMAT(ADDDATE( NOW(),INTERVAL -30 DAY), '%Y-%m-%d') AND DATE_FORMAT(NOW(), '%Y-%m-%d') ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_NEXTWEEK=
			   " AND  DATE_FORMAT(roa_time_order_start,'%Y-%m-%d') BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d') AND DATE_FORMAT(ADDDATE(NOW(),INTERVAL 7 DAY), '%Y-%m-%d') ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_FUTUREALL=
			" AND roa_time_order_start > NOW() ";
	public static final String S_ORDERS_DATA2="ORDER BY roa_time_order_start DESC ";
}
