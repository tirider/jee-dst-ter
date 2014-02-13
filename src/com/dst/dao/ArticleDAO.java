package com.dst.dao;
 
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;  
import javax.persistence.PersistenceContext;  
import javax.persistence.Query;

import com.dst.model.Article;

/**
 * Session Bean implementation class ArticleBean
 */
@Stateless
@LocalBean
public class ArticleDAO 
{
	/**
	 * Container-Managed Entity Manager
	 */
	@PersistenceContext
	EntityManager em;
	
    /**
     * Checks whether Article's identifier exist.
     * @param aid
     * @return True or False
     */
	public boolean exists(String aid)
	{
    	Query query = em.createQuery("FROM Article a WHERE a.id=?1");
    	query.setParameter(1, aid);
		
		return (query.getResultList().size() > 0);
	}
	
	/**
	 * Inserts Article into database. 
	 * @param article
	 * @return
	 */
	public void save(Article article)
	{
		try
		{
			em.persist(article);
		}
		catch(Exception e)
		{
			System.err.println("Error save()...");
		}
	}

	/**
	 * Deletes an Article from database by its id.
	 * @param id
	 * @return True or False
	 */
	public boolean delete(String id)
	{
    	Query query = em.createQuery("DELETE FROM Article a WHERE a.id=?1");
    	query.setParameter(1, id);
    	
    	try
    	{
    		return (query.executeUpdate() > 0);
    	}
    	catch(Exception e)
		{
			System.err.println("Error deleteArticle()...");
    		return false;
		}
	}
	
	/**
	 * Finds an Article by its id.
	 * @param aid
	 * @return
	 */
	public Article find(String aid)
	{
		try
		{
			return em.find(Article.class, aid);
		}
		catch(Exception e)
		{
			System.err.println("Error findArticle()...");
			return null;
		}
	}
	
	/**
	 * Retrieves all system Article.
	 * @return An Article set.
	 */
	@SuppressWarnings("unchecked")
	public List<Article> findAll()
	{
    	Query query = em.createQuery("FROM Article a");

    	return query.getResultList();
	}
}
