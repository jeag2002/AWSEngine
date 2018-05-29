package com.amazonaws.lambda.bean;

public class AuthenticateUserRequest {
	
	private String userName;
	private String passwordHash;
	
	public AuthenticateUserRequest() {
		userName = "";
		passwordHash = "";
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	@Override
	public String toString() {
		return "userName " +  this.userName + " passwordHash " +  this.passwordHash; 
	}


	
	

}
