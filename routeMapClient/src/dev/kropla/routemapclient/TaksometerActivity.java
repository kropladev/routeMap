package dev.kropla.routemapclient;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import dev.kropla.connection.servlet.ControllerServlets;
import dev.kropla.connection.webservice.IWebServiceUtils;
import dev.kropla.connection.webservice.WebServiceConnect;
import dev.kropla.connection.webservice.WebServiceUtils;
import dev.kropla.connection.webservice.pojo.MethodArgOrder;
import dev.kropla.connection.webservice.pojo.MethodArgRoute;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.routemapclient.R;
import dev.kropla.utils.Config;
import dev.kropla.utils.location.MyLocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class TaksometerActivity extends Activity  {
	private static final String ACTIVITY_TAG = "TaksometerActivity";
	private Spinner spinner;
	private TextView tvOrderName,tvOrderId,tvOrderAdress,tvOrderTime,tvOrderCustomerData1,tvOrderCustomerData2,tvDistance,tvTimer;
	private Intent intent;
	private IWebServiceUtils wsUtils;
	private OrdersAndroidObject orderObj = null;
	ArrayAdapter<String> spinnerAdapter;
	ArrayList<String> list;
	private ControllerServlets servletContr;
	private int lastSpinnerPosition=0;
	private Vibrator myVibrator;
	private boolean selectedFlag=false;
	Format formatter ;
	Time now;
	public Calendar timerValue;
	private PeriodicDisplayUpdate mPeriodicUpdate;
	private Handler mHandler;
	public static int timeInSecs=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_taksometer);
		
		initializeVariablesAndParameters();
		fetchVariablesWithWidgets();
		fetchStatusSpinnerAdapterValues();//glowna logika activity - akcja na zmiane wartosci spinnera
		
		setOrderIdValue(); //pobierz id zamowienia z obiektu intent wywolujacego to activity 
		pullOrderDataFromWs(); //pobierz szczegolowe dane zamowienia
		changeValuesOnView();
	}
	
	@SuppressLint("SimpleDateFormat")
	private void initializeVariablesAndParameters() {
		mHandler = new Handler();
		orderObj= new OrdersAndroidObject();
		intent = getIntent();
		wsUtils= new WebServiceUtils();
		servletContr= new ControllerServlets();
		list = new ArrayList<String>();
		formatter= new SimpleDateFormat("HH:mm:ss");
		now = new Time();
		now.setToNow();
	}
	
	private void fetchVariablesWithWidgets() {
		spinner = (Spinner) findViewById(R.id.spinner1);
		tvOrderName=(TextView) findViewById(R.id.textViewTxmetrOrderName);
		tvOrderId=(TextView) findViewById(R.id.textViewTxmetrOrderId);
		tvOrderAdress=(TextView) findViewById(R.id.textViewTxmetrOrderAddressFrom);
		tvOrderTime=(TextView) findViewById(R.id.textViewTxmetrTime);
		tvOrderCustomerData1=(TextView) findViewById(R.id.TextViewTxmetrCustomerData1);
		tvOrderCustomerData2=(TextView) findViewById(R.id.TextViewTxmetrCustomerData2);
		tvDistance=(TextView) findViewById(R.id.TextViewtxmetrOrderDistance);
		tvTimer=(TextView) findViewById(R.id.TextViewTxmetrOrderTimer);	
		myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	private void fetchStatusSpinnerAdapterValues(){
		// Create an ArrayAdapter using the string array and a default spinner layout
		list.addAll(Arrays.asList(this.getResources().getStringArray(R.array.status_array)));
		Log.d(ACTIVITY_TAG,"spinnerList: "+list.toString());
		spinnerAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		// Specify the layout to use when the list of choices appears
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		
		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,
					long id){
				if (selectedFlag){
					myVibrator.vibrate(30);
					Log.d(ACTIVITY_TAG,"onItemSelected VIEW ID:"+view.getId());
					Log.d(ACTIVITY_TAG,"onItemSelected parent.Id:"+parent.getId());
					Log.d(ACTIVITY_TAG,"onItemSelected R.id.spinner1:"+R.id.spinner1);
					String selectedItem=parent.getSelectedItem().toString();
					int statusId=0;
					if (parent.getId()==R.id.spinner1){
						Log.d(ACTIVITY_TAG, "onItemSelected pos:"+pos+"|id:"+id);
						if(orderObj!=null && orderObj.getOrderId()!=-1){
							mPeriodicUpdate = null;
							//Akcja na zmiane na status rozpoczete i zakonczone/odrzucoen
							MethodArgOrder argWS2= new MethodArgOrder();
							argWS2.setOrderId(Integer.toString(orderObj.getOrderId()));
							if(selectedItem.equals(Config.ORDER_STATUS_1)){
								statusId=7;
							}
							if(selectedItem.equals(Config.ORDER_STATUS_2)||selectedItem.equals(Config.ORDER_STATUS_3)){
								Log.d(ACTIVITY_TAG, "1selectedItem:"+selectedItem);
								removeItemFromSpinner(Config.ORDER_STATUS_1);
								removeItemFromSpinner(Config.ORDER_STATUS_6);
								addItemToSpinner(Config.ORDER_STATUS_4);
								addItemToSpinner(Config.ORDER_STATUS_6);
								if(selectedItem.equals(Config.ORDER_STATUS_2)){
									statusId=12;
								}else{
									statusId=13;
								}
								argWS2.setStatusId(statusId);
							}
							else if(selectedItem.equals(Config.ORDER_STATUS_4)){
								Log.d(ACTIVITY_TAG, "2selectedItem:"+selectedItem);
								//w trakcie: rozpocznij przesylanie pozycji do tablicy route
								//rozpocznij update pol: czas i dystans
								//web service przygotowany - wykorzystac klase MethodArgInsertNewRoute i zmienic w LocalizationListenerze
								//inserting new route connected with order
								removeItemFromSpinner(Config.ORDER_STATUS_6);
								removeItemFromSpinner(Config.ORDER_STATUS_2);
								removeItemFromSpinner(Config.ORDER_STATUS_3);
								addItemToSpinner(Config.ORDER_STATUS_5);
								addItemToSpinner(Config.ORDER_STATUS_6);
								
								MethodArgRoute argWS=new MethodArgRoute();
								argWS.setOrderId(orderObj.getOrderId());
								Config.actualRouteId=Integer.parseInt(WebServiceConnect.insertNewRoute(argWS));	
								argWS2.setStatusId(8);
								//start saving coordinates
								Config.updateRoutesPosition=true;
								lastSpinnerPosition=pos;
								startTikTak();
							}else if(selectedItem.equals(Config.ORDER_STATUS_5)){
								Log.d(ACTIVITY_TAG, "3selectedItem:"+selectedItem);
								//zakonczone: skoncz przesylanie pozycji do tablicy
								//stop saving coordinates
								if(Config.actualRouteId!=0){
									removeItemFromSpinner(Config.ORDER_STATUS_4);
									MethodArgRoute argWS=new MethodArgRoute();
									argWS.setOrderId(orderObj.getOrderId());
									argWS.setRouteId(Config.actualRouteId);
									argWS.setStatus("finished");
									argWS.setLength(MyLocationListener.dTraveled);
									String result=WebServiceConnect.updateRoute(argWS);
									Log.d(ACTIVITY_TAG,"result updateRoute:"+result);
									argWS2.setStatusId(9);
									lastSpinnerPosition=pos;
								}else{
									setActualSpinnerValue(lastSpinnerPosition);
								}
								Config.updateRoutesPosition=false;
								Config.actualRouteId=0;
							}else if(selectedItem.equals(Config.ORDER_STATUS_6)){
								Log.d(ACTIVITY_TAG, "4selectedItem:"+selectedItem);
								servletContr.confirmOrder(orderObj.getOrderId(), Config.userAccount.getVehicleId(), 2);
								if(Config.actualRouteId!=0){
									MethodArgRoute argWS=new MethodArgRoute();
									argWS.setOrderId(orderObj.getOrderId());
									argWS.setRouteId(Config.actualRouteId);
									argWS.setStatus("rejected");
									argWS.setLength(MyLocationListener.dTraveled);
									String result=WebServiceConnect.updateRoute(argWS);
									Log.d(ACTIVITY_TAG,"result updateRoute:"+result);
									argWS2.setStatusId(10);
									lastSpinnerPosition=pos;
								}else{
									setActualSpinnerValue(lastSpinnerPosition);
								}
								//wyslij status odrzucenia zlecenia i zamknij zlecenie
								//stop saving coordinates if was switched on
								Config.updateRoutesPosition=false;
								Config.actualRouteId=0;
							}else {
								Log.d(ACTIVITY_TAG, "5selectedItem:"+selectedItem);
								if(Config.actualRouteId!=0){
									MethodArgRoute argWS=new MethodArgRoute();
									argWS.setOrderId(orderObj.getOrderId());
									argWS.setRouteId(Config.actualRouteId);
									argWS.setStatus("admin");
									argWS.setLength(MyLocationListener.dTraveled);
									String result=WebServiceConnect.updateRoute(argWS);
									Log.d(ACTIVITY_TAG,"result updateRoute:"+result);
									Log.d(ACTIVITY_TAG,"pos value:"+pos);
								}
						
								Log.d(ACTIVITY_TAG,"STATUS_ID:"+statusId);
								lastSpinnerPosition=pos;
								argWS2.setStatusId(statusId);
								//stop saving coordinates if was switched on
								Config.updateRoutesPosition=false;
								Config.actualRouteId=0;
							}
							if (argWS2.getStatusId()!=0){
								Log.d(ACTIVITY_TAG, "6argWS2.getStatusId():"+argWS2.getStatusId());
								WebServiceConnect.changeOrderStatus(argWS2);
							}
						}
					}
					spinnerAdapter.notifyDataSetChanged();
					setActualSpinnerValue(spinnerAdapter.getPosition(selectedItem));
				}else{selectedFlag=true;}
			}
				
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}	
		});
	}
	
	private void setOrderIdValue() {
		orderObj.setOrderId(intent.getIntExtra("orderId", -1));
	}

	private void pullOrderDataFromWs() {
		orderObj=wsUtils.getOrderValues(orderObj);
	}
	
	private void changeValuesOnView() {
		tvOrderName.setText(this.orderObj.getOrderName());
		tvOrderId.setText(Integer.toString(this.orderObj.getOrderId()));
		tvOrderAdress.setText(this.orderObj.getOrderStreet_from());
		tvOrderTime.setText(Config.convertTime(this.orderObj.getTimeOrderStart(),"dd-MM-yyyy"));
		tvOrderCustomerData1.setText(this.orderObj.getCustName());
		tvOrderCustomerData2.setText(this.orderObj.getCustPhone());
		tvTimer.setText("00:00:00");
		tvDistance.setText("0.0 km");
		//tvDistance.setText(text);
		//tvTimer.setText(text);
		setActualSpinnerValue();	
	}
	
