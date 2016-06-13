package com.hll.jxtapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.hll.basic.RightMoveView;
import com.hll.basic.RightTabView;
public class RightMenuView {
	private LinearLayout rightTitle;
	private LinearLayout loginSystem;
	private LinearLayout freeTime;
	private LinearLayout learnPlan;
	private LinearLayout orderList;
	private LinearLayout examPlan;
	private LinearLayout errorExercise;
	private LinearLayout remindExercise;
	private LinearLayout friendManage;
	private LinearLayout myTopic;
	private LinearLayout news;
	private LinearLayout recommendCode;
	private LinearLayout logOff;

	private StudentInfoActivity studentInfo;
	private View view;
	private RightMoveView rightMoveView;
	private int currentTab = RightTabView.TAB_ITEM01;

	/**
	 * 构造初始化
	 * 
	 * @param userInfo2
	 * @param moveView
	 */
	public RightMenuView(StudentInfoActivity studentInfo, RightMoveView rightMoveView) {
		this.studentInfo = studentInfo;
		this.rightMoveView = rightMoveView;
		this.view = LayoutInflater.from(studentInfo).inflate(
				R.layout.rightmenu, null);
		createView();
		setViewProperty();
	}

	public void createView() {
		rightTitle=(LinearLayout) view.findViewById(R.id.id_titleOfRight);
		loginSystem = (LinearLayout) view.findViewById(R.id.id_loginSystem);
		freeTime = (LinearLayout) view.findViewById(R.id.id_freeTime);
		learnPlan = (LinearLayout) view.findViewById(R.id.id_learnPlan);
		orderList = (LinearLayout) view.findViewById(R.id.id_orderList);
		examPlan = (LinearLayout) view.findViewById(R.id.id_examPlan);
		errorExercise = (LinearLayout) view.findViewById(R.id.id_errorExercise);
		remindExercise = (LinearLayout) view
				.findViewById(R.id.id_remindExercise);
		friendManage = (LinearLayout) view.findViewById(R.id.id_friendManage);
		myTopic = (LinearLayout) view.findViewById(R.id.id_myTopic);
		news = (LinearLayout) view.findViewById(R.id.id_news);
		recommendCode = (LinearLayout) view.findViewById(R.id.id_recommendCode);
		logOff = (LinearLayout) view.findViewById(R.id.id_logOff);
	}

