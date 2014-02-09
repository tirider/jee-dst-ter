package com.dst.beans;

import java.util.Date;

public class Article 
{
	private String id;
	private String name;
	private String format;
	private int size;
	private int numberofpages;
	private Date creationdate;

	public Article() 
	{
		this.id = new String();
		this.name = new String();
		this.format = new String();
		this.size = 0;
		this.numberofpages = 0;
		this.creationdate = new Date();
	}
	
	public Article(String id, String name, String format, int size, int numberofpages) 
	{
		this.id = id;
		this.name = name;
		this.format = format;
		this.size = size;
		this.numberofpages = numberofpages;		
		this.creationdate = new Date();
	}
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getFormat() { return format; }
	public void setFormat(String format) { this.format = format; }

	public int getSize() { return size; }
	public void setSize(int size) { 	this.size = size; }

	public int getNumberofpages() { return numberofpages; }
	public void setNumberofpages(int numberofpages) { this.numberofpages = numberofpages; }

	public Date getCreationdate() { return creationdate; }
	public void setCreationdate(Date creationdate) { 	this.creationdate = creationdate; }
}