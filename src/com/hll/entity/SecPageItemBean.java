package com.hll.entity;

import java.util.List;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SecPageItemBean {

	public int coachSelfImg;
	public int  coachPrice;
	public String  teachType;
	public int  orderTimes;
	public String  moreInfo;
	
	public SecPageItemBean(int coachSelfImg,int coachPrice,String teachType
			,int orderTimes,String moreInfo){
		this.coachSelfImg=coachSelfImg;
		this.coachPrice=coachPrice;
		this.teachType=teachType;
		this.orderTimes=orderTimes;
		this.moreInfo=moreInfo;
	}



}
