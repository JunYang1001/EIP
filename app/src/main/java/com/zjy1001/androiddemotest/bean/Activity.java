package com.zjy1001.androiddemotest.bean;

import java.io.Serializable;

public class Activity implements Serializable {
       private int a_id;
       private String act_name;
       private String act_time;
       private String act_place;
       private int act_number;
       private String act_type;
       private String act_createid;
       private String act_createname;
       private String act_detail;
      
	   
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
	public String getAct_time() {
		return act_time;
	}
	public void setAct_time(String actTime) {
		act_time = actTime;
	}
	public String getAct_place() {
		return act_place;
	}
	public void setAct_place(String actPlace) {
		act_place = actPlace;
	}
	public int getAct_number() {
		return act_number;
	}
	public void setAct_number(int actNumber) {
		act_number = actNumber;
	}
	public String getAct_type() {
		return act_type;
	}
	public void setAct_type(String actType) {
		act_type = actType;
	}
	public String getAct_createid() {
		return act_createid;
	}
	public void setAct_createid(String actCreateid) {
		act_createid = actCreateid;
	}
	public String getAct_createname() {
		return act_createname;
	}
	public void setAct_createname(String actCreatename) {
		act_createname = actCreatename;
	}
	public String getAct_detail() {
		return act_detail;
	}
	public void setAct_detail(String actDetail) {
		act_detail = actDetail;
	}

}
