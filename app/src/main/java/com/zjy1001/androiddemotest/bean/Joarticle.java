package com.zjy1001.androiddemotest.bean;

import java.io.Serializable;

public class Joarticle  implements Serializable {
	 private int c_id;
	 private int art_id;
     private String article_name;
     private String article_detail;
     private String article_time;
     private String user_id;
     private String user_name;
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int cId) {
		c_id = cId;
	}
	public int getArt_id() {
		return art_id;
	}
	public void setArt_id(int artId) {
		art_id = artId;
	}
	public String getArticle_name() {
		return article_name;
	}
	public void setArticle_name(String articleName) {
		article_name = articleName;
	}
	public String getArticle_detail() {
		return article_detail;
	}
	public void setArticle_detail(String articleDetail) {
		article_detail = articleDetail;
	}
	public String getArticle_time() {
		return article_time;
	}
	public void setArticle_time(String articleTime) {
		article_time = articleTime;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
     
}
