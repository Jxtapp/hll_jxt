package com.hll.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络 服务 相关信息
 * @author liaoyun
 * 2016-6-2
 */
public class NetworkInfoUtil {
	public static String baseUtl="http://192.168.1.3:8080/hll";  //服务器 地址
	public static String picUtl=baseUtl+"/file/pic/";            //图片接口
	public static ConnectivityManager connectManager;            //网络状态
	public static String sessionId=null;                         //会话 session 对应的 sessionId
	public static Integer socketId = null;                       //用户websocket连接码
	public static String accountId;                              //用户账号
	public static String name;                                   //用户姓名
	public static String nickName;                               //用户昵称
	
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
