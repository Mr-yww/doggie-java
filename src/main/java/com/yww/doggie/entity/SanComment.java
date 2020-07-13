package com.yww.doggie.entity;

public class SanComment {

	private String oid;
	private String star_oid;
	private String comment_time;
	private String content;
	private int from_user_oid;
	private String from_user_name;
	private int to_user_oid;
	private String to_user_name;
	private int zan_num;
	
	public int getZan_num() {
		return zan_num;
	}
	public void setZan_num(int zan_num) {
		this.zan_num = zan_num;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getStar_oid() {
		return star_oid;
	}
	public void setStar_oid(String star_oid) {
		this.star_oid = star_oid;
	}
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		String timeStr = comment_time.replace(".0", "");
		this.comment_time = timeStr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFrom_user_oid() {
		return from_user_oid;
	}
	public void setFrom_user_oid(int from_user_oid) {
		this.from_user_oid = from_user_oid;
	}
	public String getFrom_user_name() {
		return from_user_name;
	}
	public void setFrom_user_name(String from_user_name) {
		this.from_user_name = from_user_name;
	}
	public int getTo_user_oid() {
		return to_user_oid;
	}
	public void setTo_user_oid(int to_user_oid) {
		this.to_user_oid = to_user_oid;
	}
	public String getTo_user_name() {
		return to_user_name;
	}
	public void setTo_user_name(String to_user_name) {
		this.to_user_name = to_user_name;
	}
	
	
	
}
