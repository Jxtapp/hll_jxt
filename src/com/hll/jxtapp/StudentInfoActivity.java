package com.hll.jxtapp;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.hll.basic.RightMoveView;
import com.hll.basic.RightTabView;
import com.hll.basic.LeftTabView;
import com.hll.right.ErrorExerciseView;
import com.hll.right.ExamPlanView;
import com.hll.right.FriendManageView;
import com.hll.right.LogOffView;
import com.hll.right.LoginSystemView;
import com.hll.right.FreeTimeView;
import com.hll.right.LearnPlanView;
import com.hll.right.MyTopicView;
import com.hll.right.NewsView;
import com.hll.right.OrderListView;
import com.hll.right.RecommendCodeView;
import com.hll.right.RemindExerciseView;

public class StudentInfoActivity extends Activity implements  Serializable{

	
	private static final long serialVersionUID = 1L;
	private Context context;
	private RightMoveView rightmoveView;
	private RightMenuView rightMenuView;
	public LoginSystemView loginSystem;
	public FreeTimeView freeTime;
	public LearnPlanView learnPlan;
	public ErrorExerciseView errorExercise;
	public ExamPlanView examPlan;
	public FriendManageView friendManage;
	public LogOffView logOff;
	public MyTopicView myTopic;
	public NewsView news;
	public OrderListView orderList;
	public RecommendCodeView recommendCode;
	public RemindExerciseView remindExercise;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.context = this;
		
		//创建移动视图
		rightmoveView = new RightMoveView(context);
		//创建左边菜单视图
		rightMenuView = new RightMenuView(this, rightmoveView);
		
		/****************************************************/
		//界面01
		loginSystem = new LoginSystemView(context);
		loginSystem.setRightMoveView(rightmoveView);//赋值移动视图
		loginSystem.init();
		/***************************************************/
		
		freeTime = new FreeTimeView(context);
		learnPlan = new LearnPlanView(context);
		errorExercise = new ErrorExerciseView(context);
		examPlan = new ExamPlanView(context);
		friendManage = new FriendManageView(context);
		logOff = new LogOffView(context);
		myTopic = new MyTopicView(context);
		news = new NewsView(context);
		orderList = new OrderListView(context);
		recommendCode = new RecommendCodeView(context);
		remindExercise = new RemindExerciseView(context);
		
		//移动视图默认界面：界面01，
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
