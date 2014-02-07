package com.container.models.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.container.models.beans.User;
import com.container.models.dao.UserDAO;

public final class ResetPasswordForm 
{
	// CHAMPS DU FORMULAIRE
	private static final String FIELD1 = "email";
	
	// INTERFACE D'ACCES AU DONNEES ON DB
	private UserDAO userDAO;
	
	//#####################################################################################	
	// CONTROL DE RESULUT
	private String result = new String();
	private Map<String, String> errors = new HashMap<String,String>();
	
	//#####################################################################################	
	// CONSTRUCTOR
	public ResetPasswordForm(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}
	
	//#####################################################################################	
	// FONCTION DE CONNEXION POUR UN UTILISATEUR	
	public User recover( HttpServletRequest request ) 
	{
		// RECUPERATION DES CHAPMS (PARAMETRES)  DU FORMAULAIRE
		String email  = request.getParameter(FIELD1);
		
		User user = new User();	
		
		try
		{
			// VERIFIE SI LE MAIL EST VALIDE
			this.handleEmail(email);
			
			// SI LE MAIL EST VALIDE
			if(this.getErrors().isEmpty())  
			{
				// VERIFIER SI EXISTE
				if(userDAO.existsEmail(email))
				{
					// GENERATE NEW SHORT PASSWORD
					String uuid = UUID.randomUUID().toString();
					String newpassword = uuid.substring(0, uuid.indexOf("-"));
					
					// EXUECUTE UPDATE
					boolean success = userDAO.updatePasswordByEmail(email, newpassword);
					
					if(success)
					{
						user = userDAO.findByEmail(email);
						user.setPassword(newpassword);
					}
					else
					{
						// REPORT THE ERROR
						this.setErrors(FIELD1, "It was not possible to update.");
						
						// RAPPEL DU MAIL SUR LE CHAMP DU FORMULAIRE EN CAS D ERREUR
						user.setEmail(email);						
					}
				}
				else
				{
					// REPORT THE ERROR
					this.setErrors(FIELD1, "Email address has not been found.");
					
					// RAPPEL DU MAIL SUR LE CHAMP DU FORMULAIRE
					user.setEmail(email);					
				}
			}
			else
			{				
				// RAPPEL DU MAIL SUR LE CHAMP DU FORMULAIRE
				user.setEmail(email);
			}			
		}
		catch(Exception e)
		{
			this.setErrors("general", "Resetting password failed.");
			System.out.println(this.getClass().getName()+":\n"+e.getMessage());
		}
		
		return user;
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
			this.setErrors(FIELD1, e.getMessage());
		}
	}
	
	private void validateEmail(String email) throws FormValidationException
	{	
		if (email != null) 
		{
			if ( ! email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) )
				throw new FormValidationException("Please enter a valid email address.");
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
