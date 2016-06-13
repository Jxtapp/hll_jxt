package com.hll.util;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * 定位 服务 相关信息
 * @author liaoyun
 * 2016-6-4
 */
public class LocationInfoUtil {
	//定位管理对象
	public static LocationManager locationManager;
	
	
	private Timer timer1;
	private LocationResult locationResult;
	private boolean gps_enabled=false;
	private boolean network_enabled=false;
	/**
	 * 在 gps 和  network 之间进行切换，，用于获得较好的位置信息
	 * liaoyun 2016-6-4
	 * @param context
	 * @param result
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean getLocation(Context context,LocationResult result){
		locationResult = result;
		if(locationManager==null){
			locationManager=(LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		}
		//gps 定位 是否可用
		gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//network 定位 是否可用
		network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		//如果两种定位方式都 不可用 
		if(!gps_enabled && !network_enabled){
			return false;
		}
		//如果 gps 定位 可用
		if(gps_enabled){
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, LocationListenerGps);
		}
		//如果  network 定位可以用
		if(network_enabled){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, LocationListenerNetwork);
		}
		timer1 = new Timer();
		timer1.schedule(new GetLastLocation(), 2000);
		return true;
	}
	
	//gps 位置监听
	LocationListener LocationListenerGps = new LocationListener() {
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			
		}
		@Override
		public void onProviderEnabled(String arg0) {
			
		}
		@Override
		public void onProviderDisabled(String arg0) {
			
		}
		@Override
		public void onLocationChanged(Location location) {
			timer1.cancel();
			locationResult.getLocation(location);
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(LocationListenerNetwork);
		}
	};
	//network 定位  位置 监听
	LocationListener LocationListenerNetwork = new LocationListener() {
		
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			
		}
		@Override
		public void onProviderEnabled(String arg0) {
			
		}
		@Override
		public void onProviderDisabled(String arg0) {
			
		}
		@Override
		public void onLocationChanged(Location location) {
			timer1.cancel();
			locationResult.getLocation(location);
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(LocationListenerGps);
		}
	};
	
	//定时任务事件，取得 定位效果最好的 方式（gps or network）
	private class GetLastLocation extends TimerTask{
		@Override
		public void run() {
			locationManager.removeUpdates(LocationListenerGps);
			locationManager.removeUpdates(LocationListenerNetwork);
			Location networkLocation=null,gpsLocation=null;
			if(gps_enabled){
				gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			if(network_enabled){
				networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			if(!gps_enabled && !network_enabled){
				if(gpsLocation.getTime() > networkLocation.getTime()){
					locationResult.getLocation(gpsLocation);
				}else{
					locationResult.getLocation(networkLocation);
				}
			}
			if(gpsLocation!=null){
				locationResult.getLocation(gpsLocation);
			}
			if(networkLocation!=null){
				locationResult.getLocation(networkLocation);
			}
			locationResult.getLocation(null);
		}
	}
	
	//用来 保存位置信息
	private static abstract class LocationResult{
		public abstract void getLocation(Location location);
	}
}
