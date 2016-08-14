package com.hll.entity;

import java.io.Serializable;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4803792318287438457L;
	private String code;
	private String desc;
	public Item() {
		super();
	}
	public Item(String code,String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return desc;
	}
}
