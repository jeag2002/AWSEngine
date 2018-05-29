package com.amazonaws.lambda.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Users")
public class User {
	
    private String userName;
    private Integer userId;
    private String passwordHash;
    private String openIdToken;
	
    @DynamoDBHashKey(attributeName="userName")
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
	
    @DynamoDBAttribute(attributeName="userId")
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
	
	
    @DynamoDBAttribute(attributeName="passwordHash")
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
	
    @DynamoDBAttribute(attributeName="openIdToken")
    public String getOpenIdToken() { return openIdToken; }
    public void setOpenIdToken(String openIdToken) { this.openIdToken = openIdToken; }
	
    public User(String userName, Integer userId, String passwordHash, String openIdToken) {
        this.userName = userName;
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.openIdToken = openIdToken;
    }
    
    @Override
    public String toString() {
    	return "userName: " + this.userName + " userId: " + this.userId + " passwordHash: " + this.passwordHash +  " openIdToken: " + this.openIdToken;
    }
    
	
    public User(){ }	
}