package com.dst.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DAOFactoryInitialisator implements ServletContextListener 
{
    // METHODE DECLANCHABLE AU DEMARRAGE DE L'APPLICATION(SERVEUR)
    public void contextInitialized(ServletContextEvent event) 
    {
    	// RECUPERATION DU CONTEXT DE LA SERVLET
    	ServletContext servletcontext = event.getServletContext();
    	
    	String appname = servletcontext.getContextPath().replace("/", "");
    	System.out.println("Starting up application..."+appname);
    }

    // METHODE DECLANCHABLE A LA FERMETURE DE L'APPLICATION
    public void contextDestroyed(ServletContextEvent event) 
    {
    	System.out.println("Shutting down application...");
    }
	
}
