package com.hll.entity;

import java.io.Serializable;

public class SchoolDetailInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6417171573121942922L;
	private String topPIc;                          //页面最上面的图片
	private String schoolAccount;                   //学校账号
	private String schoolName;                      //学校名称
	private String placeId;                         //场地id
	private String placeName;                       //场地名称
	private String tel;                             //电话
	private String qq;                              //QQ
	private String wechat;                          //微信
	private double price;                           //C1 价格
	private String schoolAddress;                   //驾校地址
	private String placeAddress;                    //场地地址
	private String bugget;                          //优惠活动信息
	private String schoolInfo;                      //学校详情
	private String placeRemark;                     //驾校备注信息
	public SchoolDetailInfo() {
		super();
	}
	public String getSchoolAccount() {
		return schoolAccount;
	}
	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
	public String getPlaceAddress() {
		return placeAddress;
	}
	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}
	public String getBugget() {
		return bugget;
	}
	public void setBugget(String bugget) {
		this.bugget = bugget;
	}
	public String getSchoolInfo() {
		return schoolInfo;
	}
	public void setSchoolInfo(String schoolInfo) {
		this.schoolInfo = schoolInfo;
	}
	public String getPlaceRemark() {
		return placeRemark;
	}
	public void setPlaceRemark(String placeRemark) {
		this.placeRemark = placeRemark;
	}
	public String getTopPIc() {
		return topPIc;
	}
	public void setTopPIc(String topPIc) {
		this.topPIc = topPIc;
	}
	public void nullToSpace() {
		setPlaceId(getValue(getPlaceId()));
		setPlaceName(getValue(getPlaceName()));
		setTel(getValue(getTel()));
		setQq(getValue(getQq()));
		setWechat(getValue(getWechat()));
		setSchoolAddress(getValue(getSchoolAddress()));
		setPlaceAddress(getValue(getPlaceAddress()));
		setBugget(getValue(getBugget()));
		setSchoolInfo(getValue(getSchoolInfo()));
		setPlaceRemark(getValue(getPlaceRemark()));
		setTopPIc(getValue(getTopPIc()));
	}
	private String getValue(String str){
		if(str==null){
			return "";
		}else{
			return str.trim();
		}
	}
}