	public void setViewProperty() {
		loginSystem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startLoginSystem();
			}
		});

		freeTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startFreeTime();
			}
		});

		learnPlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startLearnPlan();
			}
		});

		orderList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startOrderList();
			}
		});
		
		examPlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startExamPlan();
			}
		});
		
		errorExercise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startErrorExercise();
			}
		});

		remindExercise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startRemindExercise();
			}
		});

		friendManage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startFriendManage();
			}
		});

		myTopic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startMyTopic();
			}
		});

		news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startNews();
			}
		});

		recommendCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startRecommendCode();
			}
		});
		
		logOff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startLogOff();
			}
		});
	}

	/**
	 * 进入loginSystem
	 */
	public void startLoginSystem() {
		if (!startInit(RightTabView.TAB_ITEM01)) {
			loginSystem.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM01);
			if (studentInfo.loginSystem.view == null) {
				studentInfo.loginSystem.init();
				studentInfo.loginSystem.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.loginSystem,null,
					RightTabView.TAB_ITEM01);
		}
	}

	/**
	 * 打开freeTime
	 */
	public void startFreeTime() {
		if (!startInit(RightTabView.TAB_ITEM02)) {
			freeTime.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM02);

			if (studentInfo.freeTime.view == null) {
				studentInfo.freeTime.init();
				studentInfo.freeTime.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.freeTime,null,
					RightTabView.TAB_ITEM02);
		}
	}

	/**
	 * 打开learnPlan
	 */
	public void startLearnPlan() {
		if (!startInit(RightTabView.TAB_ITEM03)) {
			learnPlan.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM03);

			if (studentInfo.learnPlan.view == null) {
				studentInfo.learnPlan.init();
				studentInfo.learnPlan.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.learnPlan,null,
					RightTabView.TAB_ITEM03);
		}
	}
	/**
	 * 打开orderList
	 */
	public void startOrderList() {
		if (!startInit(RightTabView.TAB_ITEM04)) {
			orderList.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM04);

			if (studentInfo.orderList.view == null) {
				studentInfo.orderList.init();
				studentInfo.orderList.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.orderList,null,
					RightTabView.TAB_ITEM04);
		}
	}

	/**
	 * 打开examPlan
	 */
	public void startExamPlan() {
		if (!startInit(RightTabView.TAB_ITEM05)) {
			examPlan.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM05);

			if (studentInfo.examPlan.view == null) {
				studentInfo.examPlan.init();
				studentInfo.examPlan.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.examPlan,null,
					RightTabView.TAB_ITEM05);
		}
	}

	/**
	 * 打开errorExercise
	 */
	public void startErrorExercise() {
		if (!startInit(RightTabView.TAB_ITEM06)) {
			errorExercise.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM06);

			if (studentInfo.errorExercise.view == null) {
				studentInfo.errorExercise.init();
				studentInfo.errorExercise.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.errorExercise,null,
					RightTabView.TAB_ITEM06);
		}
	}

	/**
	 * 打开remindExercise
	 */
	public void startRemindExercise() {
		if (!startInit(RightTabView.TAB_ITEM07)) {
			remindExercise.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM07);

			if (studentInfo.remindExercise.view == null) {
				studentInfo.remindExercise.init();
				studentInfo.remindExercise.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.remindExercise,null,
					RightTabView.TAB_ITEM07);
		}
	}

	/**
	 * 打开friendManage
	 */
	public void startFriendManage() {
		if (!startInit(RightTabView.TAB_ITEM08)) {
			friendManage.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM08);

			if (studentInfo.friendManage.view == null) {
				studentInfo.friendManage.init();
				studentInfo.friendManage.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.friendManage,null,
					RightTabView.TAB_ITEM08);
		}
	}

	/**
	 * 打开myTopic
	 */
	public void startMyTopic() {
		if (!startInit(RightTabView.TAB_ITEM09)) {
			myTopic.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM09);

			if (studentInfo.myTopic.view == null) {
				studentInfo.myTopic.init();
				studentInfo.myTopic.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.myTopic,null,
					RightTabView.TAB_ITEM09);
		}
	}

	/**
	 * 打开news
	 */
	public void startNews() {
		if (!startInit(RightTabView.TAB_ITEM10)) {
			news.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM10);

			if (studentInfo.news.view == null) {
				studentInfo.news.init();
				studentInfo.news.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.news,null,
					RightTabView.TAB_ITEM10);
		}
	}

	/**
	 * 打开recommendCode
	 */
	public void startRecommendCode() {
		if (!startInit(RightTabView.TAB_ITEM11)) {
			recommendCode.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM11);

			if (studentInfo.recommendCode.view == null) {
				studentInfo.recommendCode.init();
				studentInfo.recommendCode.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.recommendCode,null,
					RightTabView.TAB_ITEM11);
		}
	}

	/**
	 * 打开logOff 
	 */
	public void startLogOff () {
		if (!startInit(RightTabView.TAB_ITEM12)) {
			logOff.setBackgroundResource(R.color.yellow);

			// 打开视图
			setCurrentTab(RightTabView.TAB_ITEM12);

			if (studentInfo.logOff.view == null) {
				studentInfo.logOff.init();
				studentInfo.logOff.setRightMoveView(rightMoveView);
			}

			rightMoveView.setMainView(studentInfo.logOff,null,
					RightTabView.TAB_ITEM12);
		}
	}

	public boolean startInit(int tabId) {
		if (rightMoveView.getNowState() ==RightMoveView.MAIN) {
			return true;
		}

		if (currentTab == tabId) {
			int now_state = rightMoveView.getNowState();
			if (now_state == RightMoveView.MAIN) {
				rightMoveView.moveToLeft(true);
			} else {
				rightMoveView.moveToMain(true, 0);
			}
			return true;
		} else {

			initLeftMenuBackGround();

			return false;
		}
	}

	/**
	 * 初始化左边菜单背景
	 */
	private void initLeftMenuBackGround() {
		this.loginSystem.setBackgroundResource(R.color.white);
		this.freeTime.setBackgroundResource(R.color.white);
		this.learnPlan.setBackgroundResource(R.color.white);
		this.orderList.setBackgroundResource(R.color.white);
		this.examPlan.setBackgroundResource(R.color.white);
		this.errorExercise.setBackgroundResource(R.color.white);
		this.remindExercise.setBackgroundResource(R.color.white);
		this.friendManage.setBackgroundResource(R.color.white);
		this.myTopic.setBackgroundResource(R.color.white);
		this.news.setBackgroundResource(R.color.white);
		this.recommendCode.setBackgroundResource(R.color.white);
		this.logOff.setBackgroundResource(R.color.white);
	}

	public void setWidth(int w) {
		// 为了使侧边栏布局合理 所以要为某项布局中宽度fill_parent的控件设置绝对宽度即侧边栏的宽度
		LayoutParams p = view.getLayoutParams();
		p.width = w;
		view.setLayoutParams(p);

		ViewGroup.LayoutParams params = rightTitle.getLayoutParams();
		params.width = w;
		rightTitle.setLayoutParams(params);
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
