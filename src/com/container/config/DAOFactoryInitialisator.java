package com.container.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.container.models.dao.DAOInterface;
import com.container.models.dao.DAOFactory;

@WebListener
public class DAOFactoryInitialisator implements ServletContextListener 
{
	// INTERFACE D'ACCES AUX DONNEES
	private DAOInterface dao;
	
    // METHODE DECLANCHABLE AU DEMARRAGE DE L'APPLICATION(SERVEUR)
    public void contextInitialized(ServletContextEvent event) 
    {
    	// RECUPERATION DU CONTEXT DE LA SERVLET
    	ServletContext servletcontext = event.getServletContext();
    	
    	// GET UNE INSTANCE MONGODB
    	this.dao = DAOFactory.createMongodbConnection();
    	
    	// AJOUT L'INSTANCE FABRIUE AU CONTEXT DE LA SERVLET
    	servletcontext.setAttribute("dao", this.dao);
    }

    // METHODE DECLANCHABLE A LA FERMETURE DE L'APPLICATION
    public void contextDestroyed(ServletContextEvent event) 
    {
		// FAIRE QQCH SI BESOIN
    }
	
}
