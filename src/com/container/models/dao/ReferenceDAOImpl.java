package com.container.models.dao;

import java.util.ArrayList;
import java.util.List;

import com.container.models.beans.Reference;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class ReferenceDAOImpl implements ReferenceDAO 
{
	// REPRESENTE LA COLLECTION
	private static final String TABLE_NAME     = "references";
	
	// CHAMPS DE LA COLLETION DANS LA BASE DE DONNEES
	private static final String TABLE_FIELD_1 = "_id";
	private static final String TABLE_FIELD_2 = "uid";
	private static final String TABLE_FIELD_3 = "aid";
	private static final String TABLE_FIELD_4 = "name";
	private static final String TABLE_FIELD_5 = "indexationdate";

	// OBJET D'ACCES AUX DONNEES
	private DAOInterface dao;
	private int affectedRows;
	
	// CONSTRUCTEUR
	public ReferenceDAOImpl(DAOInterface dao)
	{
		this.dao = dao;
	}
	
	@Override
	public boolean find(String uid, String aid, String name) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_2, uid);
		searchquery.put(TABLE_FIELD_3, aid);
		searchquery.put(TABLE_FIELD_4, name);
		
		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
	 
		// BEANS ARTICLE
		Reference reference = new Reference();
		
		try
		{
			if (row != null) 
			{
				reference.setId(row.get(TABLE_FIELD_1).toString());
				reference.setUserid(row.get(TABLE_FIELD_2).toString());
				reference.setArticleid(row.get(TABLE_FIELD_3).toString());
				reference.setName(row.get(TABLE_FIELD_4).toString());
			}
		}
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}

		if( ! reference.getId().trim().isEmpty())
			return true;
		return false;
	}

	@Override
	public Reference findUsingName(String uid, String name) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_2, uid);
		searchquery.put(TABLE_FIELD_4, name);
		
		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
	 
		// BEANS ARTICLE
		Reference reference = new Reference();
		
		try
		{
			if (row != null) 
			{
				reference.setId(row.get(TABLE_FIELD_1).toString());
				reference.setUserid(row.get(TABLE_FIELD_2).toString());
				reference.setArticleid(row.get(TABLE_FIELD_3).toString());
				reference.setName(row.get(TABLE_FIELD_4).toString());
			}
		}
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
		
		return reference;
	}

	/** METHODE QUI RECUPERE TOUT LES DOCID D'UN UTILISATEUR (UID) **/
	public List<String> findArticlesByUserId(String uid)
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY - QUERY VIDE MATCH ALL DOCUMENTS
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_2, uid);
		
		// RESULTAT DE LA REQUETE
		DBCursor rows = collection.find(searchquery);
		
		// OBJECT RECEVEUR DE BEANS REFERENCE
		List<String> docsSet = new ArrayList<String>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// RECUPERATION DE L IDENTIFIANT DE CHAQUE DOCUMENT
				docsSet.add(row.get(TABLE_FIELD_3).toString());
			}
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
			
		return docsSet;
	}
	
	/** METHODE QUI RECUPERE TOUT LES UTILISATEUR (UID) QUI PARTAGENT CE DOCUMENT (DOCID) **/
	public List<String> findUsersByArticleId(String aid)
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY - QUERY VIDE MATCH ALL DOCUMENTS
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_3, aid);
		
		// RESULTAT DE LA REQUETE
		DBCursor rows = collection.find(searchquery);
		
		// OBJECT RECEVEUR DE BEANS REFERENCE
		List<String> usersSet = new ArrayList<String>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// RECUPERATION DE L IDENTIFIANT DE CHAQUE UTILISATUER
				usersSet.add(row.get(TABLE_FIELD_2).toString());
			}
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
			
		return usersSet;
	}
	
	// RENDS TOUTES LES REFERENCES D UN UTILISATEUR
	@Override
	public List<Reference> findByUserId(String uid, int offset, int n) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY - QUERY VIDE MATCH ALL DOCUMENTS
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_2, uid);
		
		// RESULTAT DE LA REQUETE
		DBCursor cursor = collection.find(searchquery);
		DBCursor rows = cursor.skip(offset).limit(n);
		
		// OBJECT RECEVEUR DE BEANS REFERENCE
		List<Reference> references = new ArrayList<Reference>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// BEANS UTILISATEUR
				Reference reference = new Reference();
		
				reference.setId(row.get(TABLE_FIELD_1).toString());
				reference.setUserid(row.get(TABLE_FIELD_2).toString());
				reference.setArticleid(row.get(TABLE_FIELD_3).toString());
				reference.setName(row.get(TABLE_FIELD_4).toString());
				
				// SET DANS LA LISTE
				references.add(reference);
			}
			
			// RECUPERATION DU NOMBRE D ELEMENTS AFFECTES (COUNTER)
			this.setAffectedRows(cursor.count());
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
			
		return references;
	}

	// RENDS TOUTES LES REFERENCES D UN ARTICLE
	@Override
	public List<Reference> findByArticleId(String aid) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY - QUERY VIDE MATCH ALL DOCUMENTS
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_3, aid);
		
		// RESULTAT DE LA REQUETE
		DBCursor rows = collection.find(searchquery);
		
		// OBJECT RECEVEUR DE BEANS REFERENCE
		List<Reference> references = new ArrayList<Reference>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// BEANS UTILISATEUR
				Reference reference = new Reference();
		
				reference.setId(row.get(TABLE_FIELD_1).toString());
				reference.setUserid(row.get(TABLE_FIELD_2).toString());
				reference.setArticleid(row.get(TABLE_FIELD_3).toString());
				reference.setName(row.get(TABLE_FIELD_4).toString());
				
				// SET DANS LA LISTE
				references.add(reference);
			}
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
			
		return references;
	}

	// RENDS TOUTES LES REFERENCES
	@Override
	public List<Reference> findAll() 
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
		
		// OBJECT RECEVEUR DE BEANS REFERENCE
		List<Reference> references = new ArrayList<Reference>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// BEANS UTILISATEUR
				Reference reference = new Reference();
		
				reference.setId(row.get(TABLE_FIELD_1).toString());
				reference.setUserid(row.get(TABLE_FIELD_2).toString());
				reference.setArticleid(row.get(TABLE_FIELD_3).toString());
				reference.setName(row.get(TABLE_FIELD_4).toString());
				
				// SET DANS LA LISTE
				references.add(reference);
			}
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
			
		return references;
	}

	// AJOUT UNE NOUVELLE REFERENCE A LA DB
	@Override
	public boolean create(Reference reference) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject query = new BasicDBObject();
		query.put(TABLE_FIELD_2, reference.getUserid());
		query.put(TABLE_FIELD_3, reference.getArticleid());
		query.put(TABLE_FIELD_4, reference.getName());
		query.put(TABLE_FIELD_5, reference.getIndexationdate());
		
		// COMMITING AND CHECKING IF WRITINT WAS RIGHT
		WriteResult  insertion = collection.insert(query);
		if (insertion.getLastError() != null)
			return true;
		return false;
	}

	@Override
	public boolean delete(String uid, String aid, String name) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY		
		BasicDBObject wherequery = new BasicDBObject();
		wherequery.put(TABLE_FIELD_2, uid);
		wherequery.put(TABLE_FIELD_3, aid);
		wherequery.put(TABLE_FIELD_4, name);
		
		// COMMITING AND CHECKING IF WRITTING WAS RIGHT
		WriteResult  delete = collection.remove(wherequery);
		if (delete.getLastError() != null)
			return true;
		return false;
	}

	@Override	
	public int getAffectedRows() 
	{
		return affectedRows;
	}

	public void setAffectedRows(int affectedRows) 
	{
		this.affectedRows = affectedRows;
	}
}