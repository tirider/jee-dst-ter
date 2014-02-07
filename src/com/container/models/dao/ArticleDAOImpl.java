package com.container.models.dao;

import java.util.ArrayList;
import java.util.List;

import com.container.models.beans.Article;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class ArticleDAOImpl implements ArticleDAO 
{
	// REPRESENTE LA COLLECTION
	private static final String TABLE_NAME     = "articles";
	
	// CHAMPS DE LA COLLETION DANS LA BASE DE DONNEES
	private static final String TABLE_FIELD_1 = "_id";
	private static final String TABLE_FIELD_2 = "name";
	private static final String TABLE_FIELD_3 = "format";
	private static final String TABLE_FIELD_4 = "size";
	private static final String TABLE_FIELD_5 = "numberofpages";
	private static final String TABLE_FIELD_6 = "creationdate";	

	// OBJET D'ACCES AUX DONNEES
	private DAOInterface dao;

	// CONSTRUCTEUR
	public ArticleDAOImpl(DAOInterface dao)
	{
		this.dao = dao;
	}
	
	/** CETTE METHODE INITIALISE UN OBJET ARTICLE TROUVE DEPUIS UN ID **/
	@Override
	public Article find(String aid) 
	{		
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_1, aid);
		
		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
	 
		// BEANS ARTICLE
		Article article = new Article();
		
		try
		{
			if (row != null) 
			{
				article.setId(row.get(TABLE_FIELD_1).toString());
				article.setName(row.get(TABLE_FIELD_2).toString());
				article.setFormat(row.get(TABLE_FIELD_3).toString());
				article.setSize(Integer.parseInt(row.get(TABLE_FIELD_4).toString()));
				article.setNumberofpages(Integer.parseInt(row.get(TABLE_FIELD_5).toString()));
			}
		}
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}

		return article;
	}

	/** CETTE METHODE VERIFIE SI UN ARTICLE EXISTE DANS LA RELATION ARTICLES **/
	@Override
	public boolean exists(String aid)
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_1, aid);
		
		// RESULTAT DE LA REQUETE
		DBObject row = collection.findOne(searchquery);
	 
		boolean exists = false;
		
		if(row != null) exists = true;
			
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();		
		
		return exists;
	}
	
	/** CETTE METHODE RENDS TOUT LES TUPLES DE LA RELATION ARTICLE **/
	@Override
	public List<Article> findAll() 
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
		List<Article> articles = new ArrayList<Article>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// BEANS UTILISATEUR
				Article article = new Article();
				
				article.setId(row.get(TABLE_FIELD_1).toString());
				article.setName(row.get(TABLE_FIELD_2).toString());
				article.setFormat(row.get(TABLE_FIELD_3).toString());
				article.setSize(Integer.parseInt(row.get(TABLE_FIELD_4).toString()));
				article.setNumberofpages(Integer.parseInt(row.get(TABLE_FIELD_5).toString()));				

				// SET DANS LA LISTE
				articles.add(article);
			}
		}
		finally
		{
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
			
		return articles;
	}

	@Override
	public boolean create(Article article) 
	{
		// GET A THE CONNECTION FROM THE POOL
		DB db = (DB) this.dao.getConnection();
		db.requestStart();
		
		// ON CHERCHE LA COLLECTION CORRESPONDANTE SUR MONGODB
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// CREATION DU QUERY
		BasicDBObject query = new BasicDBObject();
		query.put(TABLE_FIELD_1, article.getId());
		query.put(TABLE_FIELD_2, article.getName());
		query.put(TABLE_FIELD_3, article.getFormat());
		query.put(TABLE_FIELD_4, article.getSize());
		query.put(TABLE_FIELD_5, article.getNumberofpages());
		query.put(TABLE_FIELD_6, article.getCreationdate());
		
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
	public boolean updateName(String id, String newname) 
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
		setquery.append(TABLE_FIELD_2, newname);
		updatequery.put("$set", setquery);
		
		// COMMITING AND CHECKING IF WRITTING WAS RIGHT
		WriteResult update = collection.update(wherequery, updatequery);
		if (update.getLastError() != null)
			return true;
		return false;
	}
}
