package com.hll.entity;
/**
 * 聊天内容中，每天记录实体类
 * @author heyi
 * 2016/8/13
 *
 */
public class ItemOfChatContentBean {
 public String nameOfPersonInChatroom;
 public String contentOfItemInChatroom;
public ItemOfChatContentBean() {
	super();
}
public ItemOfChatContentBean(String nameOfPersonInChatroom, String contentOfItemInChatroom) {
	super();
	this.nameOfPersonInChatroom = nameOfPersonInChatroom;
	this.contentOfItemInChatroom = contentOfItemInChatroom;
}
public String getNameOfPersonInChatroom() {
	return nameOfPersonInChatroom;
}
public void setNameOfPersonInChatroom(String nameOfPersonInChatroom) {
	this.nameOfPersonInChatroom = nameOfPersonInChatroom;
}
public String getContentOfItemInChatroom() {
	return contentOfItemInChatroom;
}
public void setContentOfItemInChatroom(String contentOfItemInChatroom) {
	this.contentOfItemInChatroom = contentOfItemInChatroom;
}
 
}
