package com.dst.form;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordForm 
{
	// CONTROL DE RESULUT
	private Map<String, String> errors = new HashMap<String,String>();
	
	/**
	 * validate passwords
	 * @param currentpassword
	 * @param newpassword
	 * @param newpasswordconfirmation
	 */
	public void validate(String currentpassword, String newpassword, String newpasswordconfirmation) 
	{
		try
		{
			if(!currentpassword.matches("^.{6,}$"))
				this.setErrors("currentpassword", "Your current password is illegal...");
			if(!newpassword.matches("^.{6,}$") && newpassword.equals(newpasswordconfirmation))
				this.setErrors("newpassword", "Your current password is illegal...");
			if(!newpasswordconfirmation.matches("^.{6,}$"))
				this.setErrors("newpasswordconfirmation", "Your current password is illegal...");
			if(currentpassword.equals(newpassword))
				this.setErrors("newpassword", "Your new passwords are not accepted...");
		}
		catch(Exception e)
		{
			setErrors("general", "Error changing password !!!");
			System.err.println("Error changing password...");
		} 
	}
	
	public Map<String, String> getErrors() { return errors;}
	
	public void setErrors(String errorId, String message) { this.errors.put(errorId, message); }

}
