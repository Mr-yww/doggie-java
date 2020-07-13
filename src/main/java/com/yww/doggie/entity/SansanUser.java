package com.yww.doggie.entity;

public class SansanUser {

	private int oid;
	private String nick_name;
	private String regist_time;
	private String password;
	private String token;
	
	public SansanUser() {
		
	}
	public SansanUser(int oid, String nick_name, String regist_time, String password, String token) {
		this.oid = oid;
		this.nick_name = nick_name;
		this.regist_time = regist_time;
		this.password = password;
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getRegist_time() {
		return regist_time;
	}
	public void setRegist_time(String regist_time) {
		String timeStr = regist_time.replace(".0", "");
		this.regist_time = timeStr;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
