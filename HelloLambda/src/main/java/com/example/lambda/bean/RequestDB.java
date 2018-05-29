package com.example.lambda.bean;

public class RequestDB {

	private int id;
	private String name;
	
	public RequestDB() {
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
		return "{\"id\":"+id+",\"name\":\""+name+"\"}"; 
	}
	
	
}
