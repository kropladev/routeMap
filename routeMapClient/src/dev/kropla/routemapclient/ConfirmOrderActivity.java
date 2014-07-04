package dev.kropla.routemapclient;

import java.util.List;

import dev.kropla.connection.servlet.ControllerServlets;
import dev.kropla.connection.webservice.IWebServiceUtils;
import dev.kropla.connection.webservice.WebServiceConnect;
import dev.kropla.connection.webservice.WebServiceUtils;
import dev.kropla.connection.webservice.pojo.MethodArgOrder;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.dto.UserAccount;
import dev.kropla.routemapclient.R;
import dev.kropla.utils.Config;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmOrderActivity extends Activity implements OnClickListener {
	//2 buttony + 5 TextView
	private TextView textOrderName, textOrderAddressFrom, textOrderTime, textOrderCustomer, textOrderAddressGoal, textOrderType, textOrderId;
	private Button buttonAccept, buttonReject;
	private OrdersAndroidObject orderObj = null;
	private static final String ACTIVITY_TAG="ConfirmOrderActivity";
	private Intent intent;
	private int vehicleId=-1;
	private IWebServiceUtils wsUtils;
	private ControllerServlets servletContr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(ACTIVITY_TAG,"ON CREATE ConfirmOrderActivity");
		setContentView(R.layout.activity_confirm_order);
		initializedVariables();
		fetchVariables();
		orderObj=wsUtils.getOrderValues(orderObj);
		changeValuesOnView(orderObj);
	}
	private void changeValuesOnView(OrdersAndroidObject argOrder) {
		Log.d(ACTIVITY_TAG, "changeValuesOnView: ORDER:"+argOrder.toString());
		textOrderAddressFrom.setText(argOrder.getOrderStreet_from());
		textOrderAddressGoal.setText(argOrder.getOrderStreet_goal());
		textOrderCustomer.setText(argOrder.getCustName());
		textOrderName.setText(argOrder.getOrderName());
		textOrderTime.setText(argOrder.getTimeOrderStart());
		textOrderType.setText(argOrder.getRtName());
		textOrderId.setText("id:"+Integer.toString(argOrder.getOrderId()));
		Log.d(ACTIVITY_TAG, "OrderId: "+textOrderId.getText());
		}

	private void initializedVariables() {
		orderObj = new OrdersAndroidObject();
		intent = getIntent();
		wsUtils= new WebServiceUtils();
		servletContr= new ControllerServlets();
	}
	
	private void fetchVariables() {
		textOrderAddressFrom = (TextView) findViewById(R.id.TextViewOrderPlaceValue);
		textOrderAddressGoal= (TextView) findViewById(R.id.TextViewOrderGoalValue);
		textOrderCustomer = (TextView) findViewById(R.id.TextViewOrderCustomerValue);
		textOrderName = (TextView) findViewById(R.id.textViewOrderName);
		textOrderTime = (TextView) findViewById(R.id.TextViewOrderTimeValue);
		textOrderType = (TextView) findViewById(R.id.textViewOrderType);
		buttonAccept = (Button) findViewById(R.id.buttonOrderConfirm);
		buttonReject = (Button) findViewById(R.id.buttonOrderReject);
		textOrderId = (TextView) findViewById(R.id.textViewOrderId);
		orderObj.setOrderId(intent.getIntExtra("orderId", -1));
		Log.d(ACTIVITY_TAG,"INTENT.orderId"+intent.getIntExtra("orderId", -1));
		vehicleId=intent.getIntExtra("vehicleId", -2);
		Log.d(ACTIVITY_TAG,"INTENT.vehicleId"+intent.getIntExtra("vehicleId", -1));
		if (Config.userAccount==null){
			Config.userAccount = new UserAccount();
		}
		Config.userAccount.setVehicleId(vehicleId);
		buttonAccept.setOnClickListener(this);
		buttonReject.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.buttonOrderConfirm) {
			//String result=confirmOrder(orderObj.getOrderId(), this.vehicleId, 1);
			/*String result=*/servletContr.confirmOrder(orderObj.getOrderId(), this.vehicleId, 1);
			MethodArgOrder argWs= new MethodArgOrder();
			argWs.setOrderId(Integer.toString(orderObj.getOrderId()));
			argWs.setStatusId(7);
			WebServiceConnect.changeOrderStatus(argWs);
			//potwierdzenie wyslane do systemu, zamowienie dodane do listy zamowiec wyswietlanych przez kierowce
		}else if (arg0.getId() == R.id.buttonOrderReject){
			//wyslanie informacji o odrzuceniu zgloszenia i zamkniecie activity
			//String result=confirmOrder(orderObj.getOrderId(), this.vehicleId, 2);
			/*String result=*/servletContr.confirmOrder(orderObj.getOrderId(), this.vehicleId, 2);
		}
		this.finish();
		Intent intent=null;
		if (isActivityActive(this,"dev.kropla.routemapclient",ListOfOrdersActivity.class)){
			intent = new Intent(this, ListOfOrdersActivity.class);
		}else {
			intent = new Intent(this, MainActivity.class);
		}
		intent.putExtra("vehicleId", this.vehicleId);
		startActivity(intent);
	}
	
	
	private boolean isActivityActive(Context context, String packageName, Class className) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent();
	    intent.setClassName(packageName, className.getName());
	    Log.d(ACTIVITY_TAG, "isActivityAvailable packageName:"+packageName+" |className:"+className.getName());
	    List list = packageManager.queryIntentActivities(intent,
	                    PackageManager.MATCH_DEFAULT_ONLY);
	    Log.d(ACTIVITY_TAG, "isActivityAvailable result:"+(list.size() > 0));
	    return list.size() > 0;
	}
	
	/**
	 * 
	 * @param orderId
	 * @param vehicleId
	 * @param status (int) 1- accept; 2- reject
	 * @return
	 */
	/*
	private String confirmOrder(int orderId, int vehicleId, int status) {
		
		Log.d(ACTIVITY_TAG,"confirmOrder: orderId:"+orderId+ " |vehicleId:"+vehicleId);
		String retValue="";
		List<ServletParams> lParams = new ArrayList<ServletParams>();
		lParams.add(new ServletParams("orderId", (Object)orderId, "int"));
		lParams.add(new ServletParams("vehicleId", (Object)vehicleId, "int"));
		lParams.add(new ServletParams("status", (Object)status, "int"));
		MainControllerConnection mController= new MainControllerConnection();
		
		try{
			retValue=(String)mController.postServlet("confirmOrder.htm",lParams, String.class );
			Log.d(ACTIVITY_TAG,"confirmOrder result from server:"+retValue);
			return retValue;
		}catch(ServletConnectException sce){
			Log.e(ACTIVITY_TAG,sce.getMessage());
		}
		return null;
	}*/
}
