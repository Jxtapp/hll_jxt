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
	private LinearLayout logOff;

	private StudentInfoActivity studentInfo;
	private View view;
	private RightMoveView rightMoveView;
	private int currentTab = RightTabView.TAB_ITEM01;

	/**
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
		
		logOff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startLogOff();
			}
		});
	}

	/**
	 */
	public void startLoginSystem() {
		if (!startInit(RightTabView.TAB_ITEM01)) {
			loginSystem.setBackgroundResource(R.color.yellow);

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
	 */
	public void startFreeTime() {
		if (!startInit(RightTabView.TAB_ITEM02)) {
			freeTime.setBackgroundResource(R.color.yellow);
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
	 */
	public void startLearnPlan() {
		if (!startInit(RightTabView.TAB_ITEM03)) {
			learnPlan.setBackgroundResource(R.color.yellow);

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
	 */
	public void startOrderList() {
		if (!startInit(RightTabView.TAB_ITEM04)) {
			orderList.setBackgroundResource(R.color.yellow);
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
	 */
	public void startLogOff () {
		if (!startInit(RightTabView.TAB_ITEM12)) {
			logOff.setBackgroundResource(R.color.yellow);

			// ����ͼ
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
	 */
	private void initLeftMenuBackGround() {
		this.loginSystem.setBackgroundResource(R.color.white);
		this.freeTime.setBackgroundResource(R.color.white);
		this.learnPlan.setBackgroundResource(R.color.white);
		this.orderList.setBackgroundResource(R.color.white);
		this.logOff.setBackgroundResource(R.color.white);
	}

	public void setWidth(int w) {
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
