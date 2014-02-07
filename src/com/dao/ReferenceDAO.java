package com.dao;

import java.util.ArrayList; 
import java.util.List;
import java.util.ListIterator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory; 
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.model.Reference; 

/**
 * Session Bean implementation class ReferenceBean
 */
@Stateless
@LocalBean
public class ReferenceDAO 
{
	@PersistenceUnit(unitName="jee-dst-ter")
	EntityManagerFactory emf;
	
    /**
     * Default constructor. 
     */
    public ReferenceDAO(){}
    
    /**
     * findArticlesByUserId
     * @param userId
     * @return
     */
	public List<String> findArticlesByUserId(int userId)
	{
		// ENTITY MANAGER FACTORY
    	EntityManager em  = emf.createEntityManager();
    	
    	// PREPARING QUERY
    	Query query = em.createQuery("SELECT r FROM Reference r WHERE r.user.id=?1");
    	query.setParameter(1, userId);
    	
    	// RETIEVING RESULTS
    	@SuppressWarnings("unchecked")
		ListIterator<Reference> refIterator  = query.getResultList().listIterator();

    	List<String> resultSet = new ArrayList<String>();
    	
    	while(refIterator.hasNext())
    	{
    		Reference ref = refIterator.next();
    		
    		resultSet.add(ref.getArticle().getId());
    	}
    	
    	// CLOSING ENTITIES
    	em.close();
    	
		return resultSet;
	}
    
	public boolean find(int uid, String aid, String name)
	{
		// ENTITY MANAGER FACTORY
    	EntityManager em  = emf.createEntityManager();
    	
    	// PREPARING QUERY
    	Query query = em.createQuery("SELECT r FROM Reference r WHERE r.user.id=?1 and r.article.id=?2 and r.name=?3");
    	query.setParameter(1, uid);
    	query.setParameter(2, aid);
    	query.setParameter(3, name);
    	
    	// GETTING RESULTS
		int size = query.getResultList().size();
		
		return (size > 0);
	}

	/**
	 * Save reference
	 * @param reference
	 */
	public void create(Reference reference)
	{
    	EntityManager em		= emf.createEntityManager();
		EntityTransaction tx  	= em.getTransaction();		
       
		tx.begin();
        em.persist(reference);
        tx.commit();
        
        em.close();
	}
//	public Reference findUsingName(String uid, String name) 
//	
//	public List<String> findUsersByArticleId(String aid)
//		
//	public List<Reference> findByUserId(String uid, int offset, int n)
//	
//	public List<Reference> findByArticleId(String aid)
//	
//	public List<Reference> findAll()
//	
//	public boolean create(Reference reference)
//	
//	public boolean delete(String uid, String aid, String name) 
//	
	
}
