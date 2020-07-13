package com.yww.doggie.entity;

public class SanStar {
	
	private String oid;
	private int user_oid;
	private String user_nick_name;
	private String content;
	private int hug_num;
	private int calm_num;
	private int comment_num;
	private String pub_time;
	
	public String getPub_time() {
		return pub_time;
	}
	public void setPub_time(String pub_time) {
		String timeStr = pub_time.replace(".0", "");
		this.pub_time = timeStr;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public int getUser_oid() {
		return user_oid;
	}
	public void setUser_oid(int user_oid) {
		this.user_oid = user_oid;
	}
	public String getUser_nick_name() {
		return user_nick_name;
	}
	public void setUser_nick_name(String user_nick_name) {
		this.user_nick_name = user_nick_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHug_num() {
		return hug_num;
	}
	public void setHug_num(int hug_num) {
		this.hug_num = hug_num;
	}
	public int getCalm_num() {
		return calm_num;
	}
	public void setCalm_num(int calm_num) {
		this.calm_num = calm_num;
	}
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	
}
