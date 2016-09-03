package com.hll.jxtapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.hll.jxtapp.R;

/**
 * 自定义 用户信息 界面  的 view
 */
public class LeftMenuView extends Activity{
	
	private LinearLayout login;
	private LinearLayout personInfo;
	private LinearLayout setting;
	private LinearLayout contactUs;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu);
		
		createView();
		setViewProperty();
	}
	
	

	public void createView() {
		login  = (LinearLayout)findViewById(R.id.id_login);              //登陆图标
		personInfo  = (LinearLayout)findViewById(R.id.id_personInfo);    //个人信息
		setting = (LinearLayout)findViewById(R.id.id_setting);           //设置
		contactUs = (LinearLayout)findViewById(R.id.id_contactUs);		  //联系我们
	}

	public void setViewProperty() {
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startLogin();
			}
		});
		
		personInfo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						startPersonInfo();
					}
				});
		
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startSetting();
			}
		});
		contactUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startContactUs();
			}
		});

	}
	/**
	 * user login
	 */
	public void startLogin(){
		Intent intent = new Intent(LeftMenuView.this,userLoginActivity.class);
		startActivity(intent);
	}

	public void startContactUs(){

	}
	public void startPersonInfo(){
	}
	public void startSetting(){
	}
	

}
