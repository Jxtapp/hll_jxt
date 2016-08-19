package com.hll.jxtapp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import com.hll.entity.UserO;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
	private Context context;
	private TostHandle tostHandle;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		account = (TextView) findViewById(R.id.user_account);       //账号
		password = (TextView) findViewById(R.id.user_password);     //密码
		veryfiyCode = (TextView) findViewById(R.id.user_veriryCode);//校验码
		loginButton = (Button) findViewById(R.id.login_button);     //登陆按钮
		tostHandle = new TostHandle();
		context = this;
		
		//获取最近一次登陆的信息，并自动添加到登陆界面上
		UserO lastUserInfo =  JxtUtil.getLastUserInfo();
		String lastAccount  = lastUserInfo.getAccount()==null ? "" : lastUserInfo.getAccount();
		String lastPassword = lastUserInfo.getPassword()==null ? "" : lastUserInfo.getPassword();
		Map<String,String> map = new HashMap<String,String>();
		map.put("lastAccount", lastAccount);
		map.put("lastPassword", lastPassword);
		Message message = Message.obtain();
		message.obj=map;
		loginHandler handle = new loginHandler();
		handle.sendMessage(message);
		
		loginButton.setOnClickListener(new loginOnclickListener());//登陆按钮监听器
	}
	
	//监听用户登陆
	private class loginOnclickListener implements OnClickListener{
		@Override
		public void onClick(View view) {
			final String accountStr  = account.getText()!=null ?  (String) account.getText().toString().trim() : "";
			final String passwordStr = password.getText()!=null ? (String) password.getText().toString().trim() : "";
			new Thread(){
				public void run() {
					String url = NetworkInfoUtil.baseUtl+"/user/login/"+accountStr+"/"+passwordStr+"/email.action";
					HttpURLConnection conn = JxtUtil.postHttpConn(url, "");
					try {
						InputStream is =  conn.getInputStream();
						if(is != null){                                                         //登陆成功
							String s = JxtUtil.streamToJsonString(is);
							Map<String,String> map = JxtUtil.jsonStringToMap(s);
							UserO user = new UserO();
							user.setType(Integer.valueOf(map.get("type")));
							user.setNickName(map.get("nickName"));
							user.setAccount(accountStr);
							user.setPassword(passwordStr);
							user.setEmail(map.get("email"));
							user.setTel(map.get("tel"));
							user.setLastLoadTime(map.get("lastLoadTime"));
							user.setLastLoadIp(map.get("lastLoadIp"));
							user.setLastLoadPort(map.get("lastLoadPort"));
							JxtUtil.saveLastUserInfo(user);                                        //保存用户信息
							NetworkInfoUtil.accountId = map.get("account");                        //用户的account
							NetworkInfoUtil.socketId = Integer.valueOf(map.get("sessionKey"));     //保存websocket验证的key值
							finish();                                                              //返回上一个页面
						}else{                                                                     //登陆失败
							Message message = Message.obtain();
							message.obj="用户名或者密码错误";
							tostHandle.sendMessage(message);
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
	
	@SuppressLint("HandlerLeak")
	private class loginHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			Map<String,String> map = (Map<String, String>) msg.obj;
			account.setText(map.get("lastAccount"));
			password.setText(map.get("lastPassword"));
		}
	}
}
