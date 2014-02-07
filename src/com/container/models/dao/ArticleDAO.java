package com.container.models.dao;

import java.util.List;
import com.container.models.beans.Article;

public interface ArticleDAO 
{
	public Article find(String aid);
	
	public boolean exists(String aid);
	
	public List<Article> findAll();
	
	public boolean create(Article article);
	
	public boolean delete(String id);
	
	public boolean updateName(String id, String newname);
}
