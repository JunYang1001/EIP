package com.zjy1001.androiddemotest.bean;

import java.io.Serializable;

public class Actjoiner implements Serializable {
	 private int j_id;
     private int a_id; //活动主办者id
     private String act_name;
     private String joinact_id;  //参加者id
     private String joinact_name;
	 private int u_id;
     private String user_id;
     private String user_name;
     private String user_password;
     private String user_sex;
     private int user_age;
     private String user_email;
     private String user_city;
     private long user_phone;
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
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int uId) {
		u_id = uId;
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
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String userPassword) {
		user_password = userPassword;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String userSex) {
		user_sex = userSex;
	}
	public int getUser_age() {
		return user_age;
	}
	public void setUser_age(int userAge) {
		user_age = userAge;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String userEmail) {
		user_email = userEmail;
	}
	public String getUser_city() {
		return user_city;
	}
	public void setUser_city(String userCity) {
		user_city = userCity;
	}
	public long getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(long userPhone) {
		user_phone = userPhone;
	}
     
     
}
