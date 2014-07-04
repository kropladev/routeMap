package dev.kropla.connection.webservice;

public class WebServiceConfig {

	public static final String NAMESPACE = "http://kropladev.pl/";
	//public static final String URL_1="http://"; 
	//http://localhost:8080/routeMapWS/services/PositionWS?wsdl
	public static final String URL_2 = "/routeMapWS/services/PositionWS?wsdl";	
	//public static String hostAddress="kropladev.pl";
	//public static String hostAddress="10.0.1.12:8080";
	//public static String hostAddress="10.0.1.13:8080";
	//public static String ipNumber="10.0.1.10";//siec alfa_blue w domu
	//public static String ipNumber="10.0.2.2"; //emulator na laptopie
	//public static String ipNumber="192.168.43.2"; //android ruter -polaczenie wifi do androida; 
	//public static String ipNumber="192.168.43.2"; //ruter usb
	//private static final String METHOD_NAME = "getSubimage";
	//private static final String METHOD_NAME = "getSubimage";
	//private static final String SOAP_ACTION = "http://javaeenotes.com/getSubimage";

	//public static final String METHOD_NAME = "insertGPSCoord";
	public static final String METHOD_COORDS2 = "insertGPSCoord2";
	public static final String METHOD_REGISTER = "registerNewAccount";
	public static final String METHOD_LOGIN = "logIntoApplication";
	public static final String METHOD_LOGIN_ID = "logIntoApplicationId";
	public static final String METHOD_CHANGE_VEHICLE_STATUS = "changeVehicleStatus";
	public static final String METHOD_ORDERS_USER = "getLastOrdersForUser";

	public static final String METHOD_ORDER_DATA_BY_ID = "getOrderDataById";	
	
	//public static final String SOAP_ACTION = "http://kropladev.pl/insertGPSCoord";
	public static final String METHOD_NEWROUTE = "insertNewRoute";
	public static final String METHOD_UPDATEROUTE = "updateRoute";
	public static final String METHOD_CHANGE_ORDER_STATUS = "changeOrderStatus";

}
