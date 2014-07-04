package dev.kropla.dao;

public class DbQueries {
	
	public static final String S_ORDERS_DATA=
			"SELECT orderId, orderName, custName,custPhone,custFirstName,custLastName,custStreet,custAddressAdd," +
			"custApartment,custStreet_from,custAddressAdd_from,custApartment_from,custStreet_to,custAddressAdd_to," +
			"custApartment_to," +
			"DATE_FORMAT(roa_time_late,'%Y-%m-%d %H:%i') as roa_time_late," +
			"DATE_FORMAT(roa_time_order,'%Y-%m-%d %H:%i') as roa_time_order," +
			"DATE_FORMAT(roa_time_order_start,'%Y-%m-%d %H:%i') as roa_time_order_start," +
			"DATE_FORMAT(roa_time_real_start,'%Y-%m-%d %H:%i') as roa_time_real_start," +
			"DATE_FORMAT(roa_time_finish,'%Y-%m-%d %H:%i') as roa_time_finish," +
			"rt_name,rv_name,rds_stat_desc,rds_id " +
			"FROM orders_data_all " +
			"WHERE rds_id IN( SELECT ros_group_status_id FROM rm_orders_status_groups WHERE ros_group_symbol=? ) ";
	
	public static final String S_ORDERS_DATA2="ORDER BY roa_time_order DESC ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_TODAY=
			" AND  DATE_FORMAT(roa_time_order,'%Y-%m-%d') = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d') ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_YESTERDAY=
			" AND roa_time_order > ADDDATE( NOW(),INTERVAL -1 DAY) ";
	
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_LAST3DAYS=
			" AND roa_time_order > ADDDATE( NOW(),INTERVAL -3 DAY) ";
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_LASTWEEK=
			" AND roa_time_order > ADDDATE( NOW(),INTERVAL -7 DAY) ";
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_LASTMONTh=
			" AND roa_time_order > ADDDATE( NOW(),INTERVAL -30 DAY) ";
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_NEXTWEEK=
			   " AND roa_time_order < ADDDATE(NOW(),INTERVAL 7 DAY) AND roa_time_order > NOW() ";
	public static final String S_ORDERS_DATA_RANGE_BY_DATE_FUTUREALL=
			" AND roa_time_order > NOW() ";


	public static final String S_VEHICLE_DATA=
			"SELECT rv_id, rv_name,rds_stat_desc,rds_stat_desc_add, rds_stat_color  " +
			"FROM rm_vehicles,rm_dic_statuses " +
			"WHERE rv_last_status=rds_id " +
			"ORDER BY rv_last_login_time DESC ";
	
	public static final String S_VEHICLE_DATA2=
			"SELECT rv_id, rv_name,rds_stat_desc,rds_stat_desc_add, rds_stat_color  " +
			"FROM v_driver_account_data " +
			"WHERE rda_status IN ('aktywne') " +
			"ORDER BY rv_last_login_time DESC ";
	
	public static final String S_VEHICLE_DATA_FILTER=
			"SELECT rv_id, rv_name,rds_stat_desc,rds_stat_desc_add, rds_stat_color  " +
			"FROM rm_vehicles,rm_dic_statusess " +
			"WHERE rv_last_status=rds_id " +
			"AND rv_last_status IN(?) " +
			"ORDER BY rv_last_login_time DESC";
	
	public static final String S_VEHICLE_DATA_FILTER2_1=
	"SELECT rv_id, rv_name,rds_stat_desc,rds_stat_desc_add, rds_stat_color  " +
	"FROM rm_vehicles,rm_dic_statuses " +
	"WHERE rv_last_status=rds_id " +
	"AND rv_last_status IN(";
	
	public static final String S_VEHICLE_DATA_FILTER3_1=
	"SELECT rv_id, rv_name,rds_stat_desc,rds_stat_desc_add, rds_stat_color  " +
	"FROM v_driver_account_data " +
	"WHERE rda_status IN ('aktywne') " +
	"AND rv_last_status IN(";
	
	
	public static final String S_VEHICLE_DATA_FILTER2_2=
	") ORDER BY rv_last_login_time DESC";
	
	public static final String S_SINGLE_VEHICLE_DATA=
			"SELECT rv_id, rv_name, DATE_FORMAT(rv_last_login_time,'%Y-%m-%d %H:%i') rv_last_login_time, rp_latitude,rp_longitude , rds_stat_desc_add, rds_stat_color " +
			"FROM rm_vehicles,rm_dic_statuses,rm_positions " +
			"WHERE rv_id=? " +
			"AND rv_last_status=rds_id " +
			"AND rv_last_position=rp_id";
	
