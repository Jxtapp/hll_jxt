package com.hll.entity;


public class SecPageItemBean {

	public String coachSelfImg;
	public int  coachPrice;
	public String  teachType;
	public String  orderLevel;
	
	public SecPageItemBean() {
		super();
	}

	public SecPageItemBean(String coachSelfImg,int coachPrice,String teachType
			,String orderLevel){
		this.coachSelfImg=coachSelfImg;
		this.coachPrice=coachPrice;
		this.teachType=teachType;
		this.orderLevel=orderLevel;
	}


	public String getCoachSelfImg() {
		return coachSelfImg;
	}


	public void setCoachSelfImg(String coachSelfImg) {
		this.coachSelfImg = coachSelfImg;
	}


	public int getCoachPrice() {
		return coachPrice;
	}


	public void setCoachPrice(int coachPrice) {
		this.coachPrice = coachPrice;
	}


	public String getTeachType() {
		return teachType;
	}


	public void setTeachType(String teachType) {
		this.teachType = teachType;
	}




	public String getOrderLevel() {
		return orderLevel;
	}


	public void setOrderLevel(String orderLevel) {
		this.orderLevel = orderLevel;
	}

}
