package com.hll.entity;

import java.io.Serializable;

/**
 * driver school recommend listView entity
 * @author liaoyun
 * 2016-6-1
 */
public class RecommendSchoolInfoO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7424415106905403954L;
	private String schoolAccount;
	private String schoolname;
	private String placeId;
	private String itemImg;
	private double itemPrice;
	private String itemAddress;
	private String itemNum;
	
	public RecommendSchoolInfoO() {
		super();
	}
	public RecommendSchoolInfoO(String itemImg, double itemPrice, String itemAddress, String itemNum,String schoolName, String placeId) {
		super();
		this.itemImg = itemImg;
		this.itemPrice = itemPrice;
		this.itemAddress = itemAddress;
		this.itemNum = itemNum;
		this.schoolname = schoolName;
		this.placeId = placeId;
	}
	public String getItemImg() {
		return itemImg;
	}
	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}
	public String getItemAddress() {
		return itemAddress;
	}
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	public String getSchoolAccount() {
		return schoolAccount;
	}
	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
}