	/*public static final String S_VEHICLE_POSITIONS_ALL=
			"SELECT rv_id,rv_name,rp_latitude,rp_longitude " +
			"FROM v_vehicle_positions";*/
	
	//public static final String DB_QUERY_SELECT_VEHICLE_POSITIONS_ALL_FILTER="SELECT rv_id,rv_name,rp_latitude,rp_longitude FROM v_vehicle_positions"; 
	
	public static final String S_VEHICLE_MARKERS_ALL= 
			"select rv_id, rv_name, rp_longitude, rp_latitude, rds_stat_color " +
			"FROM v_vehicle_position_markers";
	
	public static final String S_VEHICLE_MARKERS_ALL_FILTER= 
			"select rv_id, rv_name, rp_longitude, rp_latitude, rds_stat_color " +
			"FROM v_vehicle_position_markers " +
			"WHERE rds_id IN(?)";
	
	public static final String S_STATUSES_ALL= 
			"select rds_id,rds_stat_desc, rds_stat_desc_add,rds_stat_color " +
			"FROM rm_dic_statuses WHERE rds_stat_type=?";
	
	public static final String S_STATUS_ID= 
			"select rds_id " +
			"FROM rm_dic_statuses "+
			"WHERE rds_stat_id=?"		;
	
	public static final String S_DRIVER_ACCOUNT_DATA=
			  "SELECT rda_mail,rda_phone,rda_reg_id,rda_status," +
			  "rd_id,rd_first_name,rd_last_name,rd_login," +
			  "rv_id,rv_name,rv_last_status,rv_last_login_time "+
			" FROM v_driver_account_data " +
			" WHERE rd_id=? ";

	public static final String S_V_ORDERS_DATA_ANDROID=
			  "SELECT orderId, orderName,custName,custPhone,custFirstName," +
			  "custLastName, orderStreet_from,orderStreet_goal,timeOrderStart, orderTypeDesc,orderTypeId, custId, orderStatusDesc, orderStatusId "+
			" FROM v_orders_data_android " +
			" WHERE orderId=? ";
	
	public static final String S_DRIVER_ACCOUNT=
			  "SELECT rda_mail,rda_phone,rda_reg_id,rda_status " +
			" FROM rm_driver_account " +
			" WHERE rm_drivers_rd_id=? ";
	

	public static final String S_ORDERS_DATA_ANDROID=
			  "SELECT ro.ro_id as orderId, ro.ro_name as orderName," +
			  " rc.rc_name as custName, rc.rc_phone as custPhone, rc.rc_firstname as custFirstName," +
			  " rc.rc_lastname AS custLastName, ra1.ra_street as orderStreet_from,ra2.ra_street as orderStreet_goal," +
			  " oa.roa_time_order_start as timeOrderStart, dot.rt_name as orderTypeDesc,ro.rm_dic_order_types_rt_id as orderTypeId, " +
			  " ro.ro_customer as custId "+
			" FROM rm_orders ro, rc_address ra1, rc_address ra2, rm_dic_order_types dot,rm_customers rc, rm_orders_add oa " +
			" WHERE ro.ro_customer=rc.rc_id " +
			" AND ra1.ra_id=ro.rc_address_ra_id_from " +
			" AND ra2.ra_id=ro.rc_address_ra_id_to " +
			" AND dot.rt_id=ro.rm_dic_order_types_rt_id " +
			" AND oa.roa_id=ro.rm_orders_add_roa_id " +
			" AND orderId=? ";
	
	public static final String U_DRIVER_ACCOUNT_REG_ID=
			"UPDATE rm_driver_account set rda_reg_id=? WHERE rm_drivers_rd_id=?";
	
	public static final String S_VEHICLE_MARKERS_ALL_FILTER2=
	"select rv_id, rv_name, rp_longitude, rp_latitude, rds_stat_color " +
	"FROM v_vehicle_position_markers " +
	"WHERE rds_id IN (";
	
	public static final String S_VEHICLE_MARKERS_FILTER=
	"select rv_id, rv_name, rp_longitude, rp_latitude, rds_stat_color " +
	"FROM v_vehicle_position_markers " +
	"WHERE rds_id =?";
	
	
	public static final String I_NEW_ORDER= 
			"INSERT INTO rm_orders(ro_name,ro_customer,rm_routes_rr_id,rm_orders_add_roa_id,rc_address_ra_id_from, rc_address_ra_id_to, rm_dic_order_types_rt_id,rm_status) VALUES(?,?,?,?,?,?,?,5) ";

