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

//import dev.kropla.routemapclient.R;
import android.content.Context;
import android.content.Intent;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

	public static String apiRegKey="";
    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
   // public static final String SERVER_URL = "http://kropladev.pl/routeMap3";
	//public static final String SERVER_URL = "http://localhost:8080/routeMap3";
	//public static final String SERVER_URL= "http://localhost:8080/gcm_server_demo";
    //public static final String SERVER_URL = "http://10.0.1.13:8080/routeMap3";
	//public static final String SERVER_URL = "http://10.0.1.11:8080/routeMap3";
	//public static final String SERVER_URL = "http://kropladev.pl/routeMap3";

    /**
     * Google API project id registered to use GCM.
     */
    public static final String SENDER_ID = "487282640123";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Utilities";

    /**
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION =
            "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    
/*    public static void checkNotNull(Object reference, String name, Context context) {
        if (reference == null) {
            throw new NullPointerException(
                    context.getString(R.string.error_config, name));
        }
    }
    
    public static void checkConfigValues(Context context){
    	  CommonUtilities.checkNotNull(SERVER_URL, "SERVER_URL", context);
          CommonUtilities.checkNotNull(SENDER_ID, "SENDER_ID",context);
    }*/
    	
    
}
