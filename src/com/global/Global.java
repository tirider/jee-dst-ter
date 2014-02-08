package com.global;

import java.io.File;

public class Global
{
	/**
	 * METHODE QUI GERE LA CREATION DE REPERTOIRES
	 * @param directoryname
	 */
	public static void createDirectory(String directoryname)
	{
		if(! new File(directoryname).exists())
		{
			System.out.println("Creating "+directoryname+" directory...");
			
			try
			{
				new File(directoryname).mkdir();
			}
			catch(Exception e)
			{
				System.err.println("Error creating the "+directoryname+" folder, check the directoy permissions...");
			}			
		}
	}	
	
	/**
	 * METHODE QUI GERE LE DEPLACEMENT D'UN REPERTOIRE VERS UN AUTRE
	 * @param directoryname
	 */	
	public static void moveDirectory(File a, File b)
	{
		try
		{
			System.out.println("Moving from "+a.getPath()+" to "+b.getPath()+"...");
			
			// DEPLACE L ARTILE COURANT VERS LE CORPUS D ARTICLES
			a.renameTo(b);
		}
		catch(Exception e)
		{
			System.err.println("Error moving the from "+a.getPath()+" to "+b.getPath()+"...");
		}		
	}	
}
