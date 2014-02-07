package com.container.models.dao;

import java.util.ArrayList;
import java.util.List;

import com.container.models.beans.User;
import com.container.models.encryption.Encryptor;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class UserDAOImpl implements UserDAO
{
	// REPRESENTE LA COLLECTION
	private static final String TABLE_NAME     = "users";
	
	// CHAMPS DE LA COLLETION DANS LA BASE DE DONNEES
	private static final String TABLE_FIELD_1 = "_id";
	private static final String TABLE_FIELD_2 = "username";
	private static final String TABLE_FIELD_3 = "password";
	private static final String TABLE_FIELD_4 = "sexe";
	private static final String TABLE_FIELD_5 = "email"; 
	private static final String TABLE_FIELD_7 = "inscriptiondate";	

	// OBJET D'ACCES AUX DONNEES
	private DAOInterface dao;
	
	// CONSTRUCTEUR
	public UserDAOImpl(DAOInterface dao)
	{
		this.dao = dao;
	}

	//#####################################################################################	
	// TROUVER UN UTILISATEUR PAR SON ID
	@Override
	public User findById(String id) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_1, id);
		
		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
	 
		// BEANS UTILISATEUR
		User user = new User();
		
		try
		{
			if (row != null) 
			{
				user.setId(row.get(TABLE_FIELD_1).toString());
				user.setUsername(row.get(TABLE_FIELD_2).toString());
				//user.setPassword(row.get(TABLE_FIELD_3).toString());
				user.setSexe(row.get(TABLE_FIELD_4).toString());
				user.setEmail(row.get(TABLE_FIELD_5).toString()); 
			}
		} 
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
		
		return user;
	}

	//#####################################################################################
	@Override
	public User findByUsername(String username) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_2, username);

		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
		
		// BEANS UTILISATEUR
		User user = new User();
	
		try
		{
			if(row != null)
			{
				user.setId(row.get(TABLE_FIELD_1).toString());
				user.setUsername(row.get(TABLE_FIELD_2).toString());
				user.setPassword(row.get(TABLE_FIELD_3).toString());
				user.setSexe(row.get(TABLE_FIELD_4).toString());
				user.setEmail(row.get(TABLE_FIELD_5).toString()); 
			}
		}
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
		
		return user;
	}
	
	//#####################################################################################
	@Override
	public User findByEmail(String email) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_5, email.toLowerCase());

		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
		
		// BEANS UTILISATEUR
		User user = new User();
	
		try
		{
			if(row != null)
			{
				user.setId(row.get(TABLE_FIELD_1).toString());
				user.setUsername(row.get(TABLE_FIELD_2).toString());
				//user.setPassword(row.get(TABLE_FIELD_3).toString());
				user.setSexe(row.get(TABLE_FIELD_4).toString());
				user.setEmail(row.get(TABLE_FIELD_5).toString()); 
			}
		}
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
		
		return user;
	}
	
	//#####################################################################################
	@Override
	public boolean existsUsername(String username) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_2, username.toLowerCase());
		
		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
	 
		boolean exists = false;
		
		if (row != null) exists = true;  
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		return exists;
	}
	
	//#####################################################################################
	@Override
	public boolean existsEmail(String email) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_5, email.toLowerCase());
		
		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
	 
		boolean exists = false;
		
		if (row != null) exists = true;
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		return exists;
	}
	
	//#####################################################################################
	@Override
	public boolean exists(String username, String password) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_2, username);
		searchquery.put(TABLE_FIELD_3, Encryptor.ecryptToSha1(password));

		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
		
		boolean exists = false;
		
		if(row != null) exists = true;
			
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();		
		
		return exists;
	}
	
	//#####################################################################################
	// TROUVE TOUT LES UTILISATEURS INSCRIT DANS LE SYSTEM
	@Override
	public List<User> findAll() 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		
		// RESULTAT DE LA REQUETE
		DBCursor rows = collection.find(searchquery);
		
		// OBJECT RECEVEUR DE BEANS UTILISATEUR
		List<User> users = new ArrayList<User>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// BEANS UTILISATEUR
				User user = new User();
				
				user.setId(row.get(TABLE_FIELD_1).toString());
				user.setUsername(row.get(TABLE_FIELD_2).toString());
				//user.setPassword(row.get(TABLE_FIELD_3).toString());
				user.setSexe(row.get(TABLE_FIELD_4).toString());
				user.setEmail(row.get(TABLE_FIELD_5).toString()); 
				
				// SET DANS LA LISTE
				users.add(user);
			}
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
		
		return users;
	}

	//#####################################################################################	
	// CREATION D'UN UTILISATEUR
	@Override
	public boolean create(User user) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject query = new BasicDBObject();
		//query.put(TABLE_FIELD_1, user.getId());
		query.put(TABLE_FIELD_2, user.getUsername().toLowerCase());
		query.put(TABLE_FIELD_3, Encryptor.ecryptToSha1(user.getPassword()));
		query.put(TABLE_FIELD_4, user.getSexe());
		query.put(TABLE_FIELD_5, user.getEmail().toLowerCase()); 
		query.put(TABLE_FIELD_7, user.getInscriptiondate());
		
		// COMMITING AND CHECKING IF WRITINT WAS RIGHT
		WriteResult  insertion = collection.insert(query);
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		if (insertion.getLastError() != null)
			return true;
		return false;
	}
	
	//#####################################################################################		
	// ELIMINE UN UTILISATEUR DU SYSTEM
	/*ATTENTION : QUANT ON SUPPRIME UN UTILISATEUR DU SYSTEM, 
	 * ON DOIT SUPPRIMER AUSSI SES DEPENDANCES DANS TOUTE LES COLLECTIONS*/
	@Override
	public boolean delete(String id) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY		
		BasicDBObject wherequery = new BasicDBObject();
		wherequery.put(TABLE_FIELD_1, id);
		
		// COMMITING AND CHECKING IF WRITTING WAS RIGHT
		WriteResult  delete = collection.remove(wherequery);
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		if (delete.getLastError() != null)
			return true;
		return false;
	}

	//#####################################################################################
	// UPDTE LE MOT DE PASSE
	@Override
	public boolean updatePasswordById(String id, String newpassword) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject wherequery = new BasicDBObject();
		wherequery.append(TABLE_FIELD_1, id);
		
		BasicDBObject updatequery = new BasicDBObject();
		BasicDBObject setquery    = new BasicDBObject();
		setquery.append(TABLE_FIELD_3, Encryptor.ecryptToSha1(newpassword));
		updatequery.put("$set", setquery);
		
		// COMMITING AND CHECKING IF WRITTING WAS RIGHT
		WriteResult update = collection.update(wherequery, updatequery);
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		if (update.getLastError() != null)
			return true;
		return false;
	}
	
	//#####################################################################################
	// UPDTE LE MOT DE PASSE
	@Override
	public boolean updatePasswordByEmail(String email, String newpassword) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject wherequery = new BasicDBObject();
		wherequery.append(TABLE_FIELD_5, email.toLowerCase());
		
		BasicDBObject updatequery = new BasicDBObject();
		BasicDBObject setquery    = new BasicDBObject();
		setquery.append(TABLE_FIELD_3, Encryptor.ecryptToSha1(newpassword));
		updatequery.put("$set", setquery);
		
		// COMMITING AND CHECKING IF WRITTING WAS RIGHT
		WriteResult update = collection.update(wherequery, updatequery);
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		if (update.getLastError() != null)
			return true;
		return false;
	}
}