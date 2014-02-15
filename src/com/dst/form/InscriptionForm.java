package com.dst.form;


public final class InscriptionForm extends AbstractForm	
{
	// CHAMPS DU FORMULAIRE
	private String username = new String();
	private String password = new String();
	private String passwordConfirmation = new String();
	private String email = new String();
	
	// CONTROL DE RESULUT
	private String result = new String();
	
	/**
	 * Constructor with params.
	 * @param username
	 * @param password
	 * @param passwordconfirmation
	 * @param email
	 */
	public InscriptionForm(String username, String password, 
		   String passwordconfirmation, String email)
	{
		this.username = username;
		this.password = password;
		this.passwordConfirmation = passwordconfirmation;
		this.email = email;
	}
	
	public void validate() 
	{
		try
		{
			this.validateUserName(username);
			this.validatePasswords(password, passwordConfirmation);
			this.validateEmail(email);
		}
		catch(Exception e)
		{
			this.setErrors("general", "Registration failed.");
		}
	}
	
	public String getResultat()	{ return result; }
	
	public void setResultat(String resultat) { this.result = resultat; }

}
