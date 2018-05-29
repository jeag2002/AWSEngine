package es.example.dynamodb.lambda.bean;

public class Request {
	
	private int id;
	private String name;
	
	public Request() {
		id = 0;
		name = "";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "id: (" + id + ") name: (" + name + ")"; 
	}
	

}
