package com.container.models.dao;

import java.util.ArrayList;
import java.util.List;

import com.container.models.beans.Group;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class GroupDAOImpl implements GroupDAO 
{
	// REPRESENTE LA COLLECTION
	private static final String TABLE_NAME     = "groups";
	
	// CHAMPS DE LA COLLETION DANS LA BASE DE DONNEES
	private static final String TABLE_FIELD_1 = "_id";
	private static final String TABLE_FIELD_2 = "description";	

	// OBJET D'ACCES AUX DONNEES
	private DAOInterface dao;

	// CONSTRUCTEUR
	public GroupDAOImpl(DAOInterface dao)
	{
		this.dao = dao;
	}
	
	@Override
	public boolean find(String id) 
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
	 
		// BEANS ARTICLE
		Group group = new Group();
		
		try
		{
			if (row != null) 
			{
				group.setId(row.get(TABLE_FIELD_1).toString());
				group.setDescription(row.get(TABLE_FIELD_2).toString());
			}
		}
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}

		if( ! group.getId().trim().isEmpty())
			return true;
		return false;
	}

	// RENDS TOUT LES ARTICLES STOCKES DANS MONGODB
	@Override
	public List<Group> findAll() 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY - QUERY VIDE MATCH ALL DOCUMENTS
		BasicDBObject searchquery = new BasicDBObject();
		
		// RESULTAT DE LA REQUETE
		DBCursor rows = collection.find(searchquery);
		
		// OBJECT RECEVEUR DE BEANS UTILISATEUR
		List<Group> groups = new ArrayList<Group>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// BEANS UTILISATEUR
				Group group = new Group();
				
				group.setId(row.get(TABLE_FIELD_1).toString());
				group.setDescription(row.get(TABLE_FIELD_2).toString());				

				// SET DANS LA LISTE
				groups.add(group);
			}
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
			
		return groups;
	}

	@Override
	public boolean create(Group group) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject query = new BasicDBObject();
		query.put(TABLE_FIELD_1, group.getId());
		query.put(TABLE_FIELD_2, group.getDescription());
		
		// COMMITING AND CHECKING IF WRITINT WAS RIGHT
		WriteResult  insertion = collection.insert(query);
		
		if (insertion.getLastError() != null)
			return true;
		return false;
	}

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
		
		if (delete.getLastError() != null)
			return true;
		return false;
	}

	// MODIFIE LA NOM D UN DOCUMENT
	@Override
	public boolean updateDescription(String id, String description) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject wherequery = new BasicDBObject();
		wherequery.put(TABLE_FIELD_1, id);
		
		BasicDBObject updatequery = new BasicDBObject();
		BasicDBObject setquery = new BasicDBObject();
		setquery.append(TABLE_FIELD_2, description);
		updatequery.put("$set", setquery);
		
		// COMMITING AND CHECKING IF WRITTING WAS RIGHT
		WriteResult update = collection.update(wherequery, updatequery);
		
		if (update.getLastError() != null)
			return true;
		return false;
	}
}