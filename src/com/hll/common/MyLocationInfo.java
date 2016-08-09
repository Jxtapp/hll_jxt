package com.hll.common;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.hll.util.JxtUtil;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * 定位 服务 相关信息
 * @author liaoyun
 * 2016-6-4
 */
public class MyLocationInfo {
	public final static String AK = "DVgkYGWGAAiCt1l2HbBbew1fZPGc6Oo9";
	public static String MY_ADDRESS = null;                                //当前地理位置
	public static double nowLat = 0.0;                                     //当前纬度
	public static double nowLng = 0.0; 
	//当前经度
	public void getMyaddress(Context context){
		boolean gpsEnabled = false;
		boolean newWorkEnabled = false;
		LocationManager lmgr = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		gpsEnabled = lmgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
		newWorkEnabled = lmgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if(gpsEnabled){
			Location location = lmgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location != null){
				setNowLocation(location);
			}
			while(location == null){
				lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);  
			}
			//lmgr.removeTestProvider(LocationManager.GPS_PROVIDER);
		}else if(newWorkEnabled){
			Location location = lmgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if(location != null){
				setNowLocation(location);
			}
			while(location == null){
				lmgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener); 
			}
			//lmgr.removeTestProvider(LocationManager.NETWORK_PROVIDER);
		}else{
			
		}
	}
	
	private void setNowLocation(Location location) {
		nowLat = location.getLatitude();
		nowLng = location.getLongitude();
	}

	LocationListener locationListener = new LocationListener(){
		@Override
		public void onLocationChanged(Location loc) {
			setNowLocation(loc);
		}
		@Override
		public void onProviderDisabled(String arg0) {
			
		}
		@Override
		public void onProviderEnabled(String arg0) {
			
		}
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			
		}
	};
	/**
	 * Reverse Address Resolution
	 * liaoyun 2016-8-6
	 */
	public void reverseAddressResolution(double latitude, double longitude){
		String urlStr = "http://api.map.baidu.com/geocoder/v2/?"
				+ "ak="+AK+"&"
				+ "callback=renderReverse&"
				+ "location="+latitude+","+longitude+"&"
				+ "output=json&"
				+ "pois=1&"
				+ "mcode=99:06:47:3C:F9:CC:2A:4F:AE:C0:BF:B5:03:84:B6:53:93:42:1B:34;com.hll.jxtapp";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			InputStream is = con.getInputStream();
			String addressListStr = JxtUtil.inputStreamToString(is);
			if(addressListStr != null){
				int a = addressListStr.indexOf("{\"adcode\"");
				int b = addressListStr.indexOf(",\"pois\":[");
				Map<String,String> map = JxtUtil.jsonStringToMap(addressListStr.substring(a,b));
				MY_ADDRESS = map.get("province")+map.get("city")+map.get("district");//+map.get("street");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
