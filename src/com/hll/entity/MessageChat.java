package com.hll.entity;
/**
 * 消息实体
 * @author heyi
 *
 */
public class MessageChat {
	public String schollAccount;
	public String placeName;
	public String userAccount;
	public String msg;
	public String sendTime;
	public String getSchollAccount() {
		return schollAccount;
	}
	public void setSchollAccount(String schollAccount) {
		this.schollAccount = schollAccount;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
