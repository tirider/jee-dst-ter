package com.dst.global;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

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
	
	/**
	 * ecryptToSha1
	 * @param content
	 * @return
	 */
	public static String ecryptToSha1(String content)
	{
		try 
		{
	        MessageDigest md = MessageDigest.getInstance("SHA1");
	        
	        md.update(content.getBytes());
	        
	        return new BigInteger( 1, md.digest() ).toString(16);
	    }
	    catch (NoSuchAlgorithmException e) { }
		
		return content;
	}	
	
	/**
	 * genUniqueFileName
	 * @param extention
	 * @return
	 * @throws IOException
	 */
	public static String genUniqueFileName(String extention) throws IOException
	{
		return UUID.randomUUID().toString()+new Date().getTime()+"."+extention;
	}	
	
	/**
	 * genUniqueChar
	 * @return
	 */
	public static String genUniqueChar()
	{
		String uuid = UUID.randomUUID().toString();
		
		return uuid.substring(0, uuid.indexOf("-"));
	}
}
