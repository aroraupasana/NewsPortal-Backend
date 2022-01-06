package com.oracle.newsportal.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRequest {

	@NotBlank(message = "User Name can not be blank")
	@NotNull
	private String userName;
	
	@NotBlank(message = "Password can not be blank")
	@NotNull
	private String userPassword;

	public UserRequest() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
