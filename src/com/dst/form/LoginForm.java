package com.dst.form;

import java.util.HashMap;
import java.util.Map;
 
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 

public final class LoginForm 
{
	private String email 	= new String();
	private String password = new String();
	
	/**
	 * CONTROL DE RESULTATS
	 */
	private Map<String, String> errors = new HashMap<String,String>();

	/**
	 * Constructor with params.
	 */
	public LoginForm(String email, String password) 
	{ 
		this.email 		= email.toLowerCase();
		this.password 	= password.toLowerCase();
	}

	/**
	 * validates fields.
	 */
	public void validate() 
	{
		this.validateEmail(email);
		
		this.validatePassword(password);
	}
	
	/**
	 * validateEmail
	 * @param email
	 */
	private void validateEmail(String email)
	{
    	try 
    	{  
    		InternetAddress e = new InternetAddress(email);
        	e.validate();  
    	} 
    	catch (AddressException ex) 
    	{  
    		this.setErrors("email", "The email address is not correct.");
        }
	}
	
	/**
	 * validatePassword
	 * @param password
	 */
	private void validatePassword(String password)
	{
		if(!password.matches("^.{6,}$"))
			this.setErrors("password", "The password has not been entered.");
	}

	/**
	 * getErrors
	 * @return
	 */
	public Map<String, String> getErrors() { return errors; }
	
	/**
	 * setErrors
	 * @param name
	 * @param error
	 */
	public void setErrors(String name, String error) { this.errors.put(name, error); }
}
