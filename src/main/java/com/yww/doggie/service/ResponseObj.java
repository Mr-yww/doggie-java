package com.yww.doggie.service;

import java.util.Date;

public class ResponseObj {

	private int errorCode = 0;
	private String info = "OK";
	private Date ts = new java.util.Date();
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
}
