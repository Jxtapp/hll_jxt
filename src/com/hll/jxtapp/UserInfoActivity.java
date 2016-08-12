package com.hll.jxtapp;

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

public class UserInfoActivity extends Activity{
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
		
		leftMoveView = new LeftMoveView(context);
		leftMenuView = new LeftMenuView(this, leftMoveView);
		
		/****************************************************/
		login = new LoginView(context);
		login.setMoveView(leftMoveView);
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
