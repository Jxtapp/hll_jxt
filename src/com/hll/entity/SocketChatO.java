package com.hll.entity;

import java.io.Serializable;

/**
 * websocket 聊天 记录 实体
 * @author liaoyun 2016-8-21
 */
public class SocketChatO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9009870696495141896L;
	private long id;          //主键
	private String placeId;   //场地id
	private String account;   //发信人账号
	private String name;      //发信人名字
	private String nickName;  //发信人昵称
	private String content;   //信息内容
	private String sendTime;  //发送信息的时间，服务器转发的时间 yyyy-MM-dd HH:mm:ss
	public SocketChatO() {
		super();
	}
	public SocketChatO(String placeId, String account,String name,String nickName,String content,String sendTime) {
		this.placeId = placeId;
		this.account = account;
		this.name = name;
		this.nickName = nickName;
		this.content = content;
		this.sendTime = sendTime;
	}
	public SocketChatO(long id,String placeId, String account,String name,String nickName,String content,String sendTime) {
		this.id = id;
		this.placeId = placeId;
		this.account = account;
		this.name = name;
		this.nickName = nickName;
		this.content = content;
		this.sendTime = sendTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
}
