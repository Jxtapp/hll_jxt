package com.hll.entity;
/**
 * 学校筛选
 * @author liaoyun
 * 2016-6-11
 */
public class SchoolSelectBy {
	private String tranAreaSp; //练车区域
	private String orderType;  //排序方式
	private int startIndex;    //第几列
	private int pageSize;      //每页的记录条数
	public SchoolSelectBy() {
		super();
		this.startIndex=0;
		this.pageSize=5;
	}
	public String getTranAreaSp() {
		return tranAreaSp;
	}
	public void setTranAreaSp(String tranAreaSp) {
		this.tranAreaSp = tranAreaSp;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
}
