package com.hll.basic;

import android.view.View;

public class BaseView {
	public boolean isScroll=true;//�Ƿ���Թ���
	public View view;//��������ͼ
	public LeftMoveView leftMoveView;//�ƶ���ͼ
	public RightMoveView rightMoveView;
	/**
	 * ��ֵ�ƶ���ͼ
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
