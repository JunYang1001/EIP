package com.zjy1001.androiddemotest.bean;

import java.io.Serializable;

public class User  implements Serializable{
     private int u_id;
     private String user_id;
     private String user_name;
     private String user_password;
     private String user_sex;
     private int user_age;
     private String user_email;
     private String user_city;
     private String user_phone;
    
     


	public int getUser_age() {
		return user_age;
	}
	public void setUser_age(int userAge) {
		user_age = userAge;
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
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String userPhone) {
		user_phone = userPhone;
	}




}
