package com.hll.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hll.entity.SocketMsg;
import com.hll.entity.UserO;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
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
				//Log.i("jxtUtil","session1= "+NetworkInfoUtil.sessionId);
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
					//Log.i("jxtUtil","session2= "+NetworkInfoUtil.sessionId);
				}
			}
		} catch (MalformedURLException e) {
			Log.w("JxtUtil",e);
		} catch (IOException e) {
			Log.w("JxtUtil",e);
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
		if(is == null){
			return null;
		}
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
	public static Map<String,String> jsonStringToMap(String jsonString) throws Exception{
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
	
	/**
	 * 得到用户最近一次登陆成功的信息
	 * liaoyun 2016-8-9
	 * return UserO
	 */
	public static UserO getLastUserInfo(){
		SharedPreferences sp = MyApplication.getContext().getSharedPreferences("lastUserInfo", Context.MODE_PRIVATE);
		if(sp==null){
			return null;
		}
		String account = sp.getString("account", null);
		String password = sp.getString("password", null);
		UserO user = new UserO();
		user.setAccount(account);
		user.setPassword(password);
		user.setType(sp.getInt("type", 0));
		user.setNickName(sp.getString("nickName", null));
		user.setEmail(sp.getString("email", null));
		user.setTel(sp.getString("tel", null));
		user.setLastLoadTime(sp.getString("lastLoadTime", null));
		user.setLastLoadIp(sp.getString("lastLoadIp", null));
		user.setLastLoadPort(sp.getString("lastLoadPort", null));
		return user;
	}
	
	/**
	 * 保存最近一次用户登陆成功的信息
	 * liaoyun 2016-8-9
	 * @param user
	 */
	public static void saveLastUserInfo(UserO user){
		SharedPreferences sp = MyApplication.getContext().getSharedPreferences("lastUserInfo", Context.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.clear();
		ed.putString("account", user.getAccount());
		ed.putString("password", user.getPassword());
		ed.putInt("type", user.getType());
		ed.putString("nickName", user.getNickName());
		ed.putString("email", user.getEmail());
		ed.putString("tel", user.getTel());
		ed.putString("lastLoadTime", user.getLastLoadTime());
		ed.putString("lastLoadIp", user.getLastLoadIp());
		ed.putString("lastLoadPort", user.getLastLoadPort());
		ed.commit();
	}
	/**
	 * 修改 最近一次登陆的 用户的信息
	 * liaoyun 2016-8-9
	 * @param key
	 * @param value
	 */
	public static void updateLastUserInfo(String key, String value){
		if(key == null){
			return;
		}
		SharedPreferences sp = MyApplication.getContext().getSharedPreferences("lastUserInfo", Context.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putString(key.trim(), value.trim());
		ed.commit();
	}
	/**
	 * 以get请求方式 从服务器端取回一个对象
	 * liaoyun 2016-8-11
	 * @param clazz
	 * @param url
	 * @return 
	 * @return
	 */
	public static <T> T getObjectFromServer(Class<T> type,String url){
		try{
			HttpURLConnection con = getHttpConn(url);
			String str = streamToJsonString(con.getInputStream());
			JsonElement je = new JsonParser().parse(str);
			T t =  new Gson().fromJson(je, type);
			return t;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 以get请求方式 从服务器端取回一个对象的list集合
	 * liaoyun 2016-8-11
	 * @param type
	 * @param url
	 * @return 
	 * @return
	 */
	public static <T> List<T> getListObjectFromServer(Class<T> type,String url){
		try{
			List<T> list = new ArrayList<T>();
			HttpURLConnection con = getHttpConn(url);
			String str = streamToJsonString(con.getInputStream());
			JsonArray array = new JsonParser().parse(str).getAsJsonArray();
			for (JsonElement e : array) {
				list.add(new Gson().fromJson(e, type));
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 以post请求方式 从服务器端取回一个对象
	 * liaoyun 2016-8-11
	 * @param clazz
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static <T> T postObjectFromServer(Class<T> type,String url,String jsonStr){
		try{
			HttpURLConnection con = postHttpConn(url, jsonStr);
			String str = streamToJsonString(con.getInputStream());
			JsonElement je = new JsonParser().parse(str);
			T t =  new Gson().fromJson(je, type);
			return t;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 以post请求方式 从服务器端取回一个对象的list集合
	 * liaoyun 2016-8-11
	 * @param type
	 * @param url
	 * @param data
	 * @return 
	 * @return
	 */
	public static <T> List<T> postListObjectFromServer(Class<T> type,String url,String data){
		try{
			List<T> list = new ArrayList<T>();
			HttpURLConnection con = postHttpConn(url, data);
			String str = streamToJsonString(con.getInputStream());
			JsonArray array = new JsonParser().parse(str).getAsJsonArray();
			for (JsonElement e : array) {
				list.add(new Gson().fromJson(e, type));
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 以get的方式从服务器获取信息 liaoyun 2016-8-13
	 * @param url
	 * @return map
	 */
	public static Map<String,String> getMapFromServer(String url){
		try{
			HttpURLConnection con = getHttpConn(url);
			String str = inputStreamToString(con.getInputStream());
			Map<String,String> map = jsonStringToMap(str);
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 以post的方式从服务器获取信息 liaoyun 2016-8-13
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,String> postMapFromServer(String url,String jsonStr){
		try{
			HttpURLConnection con = postHttpConn(url, jsonStr);
			String str = inputStreamToString(con.getInputStream());
			Map<String,String> map = jsonStringToMap(str);
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 水平正中央的 toast
	 * liaoyun 2016-8-12
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void toastCenter(Context context,String text, int duration){
		Toast tt = Toast.makeText(context, text, duration);
		tt.setGravity(Gravity.CENTER, 0, 0);
		tt.show();
	}
	
	/**
	 * 由服务器时间字符串转换为date类型  liaoyun 2016-8-12
	 * @param serverTime
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getServerTime(String serverTime){
		if(serverTime==null || serverTime.equals("")){
			return null;
		}
		String[] ss = serverTime.split(",");
		Date date = new Date(Integer.valueOf(ss[0])-1900, Integer.valueOf(ss[1])-1, Integer.valueOf(ss[2]), Integer.valueOf(ss[3]), Integer.valueOf(ss[4]), Integer.valueOf(ss[5]));
		return date;
	}
	
	/**
	 * 将对象转换为json字符串 liaoyun 2016-8-13
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object){
		try{
			Gson gson = new Gson();
			String str = gson.toJson(object);
			return str;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 创建要发送的信息 liaoyun 2016-8-19
	 * @param scene
	 * @param type
	 * @param users
	 * @param message
	 * @return
	 */
	public static String createSocketMsg(int scene,int type,List<String> users,String message){
		String account = NetworkInfoUtil.accountId;
		if(NetworkInfoUtil.socketId==null || account==null){                     //没有登陆
			return null;
		}
		if(type == SocketMsg.TYPE_TRANSMIT && (users==null || users.size()==0)){ //当为普通转发时，转发对象不能为空
			return null;
		}
		if(scene != 3 && (message == null || message.equals(""))){               //当不为websocket登陆时，转发信息不能为空
			return null; 
		}
		SocketMsg msg = new SocketMsg();
		msg.setAccount(account);
		msg.setKey(NetworkInfoUtil.socketId);
		msg.setScene(scene);
		msg.setType(type);
		msg.setUsers(users);
		msg.setMessage(message);
		return objectToJson(msg);
	} 
}
