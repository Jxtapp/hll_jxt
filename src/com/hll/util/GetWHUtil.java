package com.hll.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class GetWHUtil {
	/**
	 * ������Ļ���
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context){
		DisplayMetrics dm = new android.util.DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
	}
	/**
	 * ������Ļ�߶�
	 * @param context
	 * @return
	 */
	public static int getWindowHeight(Context context){
		DisplayMetrics dm = new android.util.DisplayMetrics();   
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
	}
}
