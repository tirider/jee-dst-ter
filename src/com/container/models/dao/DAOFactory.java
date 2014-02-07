package com.container.models.dao;


// FABRIQUE DES CONNECTIONS 
public class DAOFactory 
{
	// LA METHODE CRÉE UNE CONNECTION POUR MONGODB
	public static MongoDB createMongodbConnection()
	{
		return new MongoDB().getInstance();
	}
}
