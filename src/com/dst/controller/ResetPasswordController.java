package com.dst.controller;

import java.io.IOException; 
import java.util.Properties;

import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dst.dao.UserDAO;
import com.dst.form.ResetPasswordForm;
import com.dst.global.Global;
import com.dst.model.User;

/**
* Servlet implementation class RecoveryPasswordService
*/
@WebServlet(name="PasswordRecoveryController", urlPatterns="/ResetPasswordService")
public class ResetPasswordController extends HttpServlet
{
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_USER = "user";
	private static final String ATTR_FORM = "form";
	private static final String ATTR_MESSAGE = "message";
	
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/outset/reset/resetPassword.jsp";
	private static final String VIEW2 = "/WEB-INF/outset/reset/resetPasswordMessage.jsp";
	
	// AUTRES
	private String messaging = new String();
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	* INTERFACE D'ACCES AUX DONNEES USER
	*/
	@EJB UserDAO userDAO;

	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}

	/**
	* @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String email = request.getParameter("email");
		
		// OBJET METIER
		ResetPasswordForm form = new ResetPasswordForm(email);
		form.validate();
		
		User user = new User();
		user.setEmail(email);
		
		// SEND USER INFORMATION PAR MAIL
		if(form.getErrors().isEmpty())
		{
			if(userDAO.existsEmail(email))
			{
				String newPassword = Global.genUniqueChar();
				
				Properties props = System.getProperties();
				
				props.setProperty("mail.smtp.host", "smtp.gmail.com");
				
				Session session = Session.getDefaultInstance(props);	
			
				try
				{
					MimeMessage message = new MimeMessage(session);
					
					message.setFrom(new InternetAddress("admin-user@google.com"));
					
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
					
					message.setSubject("About your password resetting");
					
					message.setText("Dear user,"+"\n\n" +
									"We have received a request for changing your password.\nSo your new password is : ?upassword"+"\n\n" +
									"For security reasons you should change your password\nsoon enough when your first connection.\n\nThanks, The Team");
					
					Transport.send(message);	
					
					userDAO.updatePasswordByEmail(email, newPassword);
				}
				catch (MessagingException me)
				{
					System.err.println("Problems connecting to the server. Please try later");
					messaging = "Problem connecting to the server. Please try later";
				}	
			} 
			else form.setErrors("email", "The email addresse does not exists.");
		}
		
		request.setAttribute(ATTR_USER, user);
		request.setAttribute(ATTR_FORM, form);
		request.setAttribute(ATTR_MESSAGE, messaging);
		
		if(messaging.trim().isEmpty() && form.getErrors().isEmpty())
		{
			// REDIRECTION VERS LA VUE CORRESPONDANTE
			this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
		}
		else
		{
			// REDIRECTION VERS LA VUE CORRESPONDANTE
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);	
		}
	}
}