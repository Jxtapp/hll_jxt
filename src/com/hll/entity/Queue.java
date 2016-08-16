package com.hll.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 排队相关信息
 * @author liaoyun 2016-8-14
 */
public class Queue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8723534562932647503L;
	private long id;
	private String schoolAccount;   //驾校账号
	private String schoolName;      //驾校名称
	private String placeId;         //场地Id
	private String placeName;       //场地名称
	private String userAccount;     //用户账号
	private String userName;        //用户姓名
	private String userGender;      //用户性别
	private String userAge;         //用户年龄
	private String userNickName;    //用户昵称
	private String userPic;         //用户头像
	private String userTel;         //用户电话
	private String serverTime;      //服务器时间  yyyy-MM-dd HH:mm:ss
	private int mySet;              //用户排队序号
	private int isTraining;         //是否正在训练
	private int sureTime;           //确认的次数
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	public Queue() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserAge() {
		return userAge;
	}
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserPic() {
		return userPic;
	}
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	public int getMySet() {
		return mySet;
	}
	public void setMySet(int mySet) {
		this.mySet = mySet;
	}
	public int getIsTraining() {
		return isTraining;
	}
	public void setIsTraining(int isTraining) {
		this.isTraining = isTraining;
	}
	
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	public int getSureTime() {
		return sureTime;
	}
	public void setSureTime(int sureTime) {
		this.sureTime = sureTime;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
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
	public void trim(){
		setSchoolAccount(getValue(getSchoolAccount()));
		setSchoolName(getValue(getSchoolName()));
		setPlaceId(getValue(getPlaceId()));
		setPlaceName(getValue(getPlaceName()));
		setUserAccount(getValue(getUserAccount()));
		setUserName(getValue(getUserName()));
		setUserGender(getValue(getUserGender()));
		setUserAge(getValue(getUserAge()));
		setUserNickName(getValue(getUserNickName()));
		setUserPic(getValue(getUserPic()));
		setServerTime(getValue(getServerTime()));
	}
	private String getValue(String s){
		if(s==null){
			return null;
		}else{
			return s.trim();
		}
	}
}
