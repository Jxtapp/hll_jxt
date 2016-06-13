package com.hll.jxtapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 通过 url 获取 服务器端的数据
 * @author liaoyun
 * 2016-5-24
 */
public class UrlConnect <T>{
	private String url;
	private List<T> dataList;
	
	public List<T> getDataByUrl(String urlString){
		this.url=urlString;
		new GetDataByUrlThred(url).start();
		return dataList;
	}
	/**
	 * 将 json 字符串转 List<Map<String,Object>> 的线程
	 * @author Administrator
	 *
	 */
	private  class GetDataByUrlThred extends Thread{
		private String durl;
		GetDataByUrlThred(String url){
			this.durl=url;
		}
		@Override
		public  void run() {
			try {
				URL myUrl = new URL(durl);
				HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
				con.setConnectTimeout(4000);
				con.setRequestMethod("GET");
				con.setDoInput(true);
				con.setDoOutput(true);
				con.connect();
				InputStream is = con.getInputStream();
				int len=0;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] data = new byte[1024];
				while((len=is.read(data))!=-1){
					baos.write(data,0,len);
				}
				// byte 数组 转 json 字符串
				String streamString = new String(baos.toByteArray());
				//将 String类型的json字符串 转为 List<Map<String,Object>> 类型
				//dataList=JxtUtil.JsonToListMap(streamString,T);
				Gson gson = new Gson();
				List<T> list = gson.fromJson(streamString, new TypeToken<List<T>>(){}.getType());
				dataList = list;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}