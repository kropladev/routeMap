package dev.kropla.connection.webservice;

import com.google.gson.Gson;

import android.util.Log;
import dev.kropla.connection.webservice.pojo.MethodArgOrder;
import dev.kropla.dto.OrdersAndroidObject;

public class WebServiceUtils implements IWebServiceUtils{
	private static final String ACTIVITY_TAG = "WebServiceUtils";

	public OrdersAndroidObject getOrderValues(OrdersAndroidObject argOrderObj) {
		Log.d(ACTIVITY_TAG,"OrdersAndroid: start");
		//int orderIdInt=argOrderObj.getOrderId();
		//String orderIdParam = Integer.toString(orderIdInt);
		//Log.d(ACTIVITY_TAG,"OrdersAndroid: orderIdInt:"+orderIdInt+ " |orderIdParam:"+orderIdParam);
		MethodArgOrder argWS= new MethodArgOrder();
		argWS.setOrderId(Integer.toString(argOrderObj.getOrderId()));
		//Log.d(ACTIVITY_TAG, " Data send to WS:" + argWS.getOrderId());
		String result= WebServiceConnect.getOrderDetails(argWS);
		Log.i(ACTIVITY_TAG, "RESPONSE_STATUS:"+result);
		
		argOrderObj= parseFromJson(result,OrdersAndroidObject.class);	
		Log.d(ACTIVITY_TAG, "JSONOBJECT: "+argOrderObj);
		return argOrderObj;
	}
	
	private OrdersAndroidObject parseFromJson(String result,
			Class<OrdersAndroidObject> class1) {
		Gson gson= new Gson();
		return gson.fromJson(result, class1);
	}
	
/*	private String parseToJson(OrdersAndroidObject result,
			Class<OrdersAndroidObject> class1) {
		Gson gson= new Gson();
		return gson.toJson(result, class1);
	}*/
	
	
	
}
