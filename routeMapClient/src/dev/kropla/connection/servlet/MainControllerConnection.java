package dev.kropla.connection.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.Gson;
import android.util.Log;
import dev.kropla.connection.servlet.utils.Connections;
import dev.kropla.connection.servlet.utils.ServletConfig;
import dev.kropla.connection.servlet.utils.ServletConnectException;
import dev.kropla.connection.servlet.utils.ServletParams;
import dev.kropla.utils.Config;

public class MainControllerConnection implements Connections {
	private static final String TAG = "MAIN_CONTROLLER_IMPLEMENTATION";
	public String serverUrl = Config.URL_1 + Config.HOST_ADDRESS
			+ ServletConfig.URL_2;
	public HttpClient httpClient = new DefaultHttpClient();
	public HttpPost httpPost;

	@SuppressWarnings("unchecked")
	@Override
	public Object postServlet(String servletContextName,
			List<ServletParams> params, @SuppressWarnings("rawtypes") Class objectClass)
			throws ServletConnectException {
		Log.d(TAG, "postServlet start");
		httpPost = new HttpPost(serverUrl + servletContextName);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		// UserAccount userAcc=null;
		for (ServletParams singleParam : params) {
			String paramValue = "";
			// cast value from object based on type
			if (singleParam.getParameterType().equals("int")) {
				paramValue = Integer.toString((Integer) singleParam
						.getParameterValue());
			} else if (singleParam.getParameterType().equals("string")) {
				paramValue = (String) singleParam.getParameterValue();
			}
			// add parameter value to List
			nameValuePairs.add(new BasicNameValuePair(singleParam
					.getParameterName(), paramValue));
		}
		String jsonString = "", line = "";
		Log.i(TAG, "Before connectToServer");
		try {
			BufferedReader rd = connectToservlet(httpPost, nameValuePairs);
			while ((line = rd.readLine()) != null) {
				Log.i(TAG,"line before");
				jsonString += line;
				Log.i(TAG,"line: "+line);
			}
			Log.i(TAG, "jsoString: "+jsonString);
			Gson json = new Gson();
			return json.fromJson(jsonString, objectClass);
			// {"UserAccountData":{"email":"maciej@gmail.com","phone":"+48 234123 123","regId":"ASDAASDASDAASDCX","driversId":3,"firstName":"Maciej","lastName":"kroplewski","login":"maciejTest1","name":"maciejTest1","vehicleId":51}}
		} catch (IOException e1) {
			Log.e(TAG, " Error IOException");
			e1.printStackTrace();
			return null;
		}
	}

	private BufferedReader connectToservlet(HttpPost httpPost2,
			List<NameValuePair> nameValuePairs) throws ServletConnectException {
		try {
			Log.i(TAG, "connectToServlet start");
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpClient.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			Log.i(TAG, statusLine.toString() + "|| "
					+ httpPost.getURI().toString());
			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				Log.e(TAG,
						"Error connecting to servlet: "
								+ statusLine.getStatusCode() + "|| "
								+ httpPost.getURI().toString());
				ServletConnectException sExc = new ServletConnectException();
				sExc.setStatusLine(statusLine);
				sExc.setMessageAdd("Error due connection to servlet");
				sExc.setUrl(httpPost.getURI().toString());
				throw sExc;
			}
			Log.i(TAG, "connectToServlet end");
			Log.i(TAG, "sdf ");
			return new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
		} catch (UnsupportedEncodingException e1) {
			Log.e(TAG, "ERROR UnsupportedEncodingException: "+e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "ERROR IOException: "+e.getMessage());
			e.printStackTrace();
		} catch (Exception ex){
			Log.e(TAG, "ERROR Exception: "+ex.getMessage()+ex.toString());
			ex.printStackTrace();
		}
		return null;
	}
}
