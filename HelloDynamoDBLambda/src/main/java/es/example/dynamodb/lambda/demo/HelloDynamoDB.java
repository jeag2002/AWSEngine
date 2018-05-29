package es.example.dynamodb.lambda.demo;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import es.example.dynamodb.lambda.bean.Request;

public class HelloDynamoDB implements RequestHandler<Request, String> {
	
	private DynamoDB dynamoDB;
	private String DYNAMODB_TABLE_NAME = "QueryTests";
	private Regions REGION = Regions.EU_CENTRAL_1; //franfurt
	
	

	private void initDynamoDbClient() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(REGION));
		this.dynamoDB = new DynamoDB(client);
	}
	
	private void persistData(Request personRequest) throws ConditionalCheckFailedException {
		     
			this.dynamoDB.getTable(DYNAMODB_TABLE_NAME)
		          .putItem(
		            new PutItemSpec().withItem(new Item()
		              .withInt("ID", personRequest.getId())
		              .withString("Name", personRequest.getName())));
	 }
	
	
	
    @Override
    public String handleRequest(Request input, Context context) {
        context.getLogger().log("Insert into DynamoDB : (" + input.toString() +")");
        
        String response = "";

        try {
        	initDynamoDbClient();
        	persistData(input);
        	response = "input data (" + input.toString() + ") inserted OK" ;
        	context.getLogger().log(response);
        }catch(Exception e) {
        	context.getLogger().log("Insert into DynamoDB : (" + input.toString() +") error (" + e.getMessage() + ")");
        	response = "Insert into DynamoDB : (" + input.toString() +") error (" + e.getMessage() + ")";
        }finally {
        	return response;
        }
        
    
    }

}
