package com.dst.dao;
 
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager; 
import javax.persistence.PersistenceContext; 
import javax.persistence.Query;

import com.dst.model.User;

/**
 * Session Bean implementation class UserBean
 */
@Stateless
@LocalBean
public class UserDAO 
{
	/**
	 * Container-Managed Entity Manager
	 */
	@PersistenceContext
	EntityManager em;
	
    
	/**
	 * Checks whether email and password filds exists.
	 * @param email
	 * @param password
	 * @return An User instance.
	 */
    public User findByEmailPassword(String email, String password) 
    {
    	Query query = em.createQuery("FROM User u WHERE u.email=?1 and u.password=?2");
    	query.setParameter(1, email.toLowerCase());
    	query.setParameter(2, password);
    	
		if(query.getResultList().size() == 1)
			return (User) query.getSingleResult();
		return null; 
    }
    
    /**
     * Finds an User by its email address.
     * @param email
     * @return An User instance
     */
	public User findByEmail(String email)
	{
    	Query query = em.createQuery("FROM User u WHERE u.email=?1");
    	query.setParameter(1, email.toLowerCase());
    	
		if(query.getResultList().size() > 0)
			return (User)query.getSingleResult();
		return null;
	}
	
    /**
     * Finds User by its identifier.
     * @param id
     * @return An User instance
     */
    public User findById(int id)
    {
    	try
    	{
    		return em.find(User.class, id);
    	}
    	catch(Exception e)
    	{
    		System.err.println("Error findById...");
    		return null;
    	}
    }
    
	/**
	 * Updates User's password by its email address.
	 * @param email
	 * @param newpassword
	 * @return True or False
	 */
	public boolean updatePasswordByEmail(String email, String newpassword)
	{
    	Query query = em.createQuery("UPDATE User u SET u.password=?1 WHERE u.email=?2");
    	query.setParameter(1, newpassword);
    	query.setParameter(2, email.toLowerCase());
    	
    	try
    	{
    		return (query.executeUpdate() > 0);
    	}
    	catch(Exception e)
    	{
    		System.err.println("Error updatePasswordByEmail()...");
    		return false;
    	}
	}
	
	/**
	 * Checks whether User email exists.
	 * @param email
	 * @return True or False
	 */
	public boolean existsEmail(String email)
	{
    	Query query = em.createQuery("FROM User u WHERE u.email=?1");
    	query.setParameter(1, email.toLowerCase());
    	
		return (query.getResultList().size() > 0);
	}
	
	/**
	 * Inserts User into database.
	 * @param user
	 * @return True or False
	 */
	public boolean save(User user) 
	{
		try
		{
	    	em.persist(user);
	    	
	    	return true;
		}
		catch(Exception e)
		{
			System.err.println("Error save()...");
			return false;
		}
	}
	
	/**
	 * Deletes an User from database by its id.
	 * @param id
	 * @return True or False
	 */
	public boolean deleteById(int id)
	{
		try
		{
			User user = em.find(User.class, id);
			em.remove(user);
			
			return true;
		}
		catch(Exception e)
		{
			System.err.println("Error deleteById()...");
			return false;
		}
	}
	
	/**
	 * Deletes an User by its email address.
	 * @param email
	 * @return True or False
	 */
	public boolean deleteByEmail(String email)
	{
    	Query query = em.createQuery("FROM User u WHERE u.email=?1");
    	query.setParameter(1, email.toLowerCase());
    	
		try
		{
			User user = (User)query.getSingleResult();
			em.remove(user);
			
			return true;
		}
		catch(Exception e)
		{
			System.err.println("Error deleteByEmail()...");
			return false;
		}
	}
	
	/**
	 * Retrieves all system Users.
	 * @return An user set.
	 */
	@SuppressWarnings("unchecked")
	public List<User> findAll()
	{
    	Query query = em.createQuery("FROM User u");

    	return query.getResultList();
	}
}
