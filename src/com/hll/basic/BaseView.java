package com.hll.basic;

import android.view.View;

public class BaseView {
	public boolean isScroll=true;//是否可以滚动
	public View view;//主界面视图
	public LeftMoveView leftMoveView;//移动视图
	public RightMoveView rightMoveView;
	/**
	 * 赋值移动视图
	 * @param leftMoveView
	 */
	public void setMoveView(LeftMoveView leftMoveView) {
		this.leftMoveView = leftMoveView;
	}
	public void setRightMoveView(RightMoveView rightMoveView) {
		this.rightMoveView = rightMoveView;
	}
	public boolean isScroll() {
		return isScroll;
	}

	public void setScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
}
