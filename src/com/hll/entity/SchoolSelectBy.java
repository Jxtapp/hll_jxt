package com.hll.entity;
/**
 * 学校筛选
 * @author liaoyun
 * 2016-6-11
 */
public class SchoolSelectBy {
	private String tranAreaSp;//练车区域
	private String tranTypeSp;//练车类型
	private String orderTypeSp;//排序方式
	private int startPage;     //第几列
	private int pageSize;      //每页的记录条数
	public SchoolSelectBy() {
		super();
		this.startPage=1;
		this.pageSize=5;
	}
	public String getTranAreaSp() {
		return tranAreaSp;
	}
	public void setTranAreaSp(String tranAreaSp) {
		this.tranAreaSp = tranAreaSp;
	}
	public String getTranTypeSp() {
		return tranTypeSp;
	}
	public void setTranTypeSp(String tranTypeSp) {
		this.tranTypeSp = tranTypeSp;
	}
	public String getOrderTypeSp() {
		return orderTypeSp;
	}
	public void setOrderTypeSp(String orderTypeSp) {
		this.orderTypeSp = orderTypeSp;
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
