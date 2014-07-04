package dev.kropla.connection.webservice;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import dev.kropla.connection.webservice.WebServiceConfig;
import dev.kropla.connection.webservice.pojo.MethodArgChangeStatus;
import dev.kropla.connection.webservice.pojo.MethodArgOrder;
import dev.kropla.connection.webservice.pojo.MethodArgGetUsersOrders;
import dev.kropla.connection.webservice.pojo.MethodArgInsertGpsCoord;
import dev.kropla.connection.webservice.pojo.MethodArgRoute;
import dev.kropla.connection.webservice.pojo.MethodArgLogin;
import dev.kropla.connection.webservice.pojo.MethodArgRegister;
import dev.kropla.utils.Config;

public class WebServiceConnect {
private static final String TAG_ACTIVITY="WebServiceConnect";
	
/*	public static boolean insertLocation (MethodArgInsertGpsCoord wsArg){		
		boolean resultState=false;
		String methodName=WebServiceConfig.METHOD_NAME;
		//System.out.println("registerNewAccount");
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		//add arguments to send
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_LAT,PropertyInfo.STRING_CLASS,wsArg.getLat().toString()));
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_LON,PropertyInfo.STRING_CLASS,wsArg.getLon().toString()));
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_VEHICLE_ID,PropertyInfo.STRING_CLASS,Integer.toString(wsArg.getVehicleId())));
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_VEHICLE_STATUS,PropertyInfo.STRING_CLASS,Integer.toString(wsArg.getStatusId())));
		String resultString=performWebServiceMethodCall(request,methodName);
		if (resultString=="true"){
			resultState=true;
		}
		return resultState;
	}
	*/
	
