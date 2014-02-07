package com.container.models.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDB implements DAOInterface
{
	private static final String PROPERTIES_FILE_PATH  = "/com/container/models/dao/mongodb.properties";
	
	private String server    = new String();
	private int     port        = 0;
	private String database = new String();
	private String username = new String();
	private String password = new String();
	
	private static MongoClient mongoClient;

	@Override
	public MongoDB getInstance() 
	{
		Properties properties = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = loader.getResourceAsStream(PROPERTIES_FILE_PATH);
		
		// CHARGER LES PROPRIÉTÉES DU FICHIER .properties
		try
		{
			properties.load(propertiesFile);
			
			this.server = properties.getProperty("server");
			this.port = Integer.parseInt(properties.getProperty("port"));
			this.database = properties.getProperty("database");
			this.username = properties.getProperty("username");
			this.password = properties.getProperty("password");
		}
		catch(IOException e)
		{
			System.out.println("Erreur lorsqu'on charge le fichier propriétées !");
		}
		
		return this;
	}

	@Override
	public DB getConnection() 
	{
		// CREATION D UNE CONNEXTION
		boolean authentication = false;
		DB mongoDB = null;
		
		try 
		{
			mongoClient = new MongoClient(this.server,this.port);
			mongoDB = mongoClient.getDB(this.database);
		
			// CHECK AUTHENTICATION
			authentication = mongoDB.authenticate(this.username, this.password.toCharArray());
		}
		catch (Exception e) 
		{
			System.out.println("Erreur lorsqu'on établie une connexion à la base de donées ! "+e.getMessage());
		}

		if(authentication)
			return mongoDB;
		return null;
	}
	
	//  METHODES QUI RENS LES IMPLEMENTATIONS D'ACCES AUX DONNEES
	// RENDS l'IMPLEMENTATION D'ACCES AUX DONNEES DE USERS
	public UserDAO getUserDaoImplementation()
	{
		return new UserDAOImpl(this);
	}
	
	// RENDS l'IMPLEMENTATION D'ACCES AUX DONNEES DE ARTICLE
	public ArticleDAO getArticleDaoImplementation()
	{
		return new ArticleDAOImpl(this);
	}
	
	// RENDS l'IMPLEMENTATION D'ACCES AUX DONNEES DE REFERENCE
	public ReferenceDAO getReferenceDaoImplementation()
	{
		return new ReferenceDAOImpl(this);
	}
}
