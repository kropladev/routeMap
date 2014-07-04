/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.kropla.routemapclient;

import static dev.kropla.routemapclient.CommonUtilities.TAG;
import static dev.kropla.routemapclient.CommonUtilities.displayMessage;
import com.google.android.gcm.GCMRegistrar;
import com.google.gson.Gson;
import dev.kropla.connection.servlet.MainControllerConnection;
import dev.kropla.connection.servlet.utils.ServletConnectException;
import dev.kropla.connection.servlet.utils.ServletParams;
import dev.kropla.dto.UserAccount;
import dev.kropla.routemapclient.R;
import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

    private static final int MAX_ATTEMPTS = 5;
    //private static final int BACKOFF_MILLI_SECONDS = 2000;
    //private static final Random random = new Random();

    /**
     * Register this account/device pair within the server.
     * @param userAccount 
     *
     * @return whether the registration succeeded or not. 
     * @throws ServletConnectException 
     */
    @SuppressWarnings("unused") 
	public static boolean register(final Context context, final String regId, UserAccount userAccount) throws ServletConnectException {
        Log.d(TAG, "registering device (regId = " + regId + ")");
        String contextTag="registerRegId.htm";
       //String serverUrl = SERVER_URL + "/register";
       // Map<String, String> params = new HashMap<String, String>();
        List<ServletParams> params= new ArrayList<ServletParams>();
        ServletParams singleParam = new ServletParams("regId", regId, "string");
        params.add(singleParam);
        Gson gson = new Gson();
        String userAccStr=gson.toJson(userAccount);
        ServletParams sParam2= new ServletParams("userAcc", userAccStr, "string");
        params.add(sParam2);
        String resultStr="";
        MainControllerConnection mController= new MainControllerConnection();
        //long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register on server:"+contextTag+" with params:"+params.toString());
            displayMessage(context, context.getString(R.string.server_registering, i, MAX_ATTEMPTS));
			resultStr = mController.postServlet(contextTag, params,String.class).toString();
			Log.d(TAG, "RESULT::: "+resultStr);
			if (resultStr.contains("ERROR")){
				CommonUtilities.displayMessage(context, "ERROR connecting to server");
				 return false;
			}
			GCMRegistrar.setRegisteredOnServer(context, true);
			String message = context.getString(R.string.server_registered);
			CommonUtilities.displayMessage(context, message);
			return true;
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
        return false;
    }

    /**
     * Unregister this account/device pair within the server.
     * @throws ServletConnectException 
     */
	public static void unregister(final Context context, final String regId)throws ServletConnectException {
		Log.i(TAG, "unregistering device (regId = " + regId + ")");
		String contextTag = "registerRegId.htm";
		List<ServletParams> params = new ArrayList<ServletParams>();
		ServletParams singleParam = new ServletParams("regId", regId, "string");
		params.add(singleParam);
		MainControllerConnection mController = new MainControllerConnection();
		/*String resultStr = (String)*/ mController.postServlet(contextTag, params,String.class);
		GCMRegistrar.setRegisteredOnServer(context, false);
		String message = context.getString(R.string.server_unregistered);
		CommonUtilities.displayMessage(context, message);
	}
}
