package com.dst.dao;
 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction; 
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.dst.model.Article;

/**
 * Session Bean implementation class ArticleBean
 */
@Stateless
@LocalBean
public class ArticleDAO 
{
	@PersistenceUnit(unitName="jee-dst-ter")
	EntityManagerFactory emf;
	
    /**
     * Default constructor. 
     */
    public ArticleDAO() { }
    
    /**
     * exists article
     * @param aid
     * @return
     */
	public boolean exists(String aid)
	{
		// ENTITY MANAGER FACTORY
    	EntityManager em  = emf.createEntityManager();
    	
    	// PREPARING QUERY
    	Query query = em.createQuery("FROM Article a WHERE a.id=?1");
    	query.setParameter(1, aid);
    	
    	// GETTING RESULTS
		int size = query.getResultList().size();
		
    	// CLOSING ENTITIES
    	em.close();
    	
		return (size > 0);
	}
	
	/**
	 * create article
	 * @param article
	 * @return
	 */
	public void create(Article article)
	{
    	EntityManager em		= emf.createEntityManager();
		EntityTransaction tx  	= em.getTransaction();		
       
		tx.begin();
        em.persist(article);
        tx.commit();
        
        em.close();
	}

	public boolean delete(String id)
	{
    	EntityManager em = emf.createEntityManager();
		
    	// PREPARING QUERY
    	Query query = em.createQuery("DELETE FROM Article a WHERE a.id=?1");
    	query.setParameter(1, id);
    	
    	int records = query.executeUpdate();
    	
    	em.close();
    	
		return (records > 0);
	}
//  
//	public Article find(String aid)
//	
//	public List<Article> findAll()
//	
//	

//	
//	public boolean updateName(String id, String newname)	
}
