package com.hll.util;

import android.app.Application;
import android.content.Context;
/**
 * 2016-73
 * @author liao yun
 * application manager
 */
public class MyApplication extends Application{
	
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}
	
	public static Context getContext(){
		return context;
	}
}
