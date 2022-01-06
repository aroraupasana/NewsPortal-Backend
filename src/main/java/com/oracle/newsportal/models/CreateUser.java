package com.oracle.newsportal.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUser {

	@NotBlank(message = "User Name can not be blank")
	@NotNull
	private String userName;
	
	@NotBlank(message = "Email Address can not be blank")
	@NotNull
	@Email
	private String userEmailId;
	
	@NotBlank(message = "Password can not be blank")
	@NotNull
	private String userPassword;
	
	public CreateUser() {
		super();
	}

	public CreateUser(String userName, String userEmailId, String userPassword) {
		this.userName = userName;
		this.userEmailId = userEmailId;
		this.userPassword = userPassword;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserEmailId() {
		return userEmailId;
	}
	
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
