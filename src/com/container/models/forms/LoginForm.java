package com.container.models.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.container.models.beans.User;
import com.container.models.dao.UserDAO;

public final class LoginForm 
{
	//#####################################################################################	
	// CHAMPS DU FORMULAIRE
	private static final String FIELD1 = "username";
	private static final String FIELD2 = "password";

	//#####################################################################################
	// INTERFACE D'ACCES AU DONNEES ON DB
	private UserDAO userDAO;

	//#####################################################################################
	// CONTROL DE RESULTAT
	private Map<String, String> errors = new HashMap<String,String>();

	//#####################################################################################
	// CONSTRUCTOR
	public LoginForm(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	//#####################################################################################	
	// FONCTION DE CONNEXION POUR UN UTILISATEUR
	public User connectUser(HttpServletRequest request) 
	{
		// RECUPERATION DES CHAPMS DU FORMAULAIRE
		String username = request.getParameter(FIELD1);
		String password = request.getParameter(FIELD2);
		
		User user = new User();	
		
		try
		{
			// MANIPULER LES CHAMPS
			this.handleUsername(username);
			this.handlePassword(password);
			
			// SI NON ERREURS LORSQUE HANDLING CHAMPS ALORS
			if(this.getErrors().isEmpty())  
			{
				if(this.userDAO.exists(username, password))
				{
					user = this.userDAO.findByUsername(username);
				}
				else
				{
					// GARANTIR LE RAPPELLE DU DERNIER USERNAME 
					// SAISI SUR LE CHAMP DU FORMUAIRE
					user.setUsername(username);	

					// CONTROL D'AFICHAGE DE MESSAGES D'ERREURS					
					this.setErrors("auth", "Please check your username or password.");
				}
			}
			else
			{
				// GARANTIR LE RAPPELLE DU DERNIER USERNAME TAPE SUR LE CHAMP
				user.setUsername(username);	
				
				// CONTROL D'AFICHAGE DE MESSAGES D'ERREURS
				this.setErrors("auth", "Authentication unsuccessful.");
			}
		}
		catch(Exception e)
		{
			// GESTION D'ERREURS
			this.setErrors("auth", "Authentication failed : Please try again");
			
			// WRITE ON SERVER
			System.out.println(this.getClass().getName()+":\n"+e.getMessage());
		}
		
		return user;
	}
	
	//#####################################################################################
	private void handleUsername(String username)
	{
		try
		{
			this.validateUsername(username);
		}
		catch(FormValidationException e)
		{
			this.setErrors(FIELD1, e.getMessage());
		}
	}
	
	private void validateUsername(String username) throws FormValidationException
	{
		if(username.trim().isEmpty())
			throw new FormValidationException("The user name has not been entered.");
	}
	
	//#####################################################################################
	private void handlePassword(String password)
	{
		try
		{
			this.validatePassword(password);
		}
		catch(FormValidationException e)
		{
			this.setErrors(FIELD2, e.getMessage());
		}
	}
	
	private void validatePassword(String password) throws FormValidationException
	{
		if(password.trim().isEmpty())
			throw new FormValidationException("The password has not been entered.");
	}

	//#####################################################################################	
	public Map<String, String> getErrors() { return errors; }
	public void setErrors(String name, String error) { this.errors.put(name, error); }
}
