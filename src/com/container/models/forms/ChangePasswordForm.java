package com.container.models.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.container.models.beans.User;
import com.container.models.dao.UserDAO;
import com.container.models.encryption.Encryptor;

public class ChangePasswordForm 
{
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_SESSION = "session";
	
	//CHAMPS DU FORMULAIRE
	private static final String FIELD1 = "currentpassword";
	private static final String FIELD2 = "newpassword";
	private static final String FIELD3 = "newpasswordconfirmation";
		
	// INTERFACE D'ACCES AU DONNEES ON DB
	private UserDAO userDAO;
		
	//#####################################################################################	
	// CONTROL DE RESULUT
	private String result = new String();
	private Map<String, String> errors = new HashMap<String,String>();
		
	//#####################################################################################	
	// CONSTRUCTOR
	public ChangePasswordForm(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}
	
	//#####################################################################################	
	public User change(HttpServletRequest request) 
	{
		// RECUPERATION DE L ID DE L UTILISATEUR
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(ATTR_SESSION);
			
		// RECUPERATION DES CHAPMS (PARAMETRES)  DU FORMAULAIRE
		String currentpassword         = request.getParameter(FIELD1);
		String newpassword             = request.getParameter(FIELD2);
		String newpasswordconfirmation = request.getParameter(FIELD3);
		
		try
		{
			// MANIPULER LES CHAMPS
			this.handleCurrentPassword(user.getPassword(), currentpassword);
			this.handleNewPassword(newpassword, newpasswordconfirmation);
			
			// SI NON ERREURS LORSQUE HANDLING CHAMPS ALORS
			if(this.getErrors().isEmpty())  
			{				
				boolean success = this.userDAO.updatePasswordByEmail(user.getEmail(), newpasswordconfirmation);
				
				if(!success)
				{					
					// GESTION D'ERREURS
					this.setErrors("general", "Operation failed.");
				}
			}
			else
			{				
				setErrors("general","Operation failed. Please correct the errors.");
			}
		}
		catch(Exception e)
		{
			setErrors("general", "It's not possible to continue with the operation.");
			System.out.println(this.getClass().getName()+":\n"+e.getMessage());
		}
		
		return user;
	}
	
	//#####################################################################################
	private void handleCurrentPassword(String currentpassword1, String currentpassword2)
	{
		try
		{
			this.validateCurrentPassword(currentpassword1, currentpassword2);
		}
		catch(FormValidationException e)
		{
			this.setErrors(FIELD1, e.getMessage());
		}
	}
	
	private void validateCurrentPassword(String currentpassword1, String currentpassword2) throws FormValidationException
	{
		if (currentpassword2 != null && currentpassword2.length() > 0 ) 
		{
			if(currentpassword1 != null && currentpassword1.length() > 0)
			{
				currentpassword2 = Encryptor.ecryptToSha1(currentpassword2);
				
				if(!currentpassword1.equals(currentpassword2))
					throw new FormValidationException("The current password is not correct.");
			}
			else
				throw new FormValidationException("Sorry ! we can not find the username associated.");
		}
		else 
			throw new FormValidationException("The current password was not entered.");
	}
	
	//#####################################################################################
	private void handleNewPassword(String newpassword, String newpasswordconfirmation)
	{
		try
		{
			this.validatePassword(newpassword, newpasswordconfirmation);
		}
		catch(FormValidationException e)
		{
			this.setErrors(FIELD2, e.getMessage());
		}
	}
	
	private void validatePassword(String newpassword, String newpasswordconfirmation) throws FormValidationException
	{
		if ( newpassword != null && newpassword.length() >= 6 && newpasswordconfirmation != null && newpasswordconfirmation.length() >= 6)
		{
			if ( ! newpassword.equals( newpasswordconfirmation ) )
				    throw new FormValidationException("The entered passwords are differents, please re-enter them.");
		}
		 else 
			    throw new FormValidationException("Passwords must contain at least 6 characters.");
	}
	
	//#####################################################################################
	public String getResultat()	{ return result; }
	public void setResultat(String resultat) { this.result = resultat; }
	
	//#####################################################################################
	public Map<String, String> getErrors() { return errors;}
	public void setErrors(String errorId, String message) { this.errors.put(errorId, message); }

}
