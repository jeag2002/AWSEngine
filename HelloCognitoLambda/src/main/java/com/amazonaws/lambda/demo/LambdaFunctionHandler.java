package com.amazonaws.lambda.demo;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.amazonaws.lambda.bean.AuthenticateUserRequest;
import com.amazonaws.lambda.bean.AuthenticateUserResponse;
import com.amazonaws.lambda.service.User;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<AuthenticateUserRequest, AuthenticateUserResponse> {

    @Override
    public AuthenticateUserResponse handleRequest(AuthenticateUserRequest input, Context context) {
        
    	AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();
    	
    	@SuppressWarnings("unchecked")
        User user = authenticateUser(input,context);
        if(user!=null){
            authenticateUserResponse.setUserId(user.getUserId());
            authenticateUserResponse.setStatus("true");
            authenticateUserResponse.setOpenIdToken(user.getOpenIdToken());
        }else{
            authenticateUserResponse.setUserId(null);
            authenticateUserResponse.setStatus("false");
            authenticateUserResponse.setOpenIdToken(null);
        }
    	
    	
        return authenticateUserResponse;
    }
    
    
    public User authenticateUser(AuthenticateUserRequest input, Context context){
        User user=null;
        	
        String userName = input.getUserName();
        String passwordHash = input.getPasswordHash();
        	
        try{
            AmazonDynamoDBClient client = new AmazonDynamoDBClient();
            client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
            DynamoDBMapper mapper = new DynamoDBMapper(client);
    	    	
            user = mapper.load(User.class, userName);
            
            if (user == null) {
            	context.getLogger().log("user NULL!\n");
            }else {
            	context.getLogger().log("user: (" + user.toString() + ") -- input (" + input.toString() + ")\n");
            }
    	    	
            if(user!=null){
                if(user.getPasswordHash().equalsIgnoreCase(passwordHash)){
                    String openIdToken = getOpenIdToken(user.getUserId());
                    user.setOpenIdToken(openIdToken);
                    return user;
                }
            }
        }catch(Exception e){
        	context.getLogger().log(e.toString()+"\n");
        }
        return user;
    }
    
    
    private String getOpenIdToken(Integer userId){
    	
        AmazonCognitoIdentityClient client = new AmazonCognitoIdentityClient();
        client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
        GetOpenIdTokenForDeveloperIdentityRequest tokenRequest = new GetOpenIdTokenForDeveloperIdentityRequest();
        tokenRequest.setIdentityPoolId("eu-central-1:847d0c06-f09e-467a-952e-f1cab22ab86f");
        	
        HashMap map = new HashMap();
        map.put("login.dhruv.services", userId.toString());
        //map.put("cognito-identity.amazonaws.com", userId.toString());
        	
        tokenRequest.setLogins(map);
        tokenRequest.setTokenDuration(new Long(10001));
        	
        GetOpenIdTokenForDeveloperIdentityResult result = client.getOpenIdTokenForDeveloperIdentity(tokenRequest);
        	
        String token = result.getToken();
        	
        return token;
    }
    
    
    
    
    

}
