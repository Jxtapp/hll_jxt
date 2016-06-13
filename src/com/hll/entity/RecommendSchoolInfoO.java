package com.hll.entity;
/**
 * driver school recommend listView entity
 * @author liaoyun
 * 2016-6-1
 */
public class RecommendSchoolInfoO {
	private String schoolAccount;
	private String schoolname;
	private String itemImg;
	private double itemPrice;
	private String itemAddress;
	private String itemNum;
	
	public RecommendSchoolInfoO() {
		super();
	}
	public RecommendSchoolInfoO(String itemImg, double itemPrice, String itemAddress, String itemNum) {
		super();
		this.itemImg = itemImg;
		this.itemPrice = itemPrice;
		this.itemAddress = itemAddress;
		this.itemNum = itemNum;
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
}
