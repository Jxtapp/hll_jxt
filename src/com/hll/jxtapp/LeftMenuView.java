package com.hll.jxtapp;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.hll.jxtapp.R;
import com.hll.basic.LeftMoveView;
import com.hll.basic.LeftTabView;


public class LeftMenuView{
	
	private LinearLayout accountInfo;
	private LinearLayout login;
	private LinearLayout personInfo;
	private LinearLayout questionBank;
	private LinearLayout myOrder;
	private LinearLayout myStore;
	private LinearLayout message;
	private LinearLayout setting;
	private LinearLayout appraiseUs;
	private LinearLayout contactUs;
	private LinearLayout aboutUs;
	
	private UserInfoActivity userInfo;
	private View view;
	private LeftMoveView leftMoveView;
	private int currentTab=LeftTabView.TAB_ITEM01;
	
	
	
	/**
	 * �����ʼ��
	 * @param userInfo2
	 * @param leftMoveView
	 */
	public LeftMenuView(UserInfoActivity userInfo,LeftMoveView leftMoveView){
		this.userInfo = userInfo;
		this.leftMoveView = leftMoveView;
		this.view = LayoutInflater.from(userInfo).inflate(R.layout.leftmenu, null);
		createView();
		setViewProperty();
	}

	public void createView() {
		accountInfo  = (LinearLayout)view.findViewById(R.id.id_accountInfo);//�˺���Ϣ������ͷ
		login  = (LinearLayout)view.findViewById(R.id.id_login);
		personInfo  = (LinearLayout)view.findViewById(R.id.id_personInfo);
		questionBank = (LinearLayout)view.findViewById(R.id.id_questionBank);
		myOrder = (LinearLayout)view.findViewById(R.id.id_myOrder);
		myStore = (LinearLayout)view.findViewById(R.id.id_myStore);
		message = (LinearLayout)view.findViewById(R.id.id_message);
		setting = (LinearLayout)view.findViewById(R.id.id_setting);
		appraiseUs = (LinearLayout)view.findViewById(R.id.id_appraiseUs);
		contactUs = (LinearLayout)view.findViewById(R.id.id_contactUs);
		aboutUs = (LinearLayout)view.findViewById(R.id.id_aboutUs);
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
		
		questionBank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startMyOrder();
			}
		});
		myOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startMyOrder();
			}
		});
		myStore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startMyStore();
			}
		});
		message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startMessage();
			}
		});
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startSetting();
			}
		});
		appraiseUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startAppraiseUs();
			}
		});
		contactUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startContactUs();
			}
		});
		aboutUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startAboutUs();
			}
		});
	}

	
	/**
	 * user login
	 */
	public void startLogin(){
		if(!startInit(LeftTabView.TAB_ITEM01)){
			login.setBackgroundResource(R.color.yellow);

			setCurrentTab(LeftTabView.TAB_ITEM01);
			if(userInfo.login.view==null){
				userInfo.login.init();
				userInfo.login.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.login, null,LeftTabView.TAB_ITEM01);
		}
	}
	/**
	 * ��questionBank
	 */
	public void startQuestinBank(){
		if(!startInit(LeftTabView.TAB_ITEM02)){
			questionBank.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM02);
			
			if(userInfo.questionBank.view==null){
				userInfo.questionBank.init();
				userInfo.questionBank.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.questionBank, null,LeftTabView.TAB_ITEM02);
		}
	}
	
	/**
	 * ��myOrder
	 */
	public void startMyOrder(){
		if(!startInit(LeftTabView.TAB_ITEM03)){
			myOrder.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM03);
			
			if(userInfo.myOrder.view==null){
				userInfo.myOrder.init();
				userInfo.myOrder.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.myOrder, null,LeftTabView.TAB_ITEM03);
		}
	}

	/**
	 * ��aboutUs
	 */
	public void startAboutUs(){
		if(!startInit(LeftTabView.TAB_ITEM04)){
			aboutUs.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM04);
			
			if(userInfo.aboutUs.view==null){
				userInfo.aboutUs.init();
				userInfo.aboutUs.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.aboutUs, null,LeftTabView.TAB_ITEM04);
		}
	}
	/**
	 * ��appraiseUs
	 */
	public void startAppraiseUs(){
		if(!startInit(LeftTabView.TAB_ITEM05)){
			appraiseUs.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM05);
			
			if(userInfo.appraiseUs.view==null){
				userInfo.appraiseUs.init();
				userInfo.appraiseUs.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.appraiseUs, null,LeftTabView.TAB_ITEM05);
		}
	}
	/**
	 * ��contactUs
	 */
	public void startContactUs(){
		if(!startInit(LeftTabView.TAB_ITEM06)){
			contactUs.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM06);
			
			if(userInfo.contactUs.view==null){
				userInfo.contactUs.init();
				userInfo.contactUs.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.contactUs, null,LeftTabView.TAB_ITEM06);
		}
	}
	/**
	 * ��message
	 */
	public void startMessage(){
		if(!startInit(LeftTabView.TAB_ITEM07)){
			message.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM07);
			
			if(userInfo.message.view==null){
				userInfo.message.init();
				userInfo.message.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.message,null,LeftTabView.TAB_ITEM07);
		}
	}
	/**
	 * ��myStore
	 */
	public void startMyStore(){
		if(!startInit(LeftTabView.TAB_ITEM08)){
			myStore.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM08);
			
			if(userInfo.myStore.view==null){
				userInfo.myStore.init();
				userInfo.myStore.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.myStore, null,LeftTabView.TAB_ITEM08);
		}
	}
	/**
	 * ��personInfo
	 */
	public void startPersonInfo(){
		if(!startInit(LeftTabView.TAB_ITEM09)){
			personInfo.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM09);
			
			if(userInfo.personInfo.view==null){
				userInfo.personInfo.init();
				userInfo.personInfo.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.personInfo, null,LeftTabView.TAB_ITEM09);
		}
	}
	/**
	 * ��setting
	 */
	public void startSetting(){
		if(!startInit(LeftTabView.TAB_ITEM010)){
			setting.setBackgroundResource(R.color.yellow);
			
			//����ͼ
			setCurrentTab(LeftTabView.TAB_ITEM010);
			
			if(userInfo.setting.view==null){
				userInfo.setting.init();
				userInfo.setting.setMoveView(leftMoveView);
			}
			
			leftMoveView.setMainView(userInfo.setting,null,LeftTabView.TAB_ITEM010);
		}
	}
	public boolean startInit(int tabId){
		if(leftMoveView.getNowState()==LeftMoveView.MAIN){
			return true;
		}
		
		if(currentTab == tabId ){
			int now_state = leftMoveView.getNowState();
			if (now_state == LeftMoveView.MAIN) {
				leftMoveView.moveToLeft(true);
			} else {
				leftMoveView.moveToMain(true,0);
			}
			return true;
		}else{
			
			initLeftMenuBackGround();

			return false;
		}
	}
	/**
	 * ��ʼ����߲˵�����
	 */
	private void initLeftMenuBackGround(){
		this.questionBank.setBackgroundResource(R.color.white);
		this.myOrder.setBackgroundResource(R.color.white);
		this.aboutUs.setBackgroundResource(R.color.white);
		this.appraiseUs.setBackgroundResource(R.color.white);
		this.contactUs.setBackgroundResource(R.color.white);
		this.message.setBackgroundResource(R.color.white);
		this.myStore.setBackgroundResource(R.color.white);
		this.personInfo.setBackgroundResource(R.color.white);
		this.setting.setBackgroundResource(R.color.white);
	}

	public void setWidth(int w) { 
		// Ϊ��ʹ��������ֺ��� ����ҪΪĳ����п��fill_parent�Ŀؼ����þ�Կ�ȼ�������Ŀ�� 
		LayoutParams p = view.getLayoutParams();
		p.width = w;
		view.setLayoutParams(p);
		
		ViewGroup.LayoutParams params = accountInfo.getLayoutParams();
		params.width = w;
		accountInfo.setLayoutParams(params);
	}
	
	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public View getView() {
		return view;
	}
	
	
}