	public static final String I_NEW_ORDER_CUSTOMER= 
			"INSERT INTO rm_customers(rc_name,rc_phone, rc_address_ra_id,rc_firstname,rc_lastname) VALUES(?,?,?,?,?) ";

	public static final String I_NEW_ORDER_CUS_ADDRESS= 
			"INSERT INTO rc_address(ra_street,ra_street_add,ra_apartment) VALUES(?,?,?) ";

	public static final String I_NEW_ADDRESS= 
			"INSERT INTO rc_address(ra_street,ra_street_add,ra_apartment) VALUES(?,?,?) ";
	public static final String I_NEW_ORDER_VEHICLE_RELATION= 
			"INSERT INTO rm_order_vehicle_rel(rm_vehicles_rv_id,rm_orders_ro_id) VALUES(?,?) ";
	/*
	public static final String I_NEW_ORDER_CUS_ADDRESS_FROM= 
			"INSERT INTO rc_address(ra_street,ra_street_add,ra_apartment) VALUES(?,?,?) ";
	
	public static final String I_NEW_ORDER_CUS_ADDRESS_TO= 
			"INSERT INTO rc_address(ra_street,ra_street_add,ra_apartment) VALUES(?,?,?) ";*/
	
	public static final String I_NEW_ORDERS_ADD= 
			"INSERT INTO rm_orders_add(roa_time_order,roa_time_order_start) VALUES(CURRENT_TIMESTAMP,?)"; // VALUES(DATE_FORMAT(CURRENT_TIMESTAMP, '%e-%M-%Y %H:%i'),?) ";
	
	public static final String I_NEW_ORDER_NEGOC= 
			"INSERT INTO rm_orders_negoc(ron_order_id,ron_vehicle_id, ron_status, ron_create_time) VALUES(?,?,?,CURRENT_TIMESTAMP)";//, '%e-%M-%Y %H:%i'))";
	
	public static final String I_NEW_ROUTES= 
			"INSERT INTO rm_routes(rr_status,rr_start_time, rr_length) VALUES(?,?,?) ";
	
	public static final String I_NEW_ROUTE_POSITION= 
			"INSERT INTO rm_route_positions(rr_lat,rr_lon, rr_timestamp, rm_routes_rr_id) VALUES(?,?,?,?) ";
	
	public static final String I_NEW_DRIVER_ACCOUNT= 
			"INSERT INTO rm_driver_account(rda_mail,rda_phone, rda_reg_id, rm_drivers_rd_id, rda_status) VALUES(?,?,?,?,?) ";

	public static final String I_NEW_DRIVER=
			"INSERT INTO rm_drivers(rd_first_name,rd_last_name, rd_login, rm_vehicles_rv_id) VALUES(?,?,?,?) ";

	public static final String I_NEW_VEHICLE=
			"INSERT INTO rm_vehicles(rv_name,rv_last_status, rv_last_login_time) VALUES(?,?,CURRENT_TIMESTAMP) ";//DATE_FORMAT(CURRENT_TIMESTAMP, '%e-%M-%Y %H:%i')) ";

	public static final String S_GET_REGID_BY_VEHICLEID = "SELECT rda_reg_id FROM v_driver_account_data where rv_id=?";

	public static final String U_ORDER_NEGOC = "UPDATE rm_orders_negoc SET ron_end_date=CURRENT_TIMESTAMP WHERE ron_order_id=? AND ron_vehicle_id=?";


	public static final String S_SHORT_STATS1="SELECT COUNT(*) as counter,vda.rda_status as accountStatus from v_driver_account_data vda GROUP BY rda_status";
	
	public static final String S_SHORT_STATS2="SELECT COUNT(*) as counter,vda.rds_stat_desc_add as driverStatus from v_driver_account_data vda GROUP BY vda.rds_stat_desc_add";
	
	public static final String S_SHORT_STATS3="SELECT COUNT(*) as counter,voda.rds_stat_desc as orderStatus,voda.rds_id as id FROM orders_data_all voda GROUP BY voda.rds_stat_desc,rds_id";
	
	public static final String S_SHORT_STATS4="SELECT COUNT(*) as counter,voda.rds_stat_desc as todayOrderStatus, rds_id as id FROM orders_data_all voda WHERE  DATE_FORMAT(roa_time_order,'%Y-%m-%d') = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d') GROUP BY voda.rds_stat_desc,rds_id";
	
	
}
