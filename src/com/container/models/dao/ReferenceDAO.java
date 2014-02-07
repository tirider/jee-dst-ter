package com.container.models.dao;

import java.util.List;

import com.container.models.beans.Reference;

public interface ReferenceDAO 
{
	public boolean find(String uid, String aid, String filename);
	
	public Reference findUsingName(String uid, String name);

	public List<String> findArticlesByUserId(String uid);
	
	public List<String> findUsersByArticleId(String aid);
	
	public List<Reference> findByUserId(String uid, int offset, int n);
	
	public List<Reference> findByArticleId(String aid);
	
	public List<Reference> findAll();
	
	public boolean create(Reference reference);
	
	public boolean delete(String uid, String aid, String name);
	
	public int getAffectedRows();
}
