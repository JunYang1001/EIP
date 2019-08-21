package com.zjy1001.androiddemotest.bean;

public class Joinarticle {
      private int c_id;
      private int crart_id; //文章发布者id
      private String crart_name;
      private String joinart_id;  //参加者id
      private String joinart_name;
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int cId) {
		c_id = cId;
	}
	public int getCrart_id() {
		return crart_id;
	}
	public void setCrart_id(int crartId) {
		crart_id = crartId;
	}
	public String getCrart_name() {
		return crart_name;
	}
	public void setCrart_name(String crartName) {
		crart_name = crartName;
	}
	public String getJoinart_id() {
		return joinart_id;
	}
	public void setJoinart_id(String joinartId) {
		joinart_id = joinartId;
	}
	public String getJoinart_name() {
		return joinart_name;
	}
	public void setJoinart_name(String joinartName) {
		joinart_name = joinartName;
	}
      
	
}
