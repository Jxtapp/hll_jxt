package com.hll.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
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
			con.setRequestProperty("Content-Type", "application/json");
			if(NetworkInfoUtil.sessionId!=null){
				//如果已经建立了联接
				con.setRequestProperty("cookie", NetworkInfoUtil.sessionId);
			}else{
				//第一次建立联接，获取 sessionId
				String cookieval = con.getHeaderField("set-cookie"); 
				if(cookieval != null) { 
					NetworkInfoUtil.sessionId = cookieval.substring(0, cookieval.indexOf(";")); 
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * 向服务器传递 json对象的参数，以post请求的方式
	 * liaoyun 2016-6-20
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static HttpURLConnection postHttpConn(String url,String jsonStr){
		HttpURLConnection con=null;
		try {
			URL myUrl = new URL(url);
			con = (HttpURLConnection) myUrl.openConnection();
			con.setConnectTimeout(4000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setInstanceFollowRedirects(false);//???????????????????????
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("ser-Agent", "Fiddler");
			con.setRequestProperty("Charset", "utf-8");
			if(NetworkInfoUtil.sessionId!=null){
				//如果已经建立了联接
				con.setRequestProperty("cookie", NetworkInfoUtil.sessionId);
			}
			OutputStream os = con.getOutputStream();
			os.write(jsonStr.getBytes());
			os.close();
			//int code = con.getResponseCode();
			
			if(NetworkInfoUtil.sessionId==null){
				//第一次建立联接，获取 sessionId
				List<String> sid = con.getHeaderFields().get("Set-Cookie");
				if(sid !=null && sid.size()>0){
					String ss = sid.get(0);
					NetworkInfoUtil.sessionId = ss.substring(0, ss.indexOf(";"));
					int a=0;
				}
			}
		} catch (MalformedURLException e) {
			String s = e.getMessage();
			Log.i("liaoyun",s);
		} catch (IOException e) {
			String s = e.getMessage();
			Log.i("liaoyun",s);
		}
		return con;
	}
	
	/**
	 * 返回 json 字符串
	 * @param url
	 * @param jsonString
	 * @return
	 */
	public static String doPost(String url,String jsonString){
		HttpPost post = new HttpPost(url);
		String str = null;
		StringEntity se;
		try {
			se = new StringEntity(jsonString);
			post.setEntity(se);
			if(NetworkInfoUtil.sessionId!=null){
				post.setHeader("Cookie", "JSESSIONID="+NetworkInfoUtil.sessionId);
			}
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			str = entity.toString();
			// get sessionId
			if(NetworkInfoUtil.sessionId==null){
				CookieStore mCookieStore = httpClient.getCookieStore();  
				List<Cookie> cookies = mCookieStore.getCookies();  
				for (int i = 0; i < cookies.size(); i++) {   
		            if ("SESSIONID".equals(cookies.get(i).getName())) {   
		            	NetworkInfoUtil.sessionId = cookies.get(i).getValue();  
		               break;  
		            }  
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
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
	
	/**
	 * liaoyun 2016-7-2
	 * convert json string to map
	 * @param jsonString
	 * @return
	 */
	public static Map<String,String> jsonStringToMap(String jsonString){
		if(jsonString != null){
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(jsonString, new TypeToken<Map<String,String>>(){}.getType());
			return map;
		}else{
			return null;
		}
	}
	
	/**
	 * liaoyun 2016-7-2
	 * convert inputstream to string
	 * @return
	 */
	public static String inputStreamToString(InputStream is){
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(is, "UTF-8");
	    String s = scanner.useDelimiter("\\A").next();
	    return s;
	}
}
