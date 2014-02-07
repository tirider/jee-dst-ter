package com.container.models.beans;

import java.util.Date;

public class Reference 
{
	private String id;
	private String userid;
	private String articleid;
	private String name;
	private Date indexationdate;
	
	public Reference() 
	{
		this.id         = new String();  
		this.userid   = new String();
		this.articleid = new String();
		this.name    = new String();		
		this.indexationdate = new Date();
	}

	public String getId() { 	return id; 	}
	public void setId(String id) { this.id = id; }

	public String getUserid() { return userid; 	}
	public void setUserid(String userid) { this.userid = userid; }

	public String getArticleid() { 	return articleid; }
	public void setArticleid(String articleid) { this.articleid = articleid; }
	
	public String getName() { 	return name; }
	public void setName(String name) { 	this.name = name; }

	public Date getIndexationdate() { return indexationdate; }
	public void setIndexationdate(Date indexationdate) { 	this.indexationdate = indexationdate; 	}
}
