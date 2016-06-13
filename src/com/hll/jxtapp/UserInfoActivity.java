package com.hll.jxtapp;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.hll.basic.LeftMoveView;
import com.hll.basic.LeftTabView;
import com.hll.left.AboutUsView;
import com.hll.left.AppraiseView;
import com.hll.left.ContactUsView;
import com.hll.left.LoginView;
import com.hll.left.MessageView;
import com.hll.left.MyOrderView;
import com.hll.left.MyStoreView;
import com.hll.left.PersonInfoView;
import com.hll.left.QuestionBankView;
import com.hll.left.SettingView;

public class UserInfoActivity extends Activity implements  Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Context context;
	private LeftMoveView leftMoveView;
	private LeftMenuView leftMenuView;
	
	public LoginView login;
	public QuestionBankView questinBank;
	public MyOrderView myOrder;
	public AboutUsView aboutUs;
	public AppraiseView appraiseUs;
	public ContactUsView contactUs;
	public MessageView message;
	public MyStoreView myStore;
	public PersonInfoView personInfo;
	public QuestionBankView questionBank;
	public SettingView setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.context = this;
		
		//创建移动视图
		leftMoveView = new LeftMoveView(context);
		//创建左边菜单视图
		leftMenuView = new LeftMenuView(this, leftMoveView);
		
		/****************************************************/
		//界面01
		login = new LoginView(context);
		login.setMoveView(leftMoveView);//赋值移动视图
		login.init();
		/***************************************************/
		
		questionBank = new QuestionBankView(context);
		myOrder = new MyOrderView(context);
		aboutUs = new AboutUsView(context);
		appraiseUs = new AppraiseView(context);
		contactUs = new ContactUsView(context);
		message = new MessageView(context);
		myStore = new MyStoreView(context);
		personInfo = new PersonInfoView(context);
		//questionBank = new QuestionBankView(context);
		setting = new SettingView(context);
		
		//移动视图默认界面：界面01，
		leftMoveView.setMainView(login, leftMenuView,LeftTabView.TAB_ITEM01);
		leftMoveView.getLeft_show_view().setCurrentTab(LeftTabView.TAB_ITEM01);

		setContentView(leftMoveView);
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}
	
	@Override
	protected void onResume() {
		super.onResume();

	}

	
}
