package com.dst.model.javabeans;

public class IndexedLuceneDocument extends LuceneDocument
{
	protected float score;
	
	public IndexedLuceneDocument(){}
	
	public IndexedLuceneDocument(String id, String format, String title, String content, String url,  float score) 
	{
		super(id, format, title, content, url);
		this.score  = score;
	}

	public float getScore() { return score; }
	
	public void setScore(float score) { this.score = score; }	
}