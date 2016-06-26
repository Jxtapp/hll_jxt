package com.hll.entity;

public class QueueListItemBean {
	public int queueListNum;
	public String  queueListName;
	public int  queueListSureOne;
	public int  queueListSureTow;
	
	
	public QueueListItemBean() {
		super();
	}
	
	public QueueListItemBean(int queueListNum, String queueListName, int queueListSureOne, int queueListSureTow) {
		super();
		this.queueListNum = queueListNum;
		this.queueListName = queueListName;
		this.queueListSureOne = queueListSureOne;
		this.queueListSureTow = queueListSureTow;
	}

	public int getQueueListNum() {
		return queueListNum;
	}
	public void setQueueListNum(int queueListNum) {
		this.queueListNum = queueListNum;
	}
	public String getQueueListName() {
		return queueListName;
	}
	public void setQueueListName(String queueListName) {
		this.queueListName = queueListName;
	}
	public int getQueueListSureOne() {
		return queueListSureOne;
	}
	public void setQueueListSureOne(int queueListSureOne) {
		this.queueListSureOne = queueListSureOne;
	}
	public int getQueueListSureTow() {
		return queueListSureTow;
	}
	public void setQueueListSureTow(int queueListSureTow) {
		this.queueListSureTow = queueListSureTow;
	}
	
}
