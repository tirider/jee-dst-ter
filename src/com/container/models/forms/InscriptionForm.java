package com.container.models.forms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.container.models.beans.User;
import com.container.models.dao.UserDAO;

public final class InscriptionForm 
{
	// CHAMPS DU FORMULAIRE
	private static final String FIELD1 = "username";
	private static final String FIELD2 = "password";
	private static final String FIELD3 = "passwordconfirmation";
	private static final String FIELD4 = "email";
	private static final String FIELD5 = "group";
	
	// INTERFACE D'ACCES AU DONNEES ON DB
	private UserDAO userDAO;
	
	//#####################################################################################	
	// CONTROL DE RESULUT
	private String result = new String();
	private Map<String, String> errors = new HashMap<String,String>();
	
	//#####################################################################################	
	// CONSTRUCTOR
	public InscriptionForm(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}
	
	//#####################################################################################	
	// FONCTION DE CONNEXION POUR UN UTILISATEUR	
	public User inscrireUser( HttpServletRequest request ) 
	{
		// RECUPERATION DES CHAPMS (PARAMETRES)  DU FORMAULAIRE
		String username              = request.getParameter(FIELD1);
		String password              = request.getParameter(FIELD2);
		String passwordconfirmation  = request.getParameter(FIELD3);
		String email                 = request.getParameter(FIELD4);
		String group                 = request.getParameter(FIELD5);
		Date inscriptiondate         = new Date();
		
		User user = new User();	
		
		try
		{
			// MANIPULER LES CHAMPS
			this.handleUsername(username);
			this.handlePassword(password, passwordconfirmation);
			this.handleEmail(email);
			this.handleGroup(group);
			
			// CREATION DE L'OBJET
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setGroupid(group);			
			user.setInscriptiondate(inscriptiondate);
			
			// SI NON ERREURS LORSQUE HANDLING CHAMPS ALORS
			if(this.getErrors().isEmpty())  
			{				
				// CREATION DE L'UTILISATEUR
				boolean success = this.userDAO.create(user);
				
				if(! success)
				{					
					// GESTION D'ERREURS
					this.setErrors("general", "Registration failed.");
				}
			}
		}
		catch(Exception e)
		{
			this.setErrors("general", "Registration failed. Please try later.");
			
			System.out.println(this.getClass().getName()+":\n"+e.getMessage());
		}
		
		return user;
	}
	
	//#####################################################################################
	// VERIFICATION DU NOM D UTILISATEUR
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
		if ( username != null && username.length() >= 3 ) 
		{
			// VERIFICATION DE L EXISTENCE UN UTILISATEUR DU MEME NOM
			if(this.userDAO.existsUsername(username))
				throw new FormValidationException("Username unavailable. Note that we ignore periods and capitalization in usernames. Try another.");
		}
		else 
			throw new FormValidationException("User names must contain at least 3 characters.");
	}
	
	//#####################################################################################
	// VERIFICATION DU MOT DE PASSE
	private void handlePassword(String password, String passwordconfirmation)
	{
		try
		{
			this.validatePassword(password, passwordconfirmation);
		}
		catch(FormValidationException e)
		{
			this.setErrors(FIELD2, e.getMessage());
		}
	}
	
	private void validatePassword(String password, String passwordconfirmation) throws FormValidationException
	{
		if ( password != null && passwordconfirmation != null )
		{
			if ( ! password.equals( passwordconfirmation ) )
				    throw new FormValidationException( "The entered passwords are differents, please re-enter them." );
			else if ( password.length() < 6 )
						     throw new FormValidationException("Passwords must contain at least 6 characters.");
		}
		 else 
			    throw new FormValidationException("Please try again.");
	}
	
	//#####################################################################################
	// VERIFICATION DU SEXE D UTILISATEUR
	private void handleGroup(String group)
	{
		try
		{
			this.validateGroup(group);
		}
		catch(FormValidationException e)
		{
			this.setErrors(FIELD5, e.getMessage());
		}
	}
	
	private void validateGroup(String group) throws FormValidationException
	{
		if( group == null || group.trim().isEmpty())
			throw new FormValidationException("You should select a group.");
	}
	
	//#####################################################################################
	// VERIFICATION DE L'EMAIL
	private void handleEmail(String email)
	{
		try
		{
			this.validateEmail(email);
		}
		catch(FormValidationException e)
		{
			this.setErrors(FIELD4, e.getMessage());
		}
	}
	
	private void validateEmail(String email) throws FormValidationException
	{	
		if (email != null) 
		{
			if ( ! email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) )
				throw new FormValidationException("Please enter a valid email address.");
			
			// VERIFICATION DE L EXISTENCE UN UTILISATEUR DU MEME NOM
			if(userDAO.existsEmail(email))
				throw new FormValidationException("Account already exists under this email address.");
		}
		else 
			throw new FormValidationException("Please enter an email address."); 
	}
	
	//#####################################################################################
	public String getResultat()	{ return result; }
	public void setResultat(String resultat) { this.result = resultat; }
	
	//#####################################################################################
	public Map<String, String> getErrors() { return errors;}
	public void setErrors(String errorId, String message) { this.errors.put(errorId, message); }

}
