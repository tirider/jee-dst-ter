package com.dst.dao;

import java.util.ArrayList; 
import java.util.List; 

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager; 
import javax.persistence.PersistenceContext; 
import javax.persistence.Query;

import com.dst.beans.Reference;

/**
 * Session Bean implementation class ReferenceBean
 */
@Stateless
@LocalBean
public class ReferenceDAO 
{
	/**
	 * Container-Managed Entity Manager
	 */
	@PersistenceContext
	EntityManager em;
    
    /**
     * Finds User article ids. 
     * @param userId
     * @return An User Article ids.
     */ 
	@SuppressWarnings("unchecked")
	public List<String> findArticlesByUserId(int userId)
	{
    	Query query = em.createQuery("FROM Reference r WHERE r.user.id=?1");
    	query.setParameter(1, userId);
    	
    	List<Reference> referenceSet = query.getResultList();
    	List<String> resultSet 		 = new ArrayList<String>();
    	
    	for(Reference reference : referenceSet)
    	{
    		resultSet.add(reference.getArticle().getId());
    	}
    	
    	return resultSet;
	}
    
	/**
	 * Checks whether an User reference exists.
	 * @param uid
	 * @param aid
	 * @param name
	 * @return True or False
	 */
	public boolean exists(int uid, String aid, String name)
	{
    	Query query = em.createQuery("FROM Reference r WHERE r.user.id=?1 and r.article.id=?2 and r.name=?3");
    	query.setParameter(1, uid);
    	query.setParameter(2, aid);
    	query.setParameter(3, name);
    	
		return (query.getResultList().size() > 0);
	}

	/**
	 * Inserts an Reference into database.
	 * @param reference
	 */
	public void save(Reference reference)
	{
		try
		{
			em.persist(reference);
		}
		catch(Exception e)
		{
			System.err.println("Error create reference ()...");
		}
	}
	
	/**
	 * Finds all User's references.
	 * @param uid
	 * @param offset
	 * @param maxElements
	 * @return A Reference set.
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> findByUserId(int uid, int offset, int maxElements)	
	{
    	Query query = em.createQuery("FROM Reference r WHERE r.user.id=?1");
    	query.setParameter(1, uid);
    	query.setFirstResult(offset);
    	query.setMaxResults(maxElements);
    	
    	List<Reference> referenceSet = query.getResultList();
    	List<Reference> resultSet  = new ArrayList<Reference>();

    	for(Reference reference : referenceSet)
    	{
    		resultSet.add(reference);
    	}
    	
		return resultSet;
	}
	
	/**
	 * Deletes an User reference.
	 * @param userId
	 * @param articleId
	 * @param name
	 * @return True or False
	 */
	public boolean delete(int userId, String articleId, String name)
	{
    	Query query = em.createQuery("DELETE FROM Reference r WHERE r.user.id=?1 and r.article.id=?2 and r.name=?3");
    	query.setParameter(1, userId);
    	query.setParameter(2, articleId);
    	query.setParameter(3, name);
    	
    	try
    	{
    		return (query.executeUpdate() > 0);
    	}
    	catch(Exception e)
    	{
    		System.err.println("Error delete reference...");
    		return false;
    	}
	}
	
	/**
	 * Finds User ids by Article id.
	 * @param articleId
	 * @return An User id set.
	 */
	@SuppressWarnings("unchecked")
	public List<String> findUsersByArticleId(String articleId)
	{
    	Query query = em.createQuery("FROM Reference r WHERE r.article.id=?1");
    	query.setParameter(1, articleId);
    	
    	List<Reference> referenceSet = query.getResultList();
    	List<String> resultSet  = new ArrayList<String>();

    	for(Reference reference : referenceSet)
    	{
    		resultSet.add(String.valueOf(reference.getUser().getId()));
    	}
    	
		return resultSet;
	}
	
	/**
	 * Retrieves all system References.
	 * @return An Reference set.
	 */
	@SuppressWarnings("unchecked")
	public List<Reference> findAll()
	{
    	Query query = em.createQuery("FROM Reference r");

    	return query.getResultList();
	}
}
