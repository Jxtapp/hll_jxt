package com.hll.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 常用方法的工具类
 * @author liaoyun
 * 2016-5-25
 */
public class JxtUtil {

	/**
	 * liaoyun 2016-5-28
	 * 返回 一个 http 联接
	 * @param url
	 * @return HttpURLConnection
	 */
	public static HttpURLConnection getHttpConn(String url){
		HttpURLConnection con=null;
		try {
			URL myUrl = new URL(url);
			con = (HttpURLConnection) myUrl.openConnection();
			con.setConnectTimeout(4000);
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * liaoyun 2016-5-28
	 * byte 数组 转 json 字符串
	 * @param is
	 * @return
	 */
	public static String streamToJsonString(InputStream is){
		ByteArrayOutputStream baos=null;
		try {
			int len=0;
			baos = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			while((len=is.read(data))!=-1){
				baos.write(data,0,len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String s="";
		if(baos!=null){
			s = new String(baos.toByteArray());
		}
		return s;
	}
}
