package com.dst.dao;
 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory; 
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.dst.model.User;

/**
 * Session Bean implementation class UserBean
 */
@Stateless
@LocalBean
public class UserDAO 
{
	@PersistenceUnit(unitName="jee-dst-ter")
	EntityManagerFactory emf;
	
    /**
     * Default constructor. 
     */
    public UserDAO() {}
    
    /**
     * Finds user by its identifier.
     * @param id
     * @return
     */
    public User findById(int id)
    {
		// ENTITY MANAGER FACTORY
    	EntityManager em  = emf.createEntityManager();

    	// PREPARING QUERY
    	Query query = em.createQuery("FROM User u WHERE u.id=?1");
    	query.setParameter(1, id);
    	
    	// RETIEVING RESULTS
    	User user = (User) query.getSingleResult();

    	// CLOSING ENTITIES
    	em.close();
    	
		return user;
    }
    
	/**
	 * Checks if email and password filds match
	 * @param email
	 * @param password
	 * @return
	 */
    public User findByEmailPassword(String email, String password) 
    {
		// ENTITY MANAGER FACTORY
    	EntityManager em  = emf.createEntityManager();
    	
    	// PREPARING QUERY
    	Query query = em.createQuery("FROM User u WHERE u.email=?1 and u.password=?2");
    	query.setParameter(1, email.toLowerCase());
    	query.setParameter(2, password);
    	
    	// GETTING RESULTS
		int size = query.getResultList().size();
		
		User user = null;
		
		if(size > 0)
			user = (User) query.getResultList().get(0);
    	
    	// CLOSING ENTITIES
    	em.close();
    	
    	return user;
    }
    
    /**
     * Finds user by its email account.
     * @param email
     * @return
     */
	public User findByEmail(String email)
	{
		// ENTITY MANAGER FACTORY
    	EntityManager em  = emf.createEntityManager();

    	// PREPARING QUERY
    	Query query = em.createQuery("FROM User u WHERE u.email=?1");
    	query.setParameter(1, email.toLowerCase());
    	
    	// GETTING RESULTS
		int size = query.getResultList().size();
		
		User user = null;
		
		if(size > 0)
			user = (User) query.getResultList().get(0);

    	// CLOSING ENTITIES
    	em.close();
    	
		return user;
	}

	/**
	 * updatePasswordByEmail
	 * @param email
	 * @param newpassword
	 * @return
	 */
	public boolean updatePasswordByEmail(String email, String newpassword)
	{
		// ENTITY MANAGER FACTORY
    	EntityManager em  		= emf.createEntityManager();
		
    	// PREPARING QUERY
    	Query query = em.createQuery("UPDATE User u SET u.password=?1 WHERE u.email=?2");
    	query.setParameter(1, newpassword);
    	query.setParameter(2, email.toLowerCase());
    	
    	int records = query.executeUpdate();
    	
    	em.close();
    	
		return (records > 0);
	}
	
	/**
	 * existsEmail
	 * @param email
	 * @return
	 */
	public boolean existsEmail(String email)
	{
		// ENTITY MANAGER FACTORY
    	EntityManager em  = emf.createEntityManager();

    	// PREPARING QUERY
    	Query query = em.createQuery("FROM User u WHERE u.email=?1");
    	query.setParameter(1, email.toLowerCase());
    	
		int size = query.getResultList().size();
		
    	em.close();
    	
		return (size > 0);
	}
	
	/**
	 * save user
	 * @param user
	 * @return
	 */
	public boolean save(User user) 
	{
		// ENTITY MANAGER FACTORY
    	EntityManager em  	 = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
	    	em.persist(user);
	    	tx.commit();
	    	em.close();
	    	
	    	return true;
		}
		catch(Exception e)
		{
			System.err.println("Error persisting user...");
			
			return false;
		}
	}
//	
//	public boolean delete(String id) 
//	
//	
//	public boolean updatePasswordByEmail(String email, String newpassword)
//	
//	public User findByUsername(String username)
//	{
//    	EntityManager em 		= emf.createEntityManager();
//
//    	Query query = em.createQuery("select u from user u where u.email=?1 and u.password=?2");
//    	query.setParameter(1, email);
//    	query.setParameter(2, password);
//    	
//		return null;
//	}
//	public User findByGroup(String group)
//	
//	public boolean existsUsername(String username)
//	
//	public boolean existsEmail(String email)
//
//	public List<User> findAll()
}
