package com.dst.form;


public final class ResetPasswordForm extends AbstractForm
{
	private String email;
	
	/**
	 * Constructor with params.
	 * @param email
	 */
	public ResetPasswordForm(String email)
	{
		this.email = email;
	}
	
	public void validate() 
	{
		try
		{
			this.validateEmail(email);
		}
		catch(Exception e)
		{
			this.setErrors("general", "Resetting password failed.");
			System.err.println("Resetting password failed...");
		}
	}
}
