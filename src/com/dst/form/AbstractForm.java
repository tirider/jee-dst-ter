package com.dst.form;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public abstract class AbstractForm 
{	
	/**
	 * Errors holder object
	 */
	private Map<String, String> errors = new HashMap<String,String>();
	
	/**
	 * setErrors
	 * @param errors
	 */
	public void setErrors(String fieldName, String fieldMessage) 
	{
		this.errors.put(fieldName, fieldMessage);
	}
	
	/**
	 * getErrors
	 * @return
	 */
	public Map<String, String> getErrors() 
	{
		return errors;
	}
	
	/**
	 * Validates the form fields
	 */
	public abstract void validate();
	
	/**
	 * Validates user name.
	 * @param username
	 * @return
	 */
	public void validateUserName(String username)
	{
    	if (!username.matches("[a-zA-Z]{2,}") || username.matches("[\\s]+"))
    		this.setErrors("password", "The password is not correct.");
	}
	
	/**
	 * This method verify whether the given mail is valid
	 * @param email
	 * @return
	 */
	public void validateEmail(String email)
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
	 * Validates user password.
	 * @param password
	 */
	public void validatePassword(String password)
	{
		if(!password.matches("^.{6,}$"))
			this.setErrors("password", "The password is not correct.");
	}

	/**
	 * Validates user passwords.
	 * @param password
	 * @param passwordConfirm
	 */
	public void validatePasswords(String password, String passwordConfirm)
	{
		if(!password.matches("^.{6,}$") && !password.equals(passwordConfirm))
			this.setErrors("password", "Passwords are not correct.");
	}
}
