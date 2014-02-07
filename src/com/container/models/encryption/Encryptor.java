package com.container.models.encryption;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

public class Encryptor 
{
	//#####################################################################################
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
	
	//#####################################################################################
	public static String genUniqueFileName(String extention) throws IOException
	{
		//
		String result = UUID.randomUUID().toString()+new Date().getTime()+"."+extention;
		
		return result;
	}
}
