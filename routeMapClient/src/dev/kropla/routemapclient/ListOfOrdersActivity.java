package dev.kropla.routemapclient;

import static dev.kropla.routemapclient.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static dev.kropla.routemapclient.CommonUtilities.SENDER_ID;
//import static dev.kropla.routemapclient.CommonUtilities.SERVER_URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;

import dev.kropla.connection.gcm.WakeLocker;
import dev.kropla.connection.servlet.MainControllerConnection;
import dev.kropla.connection.servlet.utils.ServletConnectException;
import dev.kropla.connection.servlet.utils.ServletParams;
import dev.kropla.connection.webservice.WebServiceConnect;
import dev.kropla.connection.webservice.pojo.MethodArgChangeStatus;
import dev.kropla.connection.webservice.pojo.MethodArgGetUsersOrders;
import dev.kropla.dto.UserAccount;
import dev.kropla.routemapclient.R;
import dev.kropla.utils.Config;
import dev.kropla.utils.location.MyLocationListener;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfOrdersActivity extends Activity implements
		OnItemClickListener {
	private static final String ACTIVITY_TAG = "ListOfOrdersActivity";
	private ListView ordersList;
	public static TextView mDisplay, loginMsg;
	AsyncTask<Void, Void, Void> mRegisterTask;
	List<Map<String, String>> data;
	SimpleAdapter mAdapter;
	LocationManager mlocManager;
	LocationListener mlocListener1, mlocListener2;
	private Vibrator myVibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_of_orders);
		fetchVariablesWithWidgets();
		collectUserAccountData();
		initialCheckOfGCMService();
		callGCMSericeConfigs();
		initializeVariablesAndParameters();
		refreshAdapterDataFromDb();
		prepareListView();
		startDeviceLocalizationTracking();
	}

	private void fetchVariablesWithWidgets() {
		Log.d(ACTIVITY_TAG, "fetchVariablesWithWidgets");
		ordersList = (ListView) findViewById(R.id.listViewUserOrders);
		mDisplay = (TextView) findViewById(R.id.lblMessage);
		loginMsg = (TextView) findViewById(R.id.LoginMsg);
		myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void collectUserAccountData() {
		//System.out.println("UserAccount INFO: " + Config.userAccount.toString());
		ServletParams sParams = new ServletParams("accountId",(Object) Config.userAccount.getDriversId(), "int");
		List<ServletParams> lParams = new ArrayList<ServletParams>();
		lParams.add(sParams);
		MainControllerConnection mController = new MainControllerConnection();
		try {
			Config.userAccount = (UserAccount) mController.postServlet("userAccountData.htm", lParams, UserAccount.class);
		} catch (ServletConnectException sce) {
			setDialogDestroyingActivity(Config.MSG_APP_ERROR_SERVLET_TITLE,Config.MSG_APP_ERROR_SERVLET_MESSAGE, Config.MSG_OK, null).show();
		}
		Log.d(ACTIVITY_TAG, "JSONOBJECT: " + Config.userAccount.toString());
	}

	private void initialCheckOfGCMService() {
		Log.d(ACTIVITY_TAG, "initialCheckOfGCMService");
		//checkNotNull(SERVER_URL, "SERVER_URL");
		checkNotNull(SENDER_ID, "SENDER_ID");
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);
		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);
	}

	private void callGCMSericeConfigs() {
		Log.d(ACTIVITY_TAG, "callGCMSericeConfigs");
		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
		// pobierz REGID z pamieci - SharedPreferences
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			// Automatically registers application on startup.
			Log.d(ACTIVITY_TAG, "call for REQ_ID");
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			CommonUtilities.apiRegKey = regId;
			Log.d(ACTIVITY_TAG, "REG_ID:" + regId);
			// Device is already registered on GCM, needs to check if it is
			// registered on our server as well.
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				Log.d(ACTIVITY_TAG, "Device is registered on Int Server App");
			} else {
				Log.d(ACTIVITY_TAG, "Device not registered on Int Server App");
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {
					private ServletConnectException sce = null;

					@Override
					protected Void doInBackground(Void... params) {
						boolean registered = false;
						try {
							registered = ServerUtilities.register(context,regId, Config.userAccount);
							Config.userAccount.setRegId(regId);
						} catch (ServletConnectException e) {
							Log.d(ACTIVITY_TAG,"ServletConnectException: "+ e.getMessageAdd() + "|"+ e.getUrl());
							sce = e;
						}
						// At this point all attempts to register with the app
						// server failed, so we need to unregister the device
						// from GCM - the app will try to register again when
						// it is restarted. Note that GCM will send an
						// unregistered callback upon completion, but
						// GCMIntentService.onUnregistered() will ignore it.
						if (!registered) {
							Log.d("REGISTER","UNREGISTER_PROBLEM ListOfOrder.callGCMSericeConfigs()");
							GCMRegistrar.unregister(context);
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
						if (sce != null) {
							Log.e(ACTIVITY_TAG,"ServletConnectException: runing dialog");
							// stopDeviceLocalizationTracking();
							setDialogDestroyingActivity(
									Config.MSG_APP_ERROR_SERVLET_TITLE,
									Config.MSG_APP_ERROR_SERVLET_TITLE + " ["+ sce.getUrl() + "]",
									Config.MSG_OK, null).show();
						}
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	private void initializeVariablesAndParameters() {
		Log.d(ACTIVITY_TAG, "initializeVariablesAndParameters");
		data = new ArrayList<Map<String, String>>();
		loginMsg.setText(Config.LABEL_LOGGED_NAME+ Config.userAccount.getLogin());
	}

	private void refreshAdapterDataFromDb() {
		data.clear();
		JSONArray orderList = parseJsonResult();
		int arrayLength = orderList.length();
		for (int i = 0; i < arrayLength; i++) {
			JSONObject jobject2;
			try {
				jobject2 = orderList.getJSONObject(i);
				Map<String, String> datum = new HashMap<String, String>();
				datum.put("id", "ID:" + jobject2.get("orderId").toString());
				datum.put("hour","godz.: " + (String) jobject2.get("roaTimeOrderStart"));
				datum.put("custname","klient: " + (String) jobject2.get("custName"));
				datum.put("startAddress","Adres: " + (String) jobject2.get("custStreet_from"));
				datum.put("orderId", jobject2.get("orderId").toString());
				datum.put("orderStatus", jobject2.getString("status"));
				datum.put("orderStatusId", jobject2.get("orderStatusId").toString());
				data.add(datum);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (java.lang.ClassCastException castExc) {
				Toast.makeText(getApplicationContext(),Config.MSG_PREPARE_LIST_ERROR, Toast.LENGTH_LONG).show();
			}
		}
	}

	private void prepareListView() {
		Log.d(ACTIVITY_TAG, "prepareListView");
		mAdapter = new CustomAdapter(this, data, R.layout.roads_list_view_row,
				new String[] { "id", "hour", "custname", "startAddress" },
				new int[] { R.id.orderId, R.id.orderHour, R.id.orderClientName,R.id.orderStartAddress });
		this.ordersList.setAdapter(mAdapter);
		this.ordersList.setOnItemClickListener(this);

	}

	private void startDeviceLocalizationTracking() {
		Log.d(ACTIVITY_TAG, "startDeviceLocalizationTracking");
		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mlocListener1 = new MyLocationListener(this);
		mlocListener2 = new MyLocationListener(this);
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,Config.locationPeriodMinutes, Config.locationChnageDistance,mlocListener1);
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,Config.locationPeriodMinutes, Config.locationChnageDistance,mlocListener2);
		changeVehicleStatus(Config.STATUS_LOGGED);
	}

	// ////////////////////////////////////////////////////////

	protected void stopDeviceLocalizationTracking() {
		Log.d(ACTIVITY_TAG, "stopDeviceLocalizationTracking");
		// zatrzymaj tracking pozycji dla wszystkich listenerow/nasluchow
		mlocManager.removeUpdates(mlocListener1);
		mlocManager.removeUpdates(mlocListener2);
		// wyloguj usera
		changeVehicleStatus(Config.STATUS_LOGOFF);
	}

	private void changeVehicleStatus(String statusLogged) {
		Log.d(ACTIVITY_TAG, "changeVehicleStatus");
		MethodArgChangeStatus wsArg = new MethodArgChangeStatus();
		wsArg.setUserAccountLogin(Config.userAccount.getLogin());
		wsArg.setStatus(statusLogged);
		Log.d(ACTIVITY_TAG, " Data send to WS:" + wsArg.toString());

		WebServiceConnect.changeVehicleStatus(wsArg);
	}

	private class CustomAdapter extends SimpleAdapter {
		@SuppressWarnings("unused")
		int resourceId;

		public CustomAdapter(Context context,List<? extends Map<String, ?>> data, int resource,String[] from, int[] to) {
			super(context, data, resource, from, to);
			resourceId = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.roads_list_view_row, parent,false);
			}

			TextView orderHour = (TextView) row.findViewById(R.id.orderHour);
			TextView orderId = (TextView) row.findViewById(R.id.orderId);
			TextView orderStartAddress = (TextView) row.findViewById(R.id.orderStartAddress);
			TextView orderClientName = (TextView) row.findViewById(R.id.orderClientName);
			ImageView imgView = (ImageView) row.findViewById(R.id.imageViewOrdersList);

			orderHour.setText(data.get(position).get("hour"));
			orderId.setText(data.get(position).get("id"));
			orderStartAddress.setText(data.get(position).get("startAddress"));
			orderClientName.setText(data.get(position).get("custname"));

			int orderStatusId = Integer.parseInt(data.get(position).get("orderStatusId"));
			int btnBackResourceId = R.drawable.btn_blue;
			switch (orderStatusId) {
				case 7:btnBackResourceId = R.drawable.btn_blue;break;
				case 8:btnBackResourceId = R.drawable.btn_red;break;
				case 12:btnBackResourceId = R.drawable.btn_green;break;
				case 13:btnBackResourceId = R.drawable.btn_yellow;break;
				default:btnBackResourceId = R.drawable.btn_grey;break;
			}
			imgView.setBackgroundResource(btnBackResourceId);
			return row;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		myVibrator.vibrate(30);
		Log.d(ACTIVITY_TAG, "onItemClick");
		@SuppressWarnings("unchecked")
		final Map<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
		Intent intent = new Intent(this, TaksometerActivity.class);
		String temp = item.get("orderId");
		Log.d(ACTIVITY_TAG, "OrderId:" + temp);
		intent.putExtra("orderId", Integer.parseInt(item.get("orderId")));

		startActivity(intent);
	}

	@Override
	protected void onResume() {
		Log.d(ACTIVITY_TAG, "ON RESUME");
		super.onResume();
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config,name));
		}
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(ACTIVITY_TAG, "onReceive");
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			WakeLocker.release();
		}
	};

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Log.d(ACTIVITY_TAG, "onWindowFocusChanged");
		refreshAdapterDataFromDb();
		mAdapter.notifyDataSetChanged();
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public void onBackPressed() {
		Log.d(ACTIVITY_TAG, "onBackPressed");
		// Ask the user if they want to quit
		setDialogDestroyingActivity(Config.MSG_LOGOFF_TITLE,Config.MSG_LOGOFF_MESSAGE, Config.MSG_YES, Config.MSG_NO).show();
	}

	public Builder setDialogDestroyingActivity(String title, String message,
			String positiveButton, String negativeButton) {
		if (negativeButton == null) {
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton(positiveButton,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									stopDeviceLocalizationTracking();
									ListOfOrdersActivity.this.finish();
								}
							});
		} else {
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton(positiveButton,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									stopDeviceLocalizationTracking();
									ListOfOrdersActivity.this.finish();
								}
							})
					.setNegativeButton(negativeButton,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									ListOfOrdersActivity.this.finish();
								}
							});
		}
	}

	@Override
	protected void onDestroy() {
		Log.d(ACTIVITY_TAG, "onDestroy");
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		//unregisterReceiver(mHandleMessageReceiver);
		//GCMRegistrar.setRegisteredOnServer(this, false);
		//GCMRegistrar.onDestroy(this);

		mlocManager.removeUpdates(mlocListener1);
		Log.d(ACTIVITY_TAG, "  ON_DESTROY");
		super.onDestroy();
	}
	
	private JSONArray parseJsonResult() {
		Log.d(ACTIVITY_TAG, "parseJsonResult");
		try {
			JSONObject jObject = new JSONObject(callWebServiceForData());
			return jObject.getJSONArray("orders");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String callWebServiceForData() {
		Log.d(ACTIVITY_TAG, "callWebServiceForData");
		MethodArgGetUsersOrders argWS = new MethodArgGetUsersOrders();
		argWS.setUserLogin(Config.userAccount.getLogin());
		argWS.setStatusSymbol(Config.DRIVERS_ORDERS_LIST_TAG);
		Log.d(ACTIVITY_TAG, " Data send to WS:" + argWS.toString());
		return WebServiceConnect.getLastOrdersForUser(argWS);
	}
}
