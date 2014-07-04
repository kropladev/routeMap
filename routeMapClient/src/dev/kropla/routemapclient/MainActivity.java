package dev.kropla.routemapclient;

import dev.kropla.connection.webservice.WebServiceConnect;
import dev.kropla.connection.webservice.pojo.MethodArgLogin;
import dev.kropla.dto.UserAccount;
import dev.kropla.routemapclient.R;
import dev.kropla.utils.Config;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private static final String ACTIVITY_TAG = "MainActivityTag";
	private Button registerButton, loginButton;
	public static EditText loginInput;
	private Vibrator myVibrator;
	AsyncTask<Void, Void, Void> mLoginTask;
	ProgressDialog pdialog;
	String resStatus;
	Intent intent;
	Context mcontext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// wylaczenie gornego paska w oknie activity
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		fetchVariablesWithWidgets();
		initializeVariablesAndParameters();
	}

	private void fetchVariablesWithWidgets() {
		registerButton = (Button) findViewById(R.id.buttonRegister);
		loginButton = (Button) findViewById(R.id.buttonLogin);
		loginInput = (EditText) findViewById(R.id.editTextLogin);
		myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void initializeVariablesAndParameters() {
		registerButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		if (Config.userAccount == null){Config.userAccount= new UserAccount();}
		Intent intentParent = getIntent();
		if(intentParent!=null){
			Log.d(ACTIVITY_TAG,"Getting value from parentInent vehicleId:"+intentParent.getIntExtra("vehicleId",0 ));
			Config.userAccount.setVehicleId(intentParent.getIntExtra("vehicleId",0 ));
		}
//		Config.userAccount.setEmail("email");
//		Log.d(ACTIVITY_TAG, Config.userAccount.getEmail());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.buttonRegister) {
			myVibrator.vibrate(30);
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		} else if (arg0.getId() == R.id.buttonLogin) {
			myVibrator.vibrate(30);

			// int resId=WebServiceConnect.logIntoApplicationId(argWS)
			pdialog = ProgressDialog.show(this, "", Config.MSG_LOGIN_IN_PROGRESS, true);
			mcontext = this.getApplicationContext();
			new Thread(new Runnable() {
				@Override
				public void run() {
					MethodArgLogin argWS = new MethodArgLogin();
					//Config.loginName = loginInput.getText().toString();
					//argWS.setUserLogin(Config.loginName);
					Config.userAccount.setLogin(loginInput.getText().toString());
					argWS.setUserLogin(Config.userAccount.getLogin());
					Log.d(ACTIVITY_TAG, " Data send to WS:" + argWS.getUserLogin());
					resStatus = WebServiceConnect.logIntoApplication(argWS);
					Log.i(ACTIVITY_TAG, "RESPONSE_STATUS:"+resStatus);
					//get driverId
					if (resStatus.equals("OK")){
						String driverId=WebServiceConnect.logIntoApplicationId(argWS);
						Log.i(ACTIVITY_TAG, "DRIVER_ID:"+driverId);
						Config.userAccount.setDriversId(Integer.parseInt(driverId));	
					}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							pdialog.dismiss();
							if (resStatus.equals("OK")) {
								
								intent = new Intent(mcontext,
										ListOfOrdersActivity.class);
								startActivity(intent);

								// this.finish();
							} else if (resStatus.equals("USER_NOT_IN_DB")) {
								Toast.makeText(
										mcontext,
										Config.MSG_USER_NOT_REGISTERED,
										Toast.LENGTH_LONG).show();
							} else if (resStatus.equals("ERROR")) {
								Toast.makeText(
										mcontext,
										Config.MSG_CONNECT_ERROR,
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(
										mcontext,
										Config.MSG_UNKNOW_ANSWER,
										Toast.LENGTH_LONG).show();
							}
						}
					});
				}
			}).start();
		}
	}
}