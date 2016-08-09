package com.hll.jxtapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.hll.entity.UserO;
import com.hll.util.JxtUtil;
import com.hll.util.MyApplication;
import com.hll.util.NetworkInfoUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * user login
 * @author liaoyun
 * 2016-7-2
 */
public class userLoginActivity extends Activity{
	
	private TextView account;
	private TextView password;
	private TextView veryfiyCode;
	private Button loginButton;
	private Gson gson;
	private Context context;
	private TostHandle tostHandle;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		account = (TextView) findViewById(R.id.user_account);
		password = (TextView) findViewById(R.id.user_password);
		veryfiyCode = (TextView) findViewById(R.id.user_veriryCode);
		loginButton = (Button) findViewById(R.id.login_button);
		tostHandle = new TostHandle();
		context = this;
		
		//get lasted username and password
		SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("loginMessage", Context.MODE_PRIVATE);
		if(preferences != null){
			String lastAccount  = preferences.getString("loginAccount"  , "ly285714@sina.co");
			String lastPassword = preferences.getString("login_password", "123456");
			
			Map<String,String> map = new HashMap<>();
			map.put("lastAccount", lastAccount);
			map.put("lastPassword", lastPassword);
			Message message = Message.obtain();
			message.obj=map;
			
			Handler handle = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					@SuppressWarnings("unchecked")
					Map<String,String> map = (Map<String, String>) msg.obj;
					account.setText(map.get("lastAccount"));
					password.setText(map.get("lastPassword"));
				}
			};
			handle.sendMessage(message);
		}
		//when click login button
		loginButton.setOnClickListener(new loginOnclickListener());
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	private class loginOnclickListener implements OnClickListener{
		@Override
		public void onClick(View view) {
			if(gson == null){
				gson = new Gson();
			}
			final String accountStr  = account.getText()!=null ?  (String) account.getText().toString().trim() : "";
			final String passwordStr = password.getText()!=null ? (String) password.getText().toString().trim() : "";
			
			//save user account and password
			SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("loginMessage", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putString("lastAccount", accountStr);
			editor.putString("lastPassword", passwordStr);
			editor.commit();
			
			new Thread(){
				public void run() {
					String url = NetworkInfoUtil.baseUtl+"/user/login/"+accountStr+"/"+passwordStr+"/email.action";
					HttpURLConnection conn = JxtUtil.postHttpConn(url, "");
					try {
						InputStream is =  conn.getInputStream();
						String s = JxtUtil.streamToJsonString(is);
						Map<String,String> map = JxtUtil.jsonStringToMap(s);
						if(map != null){
							if(!map.get("type").equals("0")){//if login successed, return to the main page
								if(NetworkInfoUtil.userinfo == null){
									NetworkInfoUtil.userinfo = new UserO();
								}
								String type = map.get("type");
								if(type != null){
									NetworkInfoUtil.userinfo.setType(Integer.valueOf(type));
								}
								NetworkInfoUtil.userinfo.setNickName(map.get("nickName"));
								NetworkInfoUtil.userinfo.setEmail(map.get("email"));
								NetworkInfoUtil.userinfo.setTel(map.get("tel"));
								
								//come back to the main page
								Intent intent = new Intent(context,MainActivity.class);
								context.startActivity(intent);
							}else{//if login failed
								Message message = Message.obtain();
								message.obj="用户名或者密码错误";
								tostHandle.sendMessage(message);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
	}
	
	@SuppressLint("HandlerLeak")
	private class TostHandle extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String s = (String) msg.obj;
			Toast toast=Toast.makeText(context, s, Toast.LENGTH_LONG); 
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
}
