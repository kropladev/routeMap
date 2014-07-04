package dev.kropla.routemapclient;

import dev.kropla.connection.webservice.WebServiceConnect;
import dev.kropla.connection.webservice.pojo.MethodArgRegister;
import dev.kropla.routemapclient.R;
import dev.kropla.utils.Config;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class RegisterActivity extends Activity implements OnClickListener {
     public static final String ACTIVITY_TAG="RegisterActivity";
    // UI elements
    EditText txtLogin,txtEmail,txtFName,txtLName,txtPhone;
    private Vibrator myVibrator; 
    public static int loginUserId=0;
    Button buttonPerformRegister;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //wylaczenie gornego paska w oknie activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_register);
   
        fetchVariablesWithWidgets();
		initializeVariablesAndParameters();
    }

	private void fetchVariablesWithWidgets() {
        txtLName = (EditText) findViewById(R.id.EditTextRegLName);
        txtFName = (EditText) findViewById(R.id.EditTextRegFName);
        txtEmail = (EditText) findViewById(R.id.EditTextRegEmail);
        txtLogin = (EditText) findViewById(R.id.EditTextRegLogin);
        txtPhone = (EditText) findViewById(R.id.EditTextRegPhone);
        buttonPerformRegister= (Button)findViewById(R.id.btnRegister);
        myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
	}

	private void initializeVariablesAndParameters() {
		buttonPerformRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnRegister) {
			myVibrator.vibrate(30); 
			MethodArgRegister argWS=new MethodArgRegister();
					argWS.setUserAccountName(txtLogin.getText().toString());
					argWS.setUserFName(txtFName.getText().toString());
					argWS.setUserLName(txtLName.getText().toString());
					argWS.setUserMailName(txtEmail.getText().toString());
					argWS.setUserPhone(txtPhone.getText().toString());
					argWS.setApiRegKey(CommonUtilities.apiRegKey);
					Log.d(ACTIVITY_TAG," Data send to WS:"+argWS.toString());
					String resStatus=WebServiceConnect.registerNewAccount(argWS);
					if (resStatus.equals("LOGIN_TAKEN")){
						Toast.makeText(getApplicationContext(), Config.MSG_LOGIN_UNAVAILABLE,Toast.LENGTH_SHORT).show();
						loginUserId=-1;
					}
					try{
						loginUserId=Integer.parseInt(resStatus);
					}catch (NumberFormatException nfe){
						Toast.makeText(getApplicationContext(), Config.MSG_REGISTER_ERROR+ resStatus+"]"+nfe.getMessage(),Toast.LENGTH_SHORT).show();
					}catch(NullPointerException npe){
						Toast.makeText(getApplicationContext(), Config.MSG_REGISTER_ERROR+resStatus+"]"+  npe.getMessage(),Toast.LENGTH_SHORT).show();
					}
					
					if(loginUserId>0){
						//loginStatus=true;
						Toast.makeText(getApplicationContext(), Config.MSG_NEW_ACCOUNT_CONFIRMATION+resStatus,Toast.LENGTH_SHORT).show();
						MainActivity.loginInput.setText(txtLogin.getText().toString());
						this.finish();
					}
		}	
	}
}