package dev.kropla.utils.location;

import dev.kropla.connection.webservice.WebServiceConnect;
import dev.kropla.connection.webservice.pojo.MethodArgInsertGpsCoord;
import dev.kropla.utils.Config;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.text.DecimalFormat;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {
	Context context;
	private double locLat,locLon;
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private DecimalFormat myFormatter = new DecimalFormat("##.#");
	private Location currentBestLocation=null;
	private Location lastLocation=null;
	public static  double dTraveled=0.0;
	public static String dTraveledWithFormat;
	public int tPassed;
	
	public MyLocationListener (Context contextArg){
		this.context=contextArg;
	}
	
	@Override
	public void onLocationChanged(Location loc) {
		if (isBetterLocation(loc, this.currentBestLocation)){
			locLat=loc.getLatitude();
			locLon=loc.getLongitude();
			this.currentBestLocation=loc;
			MethodArgInsertGpsCoord argWS=new MethodArgInsertGpsCoord();
			argWS.setLat(locLat);
			argWS.setLon(locLon);
			argWS.setRouteId(Config.updateRoutesPosition ? Config.actualRouteId : 0);
			argWS.setUpdateRoutesPosition(Config.updateRoutesPosition);
			argWS.setVehicleName(Config.userAccount.getLogin());
			if (Config.updateRoutesPosition ){
				dTraveledWithFormat=updateSpeedAndDistance();
			}
		//	Log.d("MyLocationListener","Lat:"+locLat+ "|Lon:"+locLon+"|RouteId:"+Config.actualRouteId+"|UpdateRoutesPos:"+Config.updateRoutesPosition+"|VehicleName:"+Config.userAccount.getLogin());
			WebServiceConnect.insertLocation2(argWS);	
		}
	}

	public String updateSpeedAndDistance() {
		String result="00.0 m";
		System.out.println("Time: " + tPassed);
		tPassed++;
		if (lastLocation != null) {
			// distance
			if (tPassed % 5 == 0 && currentBestLocation.getLatitude() != 0) {
				if (currentBestLocation != null) {
					float d = lastLocation.distanceTo(currentBestLocation);
					dTraveled += d;
					result=myFormatter.format(dTraveled) + " m";
				}
			}
		}
		lastLocation = currentBestLocation;
		return result;
	}
	
	@Override
	public void onProviderDisabled(String provider) {	
		Toast.makeText(this.context, Config.MSG_DEVICE+provider+Config.MSG_DEVICE_DISABLED,Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this.context,  Config.MSG_DEVICE+provider+Config.MSG_DEVICE_ENABLED,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	    	//Log.d("TAG", "===isMoreAccurate");
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	    	//Log.d("TAG", "===isNewer && !isLessAccurate");
	    	return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	    	//Log.d("TAG", "===isNewer && !isSignificantlyLessAccurate && isFromSameProvider");
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
}