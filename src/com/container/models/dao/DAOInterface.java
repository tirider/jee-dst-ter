package com.container.models.dao;

public interface DAOInterface 
{
	// RENS UNE INSTANCE DE LA BASE DE DONNEES
	public DAOInterface getInstance();
	
	// RENDS UNE CONNECTION A LA BASE DE DONNEES
	public Object getConnection();
}
