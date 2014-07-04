package dev.kropla.connection.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.content.Context;
import android.util.Log;

import dev.kropla.connection.servlet.utils.ServletConfig;
import dev.kropla.connection.servlet.utils.ServletConnectException;
import dev.kropla.connection.servlet.utils.ServletParams;
import dev.kropla.utils.Config;

public class ControllerServlets {
private static final String TAG = "controllerConnect";
	 public static void userAccountInfo(final Context context,  int accountId) {
	        Log.i(TAG, "getting user account Info");
	        String serverUrl = Config.URL_1+Config.HOST_ADDRESS +ServletConfig.URL_2+ "userAccountData.htm";
	        
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpPost httpPost = new HttpPost(serverUrl);
	        
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add(new BasicNameValuePair("accountId",Integer.toString(Config.userAccount.getDriversId()) ));
	        try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpClient.execute(httpPost);
				 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			      String line = "";
			      while ((line = rd.readLine()) != null) {
			        System.out.println(line);
			      }
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}   
	 }
		/**
		 * 
		 * @param orderId
		 * @param vehicleId
		 * @param status (int) 1- accept; 2- reject
		 * @return
		 */
		public String confirmOrder(int orderId, int vehicleId, int status) {
			
			Log.d(TAG,"confirmOrder: orderId:"+orderId+ " |vehicleId:"+vehicleId);
			String retValue="";
			List<ServletParams> lParams = new ArrayList<ServletParams>();
			lParams.add(new ServletParams("orderId", (Object)orderId, "int"));
			lParams.add(new ServletParams("vehicleId", (Object)vehicleId, "int"));
			lParams.add(new ServletParams("status", (Object)status, "int"));
			MainControllerConnection mController= new MainControllerConnection();
			
			try{
				retValue=(String)mController.postServlet("confirmOrder.htm",lParams, String.class );
				Log.d(TAG,"confirmOrder result from server:"+retValue);
				return retValue;
			}catch(ServletConnectException sce){
				Log.e(TAG,sce.getMessage());
			}
			return null;
		}
}
