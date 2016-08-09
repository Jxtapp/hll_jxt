package com.hll.jxtapp;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hll.common.MyLocationInfo;
import android.app.Activity;
import android.os.Bundle;

public class RecommendMapActivity extends Activity {
	//地图控件
	private MapView mMapView;
	private BaiduMap baiduMap;
	private LocationClient locationClient;
	private MyLocationListener myLocationListener;
	private boolean isFirst=true;//是不是第一次进入地图，用于定位到用户当前的位置
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());  
        setContentView(R.layout.recommend_map);
        //获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.bmapView);  
        baiduMap = mMapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        if(locationClient == null){
			//初始化 locationClient
			locationClient = new LocationClient(this);
		}
		locationClient.start();
		if(myLocationListener == null){
			//初始化定位监听器
			myLocationListener = new MyLocationListener();
		}
        //初始化定位
        this.initLocation();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		//locationClient.stop();
	}
	
	//初始化定位
	private void initLocation(){
		//设置一些必要的属性
		LocationClientOption locationOption = new LocationClientOption();
		locationOption.setCoorType("bd09ll");
		locationOption.setIsNeedAddress(true);
		locationOption.setOpenGps(true);//????????????
		locationOption.setScanSpan(1000);
		//定位精度
		locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		locationClient.setLocOption(locationOption);
		//注册位置监听器
		locationClient.registerLocationListener(myLocationListener);
	}
	
	//定位监听
	private class MyLocationListener implements BDLocationListener{
		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location==null){
				return;
			}
			MyLocationData data = new MyLocationData.Builder()//
				.accuracy(location.getRadius())//
				.latitude(location.getLatitude())//
				.longitude(location.getLongitude())//
				.build();
			
			//图标
			//MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL, true, arg2);
			//baiduMap.setMyLocationConfigeration(config);
			
			if(isFirst == true){
				//第一次进入地图，用于定位到用户当前的位置
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				final double latitude = location.getLatitude();
				final double longitude = location.getLongitude();
				baiduMap.animateMapStatus(msu);
				isFirst=false;
			}
			baiduMap.setMyLocationData(data);
		}
	}
}
