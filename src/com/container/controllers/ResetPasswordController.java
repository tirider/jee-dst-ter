package com.container.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

import com.container.models.beans.User;
import com.container.models.dao.DAOInterface;
import com.container.models.dao.UserDAO;
import com.container.models.dao.UserDAOImpl;
import com.container.models.forms.ResetPasswordForm;

/**
 * Servlet implementation class RecoveryPasswordService
 */
@WebServlet(name="PasswordRecoveryController", urlPatterns="/ResetPasswordService")
public class ResetPasswordController extends HttpServlet
{
	private static final String PROPERTIES_FILE_PATH  = "/com/container/models/dao/mail-reset-password.properties";
	
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_DAO = "dao";
	private static final String ATTR_USER = "user";
	private static final String ATTR_FORM = "form";
	private static final String ATTR_MESSAGE = "message";
	
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/outset/reset/resetPassword.jsp";
	private static final String VIEW2 = "/WEB-INF/outset/reset/resetPasswordMessage.jsp";
	
	// INTERFACE D'ACCES AUX DONNEES
	protected UserDAO userDAO;
	
	// AUTRES
	private String messaging = new String();
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;

	// POINT D'ENTRE AU SERVLET - LIKE A CONSTRUCTOR
	public void init() throws ServletException
	{
		// RECUPERATION L'OBJET D'ACCES AUX DONNEES
		DAOInterface dao = (DAOInterface) getServletContext().getAttribute(ATTR_DAO);
		
		this.userDAO = new UserDAOImpl(dao);
	}
	
	//HTTP GET METHOD
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}

	//HTTP POST METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// OBJET METIER
		ResetPasswordForm form = new ResetPasswordForm(this.userDAO);
		
		// RECUPERATION DU NOUVAU MOT DE PASSE
		User user = form.recover(request);
				
		// SEND USER INFORMATION PAR MAIL
		if(form.getErrors().isEmpty())
		{
			Properties properties = new Properties();
			ClassLoader loader    = Thread.currentThread().getContextClassLoader();
			InputStream propertiesFile = loader.getResourceAsStream(PROPERTIES_FILE_PATH);
			
			properties.load(propertiesFile);
			
			// RECUPERATION SU SERVEUR SMTP D OU VA ETRE ENVOYER LE MAIL			
			String server = properties.getProperty("server");	
			
			// CREATION DU SENDER
			String sender = properties.getProperty("sender");
			
			// RECUPERATION DU SUJET DU MAIL
			String subject = properties.getProperty("subject");
			 
			// RECUPERATION HEADER
			String mailheader = properties.getProperty("header");
			mailheader = mailheader.replace("?uid", user.getUsername());
			
			// RECUPERATION DU MESSAGE A ENVOYER
			String textmail = properties.getProperty("message");
	        textmail   = textmail.replace("?uid", user.getUsername());
	        textmail   = textmail.replace("?upassword", user.getPassword());			
			
			// RECUPERATION DU MESSAGE A ENVOYER
			String mailfooter = properties.getProperty("footer");
			
			// RECUPERATION DU RECIPIENT
			String to = user.getEmail();
			
			 // RECUPERATION DES PROPIETES DU SYSTEME
			 Properties props = System.getProperties();

			 // CONFIGURATION DU SERVEUR MAIL
			 props.setProperty("mail.smtp.host", server);
			
			 // RECUPERATION DE LA SESSION PAR DEFAUT
			 Session session = Session.getDefaultInstance(props);
			 
			 try
			 {
		        // CREATE A DEFAULT MIMEMESSAGE OBJECT
		        MimeMessage message = new MimeMessage(session);

		        // SET FROM: HEADER FIELD OF THE HEADER
		        message.setFrom(new InternetAddress(sender));

		        // SET TO: HEADER FIELD OF THE HEADER
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		        // SET UP THE SUBJECT
		        message.setSubject(subject);

		        // SET UP THE MAIL CONTECT
		        message.setText(mailheader+"\n\n"+textmail+"\n\n"+mailfooter);

		        // ENVOIE LE MESSAGE
		        Transport.send(message);		         
		     }
			 catch (MessagingException me) 
			 {
				 System.out.println(this.getClass().getName()+":\n"+me.getMessage());
		         messaging = "Problem connecting to the server. Please try later";
	         }			 

	        // PRINT MESSAGE ON SERVER	
	        System.out.println("The server : "+server);
	        System.out.println("The sender : "+sender);
	        System.out.println("The subject :"+ subject);
	        System.out.println(mailheader+"\n\n"+textmail+"\n\n"+mailfooter);
	        System.out.println("Sent message successfully....");			 
		}
		
		// AJOUT A LA REQUETE
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