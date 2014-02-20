package com.dst.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.dst.model.util.Global;

@WebListener
public class AppInitContext implements ServletContextListener 
{
    /**
     * METHODE DECLANCHABLE AU DEMARRAGE DE L'APPLICATION(SERVEUR)
     */
    public void contextInitialized(ServletContextEvent event) 
    {
    	System.out.println("Starting up application...");
    	
    	ServletContext context = event.getServletContext();
    	
		String TEMP_DIRECTORY   = context.getInitParameter("temps-directory");
		String DOCUMENTS_DIRECTORY = context.getInitParameter("documents-directory");
		String INDEX_DIRECTORY  = context.getInitParameter("index-directory");
    	
    	System.out.println("Creating : "+TEMP_DIRECTORY);
		Global.createDirectory(TEMP_DIRECTORY);
    	
		System.out.println("Creating : "+DOCUMENTS_DIRECTORY);
		Global.createDirectory(DOCUMENTS_DIRECTORY);
		
		System.out.println("Creating : "+INDEX_DIRECTORY);		
		Global.createDirectory(INDEX_DIRECTORY);
    }

    /**
     * METHODE DECLANCHABLE A LA FERMETURE DE L'APPLICATION
     */
    public void contextDestroyed(ServletContextEvent event) 
    {
    	System.out.println("Shutting down application...");
    }
}
