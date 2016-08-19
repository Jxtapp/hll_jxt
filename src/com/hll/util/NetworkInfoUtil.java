package com.hll.util;

import com.hll.entity.UserO;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络 服务 相关信息
 * @author liaoyun
 * 2016-6-2
 */
public class NetworkInfoUtil {
	//服务器 地址
	public static String baseUtl="http://192.168.191.1:8080/hll";
	//图片接口
	public static String picUtl=baseUtl+"/file/pic/";
	//网络状态
	public static ConnectivityManager connectManager;
	//sessionId
	public static String sessionId=null;
	//socketId
	public static Integer socketId = null;
	//用户账号id
	public static String accountId;
	
	/**
	 * 当前的网络接入的类型  WIFI or MOBILE or NULL
	 * liaoyun 2016-6-4
	 * @return
	 */
	public static String getNetWorkState(){
		NetworkInfo netWorkInfo = connectManager.getActiveNetworkInfo();
		if(netWorkInfo==null){
			return null;
		}
		return netWorkInfo.getTypeName();
	}
}
