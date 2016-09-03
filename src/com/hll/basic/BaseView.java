package com.hll.basic;

import android.view.View;

public class BaseView {
	public boolean isScroll=true;//
	public View view;//
	public RightMoveView rightMoveView;
	/**
	 * @param leftMoveView
	 */
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
