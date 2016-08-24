package com.hll.entity;

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
	private String name;          //姓名
	private int age;              //年龄
	private String address;       //住址
	private String tel;
	private String email;
	private String qq;
	private String weChat;
	private String password;
	private String leader;        //上级账号
	private int type;             //用户类型:1、systemAdmin;2、systememployee;3driverscholladmin;4、driverschollemploye;5、coustomuser
	private String nickName;      //昵称
	private String lastLoadTime;  //最近登陆时的服务器时间
	private String lastLoadIp;    //最近登陆的ip
	private String lastLoadPort;  //最近登陆的端口号
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
	public String getLastLoadTime() {
		return lastLoadTime;
	}
	public void setLastLoadTime(String lastLoadTime) {
		this.lastLoadTime = lastLoadTime;
	}
	public String getLastLoadIp() {
		return lastLoadIp;
	}
	public void setLastLoadIp(String lastLoadIp) {
		this.lastLoadIp = lastLoadIp;
	}
	public String getLastLoadPort() {
		return lastLoadPort;
	}
	public void setLastLoadPort(String lastLoadPort) {
		this.lastLoadPort = lastLoadPort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
