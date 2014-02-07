package com.container.models.beans;

public class Group 
{
	private String id;
	private String description;

	public Group() 
	{
		this.id = new String();
		this.description = new String();
	}
	
	public Group(String id, String description) 
	{
		this.id = id;
		this.description = description;
	}
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getDescription() { return description; }
	public void setDescription(String description) { 	this.description = description; }
}