	public static String insertLocation2(MethodArgInsertGpsCoord wsArg){		
		String methodName=WebServiceConfig.METHOD_COORDS2;
		//System.out.println("registerNewAccount");
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_VEHICLENAME,PropertyInfo.STRING_CLASS,wsArg.getVehicleName()));
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_LON,PropertyInfo.STRING_CLASS,Double.toString(wsArg.getLon())));
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_LAT,PropertyInfo.STRING_CLASS,Double.toString(wsArg.getLat())));
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_UPDATE_ROUTES_POSITION,PropertyInfo.STRING_CLASS,Boolean.toString(wsArg.isUpdateRoutesPosition())));
		request.addProperty(setPropertyValue(MethodArgInsertGpsCoord.FIELDNAME_ROUTE_ID,PropertyInfo.STRING_CLASS,Integer.toString(wsArg.getRouteId())));
		
		//Log.d(TAG_ACTIVITY+"insertLocation2", request.getProperty(0).toString());
		//Log.d(TAG_ACTIVITY+"insertLocation2", request.getProperty(1).toString());
		//Log.d(TAG_ACTIVITY+"insertLocation2", request.getProperty(2).toString());
		return performWebServiceMethodCall(request,methodName);
	}

	public static String insertNewRoute(MethodArgRoute wsArg){		
		String methodName=WebServiceConfig.METHOD_NEWROUTE;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgRoute.FIELDNAME_ORDERID,PropertyInfo.STRING_CLASS,Integer.toString(wsArg.getOrderId())));
		//Log.d(TAG_ACTIVITY+"insertNewRoute", request.getProperty(0).toString());
		return performWebServiceMethodCall(request,methodName);
	}

	public static String updateRoute(MethodArgRoute wsArg){		
		String methodName=WebServiceConfig.METHOD_UPDATEROUTE;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgRoute.FIELDNAME_ORDERID,PropertyInfo.STRING_CLASS,Integer.toString(wsArg.getOrderId())));
		request.addProperty(setPropertyValue(MethodArgRoute.FIELDNAME_ROUTEID,PropertyInfo.STRING_CLASS,Integer.toString(wsArg.getRouteId())));
		request.addProperty(setPropertyValue(MethodArgRoute.FIELDNAME_STATUS,PropertyInfo.STRING_CLASS,wsArg.getStatus()));
		request.addProperty(setPropertyValue(MethodArgRoute.FIELDNAME_LENGTH,PropertyInfo.STRING_CLASS,Double.toString(wsArg.getLength())));
		
		//Log.d(TAG_ACTIVITY+"updateRoute", request.getProperty(0).toString());
		//Log.d(TAG_ACTIVITY+"updateRoute", request.getProperty(1).toString());
		//Log.d(TAG_ACTIVITY+"updateRoute", request.getProperty(2).toString());
		//Log.d(TAG_ACTIVITY+"updateRoute", request.getProperty(3).toString());
		return performWebServiceMethodCall(request,methodName);
	}

	public static String changeOrderStatus(MethodArgOrder wsArg){		
		String methodName=WebServiceConfig.METHOD_CHANGE_ORDER_STATUS;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgOrder.FIELDNAME_ORDER_ID,PropertyInfo.STRING_CLASS,wsArg.getOrderId()));
		request.addProperty(setPropertyValue(MethodArgOrder.FIELDNAME_STATUS_ID,PropertyInfo.STRING_CLASS,Integer.toString(wsArg.getStatusId())));
	
		//Log.d(TAG_ACTIVITY+"changeOrderStatus",MethodArgOrder.FIELDNAME_ORDER_ID+":"+request.getProperty(0).toString());
		//Log.d(TAG_ACTIVITY+"changeOrderStatus", MethodArgOrder.FIELDNAME_STATUS_ID+":"+request.getProperty(1).toString());

		return performWebServiceMethodCall(request,methodName);
	}
	
	public static String registerNewAccount(MethodArgRegister wsArg){		
		String methodName=WebServiceConfig.METHOD_REGISTER;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE,methodName);
		request.addProperty(setPropertyValue(MethodArgRegister.FIELDNAME_USER_ACCOUNT_NAME,PropertyInfo.STRING_CLASS,wsArg.getUserAccountName()));
		request.addProperty(setPropertyValue(MethodArgRegister.FIELDNAME_USER_MAIL,PropertyInfo.STRING_CLASS,wsArg.getUserMailName()));
		request.addProperty(setPropertyValue(MethodArgRegister.FIELDNAME_USER_PHONE,PropertyInfo.STRING_CLASS,wsArg.getUserPhone()));
		request.addProperty(setPropertyValue(MethodArgRegister.FIELDNAME_API_KEY,PropertyInfo.STRING_CLASS,wsArg.getApiRegKey()));
		request.addProperty(setPropertyValue(MethodArgRegister.FIELDNAME_USER_FNAME,PropertyInfo.STRING_CLASS,wsArg.getUserFName()));
		request.addProperty(setPropertyValue(MethodArgRegister.FIELDNAME_USER_LNAME,PropertyInfo.STRING_CLASS,wsArg.getUserLName()));
		
		return  performWebServiceMethodCall(request,methodName);
	}
	
	public static String logIntoApplication(MethodArgLogin wsArg){		
		String methodName=WebServiceConfig.METHOD_LOGIN;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgLogin.FIELDNAME_USER_LOGIN,PropertyInfo.STRING_CLASS,wsArg.getUserLogin()));
		return performWebServiceMethodCall(request,methodName);
	}
	
	public static String changeVehicleStatus(MethodArgChangeStatus wsArg){		
		String methodName=WebServiceConfig.METHOD_CHANGE_VEHICLE_STATUS;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgChangeStatus.FIELDNAME_LOGIN,PropertyInfo.STRING_CLASS,wsArg.getUserAccountLogin()));
		request.addProperty(setPropertyValue(MethodArgChangeStatus.FIELDNAME_STATUS,PropertyInfo.STRING_CLASS,wsArg.getStatus()));
		return performWebServiceMethodCall(request,methodName);
	}
	
	public static String logIntoApplicationId(MethodArgLogin wsArg){		
		String methodName=WebServiceConfig.METHOD_LOGIN_ID;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgLogin.FIELDNAME_USER_LOGIN,PropertyInfo.STRING_CLASS,wsArg.getUserLogin()));
		return performWebServiceMethodCall(request,methodName);
	}

	public static String getLastOrdersForUser(MethodArgGetUsersOrders wsArg){		
		String methodName=WebServiceConfig.METHOD_ORDERS_USER;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgGetUsersOrders.FIELDNAME_USER_LOGIN,PropertyInfo.STRING_CLASS,wsArg.getUserLogin()));
		request.addProperty(setPropertyValue(MethodArgGetUsersOrders.FIELDNAME_STATUS_SYMBOL,PropertyInfo.STRING_CLASS,wsArg.getStatusSymbol()));
		return performWebServiceMethodCall(request,methodName);
	}
	
	public static String getOrderDetails(MethodArgOrder wsArg){		
		String methodName=WebServiceConfig.METHOD_ORDER_DATA_BY_ID;
		SoapObject request = new SoapObject(WebServiceConfig.NAMESPACE, methodName);
		request.addProperty(setPropertyValue(MethodArgOrder.FIELDNAME_ORDER_ID,PropertyInfo.STRING_CLASS,wsArg.getOrderId()));
		return performWebServiceMethodCall(request,methodName);
	}
	
	private static String performWebServiceMethodCall(SoapObject request, String methodName){
		String resultString="";
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String urlToCcall=Config.URL_1+Config.HOST_ADDRESS+WebServiceConfig.URL_2;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(urlToCcall);
		try {
			androidHttpTransport.call(WebServiceConfig.NAMESPACE+methodName, envelope);
			resultString=envelope.getResponse().toString();
			//Log.d(TAG_ACTIVITY,"RESPONSE: "+resultString);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return resultString;
	}

	private static PropertyInfo setPropertyValue(String propName,
			@SuppressWarnings("rawtypes") Class classType, String propValue) {
		PropertyInfo prop=new PropertyInfo();
		prop.name=propName;
		prop.type=classType;
		prop.setValue(propValue);
		
		return prop;
	}
	
}
