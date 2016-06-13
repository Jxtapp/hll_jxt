package com.hll.entity;

import java.util.Date;

import com.hll.entity.BaseEntity;
/**
 * 登陆用户信息
 * @author liaoyun
 * 2016-3-18
 */
public class UserO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2734291319832271162L;
	private long id;
	private String account;       //账号  唯一编号
	private String tel;
	private String email;
	private String qq;
	private String weChat;
	private String password;
	private String leader;        //上级账号
	private int type;             //用户类型:1、systemAdmin;2、systememployee;3driverscholladmin;4、driverschollemploye;5、coustomuser
	private String nickName;      //昵称
	public UserO() {
		super();
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWeChat() {
		return weChat;
	}
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}	
}
