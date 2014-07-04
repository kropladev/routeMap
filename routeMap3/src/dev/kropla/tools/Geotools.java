package dev.kropla.tools;

import java.text.DecimalFormat;
import java.text.ParseException;
import static dev.kropla.tools.Configs.log;

public class Geotools {

	public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		
		double pk = (double) (180/3.14169);

		double a1 = lat_a / pk;
		double a2 = lng_a / pk;
		double b1 = lat_b / pk;
		double b2 = lng_b / pk;

		double t1 = (double) (Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2));
		double t2 = (double) (Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2));
		double t3 = (double) (Math.sin(a1)*Math.sin(b1));
	    double tt = Math.acos(t1 + t2 + t3);

	    return 6366000*tt;
	}
	
	
	/*** 
	 * nie u�ywam - formatowanie jest w widoku
	 * @deprecated 
	 * @param dist
	 * @return
	 * @throws ParseException
	 */
	public static double formatDistance(double dist) throws ParseException{
		java.text.DecimalFormat fd = new DecimalFormat("### ##0.0");
		log.debug("formating double  " +fd.parse(fd.format(dist)).doubleValue());
		return fd.parse(fd.format(dist)).doubleValue();
	}
}