//////////////////////////////////////////////////////////////////////////	
	
	private void setActualSpinnerValue() {
		String orderStatus=this.orderObj.getOrderStatusDesc();
		//Log.d(ACTIVITY_TAG,"SPINNER order status:|"+orderStatus+"|");
		//int licznik= spinnerAdapter.getCount();
		//for(int i=0;i<licznik; i++){Log.d(ACTIVITY_TAG, "elem["+i+"]:|"+spinnerAdapter.getItem(i)+"|");}
		int spinPos= spinnerAdapter.getPosition(orderStatus);
		spinner.setSelection(spinPos);
	}
	
	private void setActualSpinnerValue(int position) {
		spinner.setSelection(position);
		
	}
	private void removeItemFromSpinner(String item){
		Log.d(ACTIVITY_TAG, "PoSiTioN1 item: "+item);
		int licznik= spinnerAdapter.getCount();
		for(int i=0;i<licznik; i++){
			Log.d(ACTIVITY_TAG, "elem["+i+"]:|"+spinnerAdapter.getItem(i)+"|");
		}
		
		int positionT=spinnerAdapter.getPosition(item);
		if (positionT>=0){
			spinnerAdapter.remove(item);
		}else {
			Log.d(ACTIVITY_TAG, "PoSiTioN4: "+positionT);
		}
	}
	private void addItemToSpinner(String item){
		int positionT=spinnerAdapter.getPosition(item);
		if (positionT==-1){
			spinnerAdapter.add(item);
			spinnerAdapter.notifyDataSetChanged();
		}else {
			Log.d(ACTIVITY_TAG, "PoSiTioN: "+positionT);
		}
	}
	
	@Override
	protected void onDestroy() {
		mPeriodicUpdate=null;
		super.onDestroy();
	}
	
	private void startTikTak(){
		resetTimer();
		mPeriodicUpdate = new PeriodicDisplayUpdate();
		mPeriodicUpdate.run();		
	}
	
	private String formatTimeS() {
		return formatter.format(timerValue.getTime());
	}
	private void incrementTimerBySecs(int seconds) {
		timerValue.add(Calendar.SECOND, seconds);
	}
	private void resetTimer() {
		timerValue = Calendar.getInstance();
		timerValue.set(2013, 9, 15, 0, 0, 0);
	}
	private class PeriodicDisplayUpdate implements Runnable 
	{
		public void run() 
		{
			if (mPeriodicUpdate == null){
				Log.d("PeriodicDisplayUpdate", "mPeriodicUpdate == null");
				return;
			}
			mHandler.postDelayed(mPeriodicUpdate, 2000 /* ms */);
				Log.d("PeriodicDisplayUpdate", "ExerciseTools.isNOTPaused");
				incrementTimerBySecs(1);
				TaksometerActivity.timeInSecs++;
				tvDistance.setText(MyLocationListener.dTraveledWithFormat);
				tvTimer.setText(formatTimeS());
		}
	}
	
}
