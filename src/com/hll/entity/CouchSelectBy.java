package com.hll.entity;

public class CouchSelectBy {

	private String carType;//练车类型
	private String tranAreaSp;//练车区域
	private String tranDistance;//距离(km)
	private int startPage;     //第几列
	private int pageSize;      //每页的记录条数
	public CouchSelectBy() {
		super();
		this.startPage=1;
		this.pageSize=5;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getTranAreaSp() {
		return tranAreaSp;
	}
	public void setTranAreaSp(String tranAreaSp) {
		this.tranAreaSp = tranAreaSp;
	}
	public String getTranDistance() {
		return tranDistance;
	}
	public void setTranDistance(String tranDistance) {
		this.tranDistance = tranDistance;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
