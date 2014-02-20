package com.dst.model.beans;

public class LuceneDocument 
{
	public static final String ID      = "id";
	public static final String FORMAT  = "format";
	public static final String TITLE   = "title";
	public static final String CONTENT = "content";
	public static final String URL     = "url";
	
	protected String id;
	protected String format;
	protected String title;
	protected String content;
	protected String url;
	
	public LuceneDocument()
	{
		this.id      = new String();
		this.format  = new String();
		this.title   = new String();
		this.content = new String();
		this.url     = new String();
	}
	
	public LuceneDocument(String id, String format, String title, String content, String url) 
	{
		this.id      = id;
		this.format  = format;
		this.title   = title;
		this.content = content;
		this.url     = url;
	}

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getFormat() {return format;	}
	public void setFormat(String format) { 	this.format = format; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public String toString() { return ""; }
}
