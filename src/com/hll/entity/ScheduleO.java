package com.hll.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * @author liaoyun
 * 2016-8-9
 */
public class ScheduleO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8553081487080779098L;
	private long id;
	private String userAccount;   //学员账号
	private String schoolAccount; //驾校账号
	private String schoolName;    //驾校名称
	private int am;               //上午是否预约：0--没有预约;1--已经预约
	private int pm;				  //下午是否预约：0--没有预约;1--已经预约
	private int ev;	              //晚上是否预约：0--没有预约;1--已经预约
	private String subj;          //训练科目
	private String placeId;       //场地id
	private String placeName;     //场地名称
	private Date   orderDate;     //预约时间
	private String createdBy;     //创建时间，即创建记录时的服务器时间
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	public ScheduleO() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getSchoolAccount() {
		return schoolAccount;
	}
	public void setSchoolAccount(String schoolAccount) {
		this.schoolAccount = schoolAccount;
	}
	public int getAm() {
		return am;
	}
	public void setAm(int am) {
		this.am = am;
	}
	public int getPm() {
		return pm;
	}
	public void setPm(int pm) {
		this.pm = pm;
	}
	public int getEv() {
		return ev;
	}
	public void setEv(int ev) {
		this.ev = ev;
	}
	public String getSubj() {
		return subj;
	}
	public void setSubj(String subj) {
		this.subj = subj;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
