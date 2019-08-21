package com.zjy1001.androiddemotest.bean;

public class Joinactivity {
      private int j_id;
      private int a_id; //活动主办者id
      private String act_name;
      private String joinact_id;  //参加者id
      private String joinact_name;
      
	public int getJ_id() {
		return j_id;
	}
	public void setJ_id(int jId) {
		j_id = jId;
	}
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int aId) {
		a_id = aId;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String actName) {
		act_name = actName;
	}
	public String getJoinact_id() {
		return joinact_id;
	}
	public void setJoinact_id(String joinactId) {
		joinact_id = joinactId;
	}
	public String getJoinact_name() {
		return joinact_name;
	}
	public void setJoinact_name(String joinactName) {
		joinact_name = joinactName;
	}
      
}
