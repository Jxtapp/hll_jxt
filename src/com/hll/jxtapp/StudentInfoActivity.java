package com.hll.jxtapp;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.hll.basic.RightMoveView;
import com.hll.basic.RightTabView;
import com.hll.right.LogOffView;
import com.hll.right.LoginSystemView;
import com.hll.right.FreeTimeView;
import com.hll.right.LearnPlanView;
import com.hll.right.OrderListView;

public class StudentInfoActivity extends Activity implements  Serializable{
	
	private static final long serialVersionUID = 1L;
	private Context context;
	private RightMoveView rightmoveView;
	private RightMenuView rightMenuView;
	public LoginSystemView loginSystem;
	public FreeTimeView freeTime;
	public LearnPlanView learnPlan;
	public LogOffView logOff;
	public OrderListView orderList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.context = this;
		
		rightmoveView = new RightMoveView(context);
		rightMenuView = new RightMenuView(this, rightmoveView);
		
		/****************************************************/
		loginSystem = new LoginSystemView(context);
		loginSystem.setRightMoveView(rightmoveView);//
		loginSystem.init();
		/***************************************************/
		
		freeTime = new FreeTimeView(context);
		learnPlan = new LearnPlanView(context);
		logOff = new LogOffView(context);
		orderList = new OrderListView(context);
		
		rightmoveView.setMainView(loginSystem, rightMenuView, RightTabView.TAB_ITEM01);
		rightmoveView.getRight_show_view().setCurrentTab(RightTabView.TAB_ITEM01);

		setContentView(rightmoveView);
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
