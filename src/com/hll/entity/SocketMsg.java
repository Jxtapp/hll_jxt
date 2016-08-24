package com.hll.entity;

import java.io.Serializable;
import java.util.List;

/**
 * websocekt 发送信息规则
 * @author liaoyun 2016-8-19
 */
public class SocketMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3534291884914592098L;
	/**
	 * 场景 普通对话
	 */
	public static final int  SCENE_CHAT = 1;     
	/**
	 * 场景 排队信息
	 */
	public static final int  SCENE_QUEUE = 2;  
	/**
	 * 场景登陆  websocket
	 */
	public static final int SCENE_LOGIN = 3;
	/**
	 * 类型 普通转发
	 */
	public static final int  TYPE_TRANSMIT = 1;  
	/**
	 * 类型 广播
	 */
	public static final int  TYPE_BROADCAST = 2; 
	
	private String       account; //user account
	private String       name;    //发信人name
	private String       nickName;//发信人昵称
	private Integer      key;     //websocket key
	private Integer      scene;   //场景：         1--普通对话；2--排队信息 ; 3--websocket 登陆
	private Integer      type;    //转发类型： 1--普通转发(发送对象的用户account放在users中)；2--广播，发给所有的人(list中不需要放信息)
	private List<String> users;
	private String       message;
	private String       time;    //转发时间，yyyy-MM-dd HH:mm:ss
	public SocketMsg() {
		super();
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public Integer getScene() {
		return scene;
	}
	public void setScene(Integer scene) {
		this.scene = scene;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
}
