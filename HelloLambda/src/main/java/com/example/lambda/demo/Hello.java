
//https://medium.com/@joshua.a.kahn/invoking-an-aws-lambda-function-from-java-29efe3a03fe8

package com.example.lambda.demo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.lambda.bean.Request;
import com.example.lambda.bean.RequestDB;

public class Hello implements RequestHandler<Request, String> {
	
	private static final String DynamoDBFunction = "HelloDynamoLambda";

    @Override
    public String handleRequest(Request input, Context context) {
        
    	context.getLogger().log("Input: " + input.getInput());   
        String output = "Hello " +  input.getInput()  +  "!";
        
        String res = "";
        
        try {
        	res = redirectInfoToDynamoLambda(input);
        }catch(Exception e) {
        	res = "Invoking Error to DynamoDB (" + e.getMessage() + ")";
        }
        
        context.getLogger().log(res);
        // TODO: implement your handler
        return output;
    }
    
    
    private String redirectInfoToDynamoLambda(Request input) throws Exception{
    	
    	String res = "";
    	
    	Regions region = Regions.EU_CENTRAL_1;
    	
    	AWSLambdaClientBuilder builder = AWSLambdaClientBuilder.standard().withRegion(region);
    	AWSLambda client = builder.build();
    	
    	RequestDB dynReq = new RequestDB();
    	dynReq.setId(11);
    	dynReq.setName(input.getInput());
    	
    	InvokeRequest req = new InvokeRequest().withFunctionName(DynamoDBFunction).withPayload(dynReq.toString()); 
    	InvokeResult result = client.invoke(req);
    	res = result.getPayload().toString();
    	return res;
    	
    }

}
