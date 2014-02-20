package com.dst.controller;

import java.io.IOException; 

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dst.model.dao.UserDAO;
import com.dst.model.form.ResetPasswordForm;
import com.dst.model.util.Global;

/**
* Servlet implementation class RecoveryPasswordService
*/
@WebServlet(name="PasswordRecoveryController", urlPatterns="/ResetPasswordService")
public class ResetPasswordController extends HttpServlet
{
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/outset/reset/resetPassword.jsp";
	private static final String VIEW2 = "/WEB-INF/outset/reset/resetPasswordMessage.jsp";
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	* INTERFACE D'ACCES AUX DONNEES USER
	*/
	@EJB UserDAO userDAO;
	
	/**
	 * MAILER DESTINATION RESSOURCE
	 */
	@Resource(mappedName="jms/MailerDestinationResource")
	private Queue mailerDestinationResource;
	
	/**
	 * QUEUE CONNECTION FACTORY
	 */
	@Resource(mappedName="jms/__QueueConnectionFactory")
	private ConnectionFactory queueConnectionFactory;
	
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
		
		// SEND USER INFORMATION PAR MAIL
		if(form.getErrors().isEmpty())
		{
			if(userDAO.existsEmail(email))
			{
				String newPassword = Global.genUniqueChar();
					
                try 
                {
                    Connection connection = queueConnectionFactory.createConnection();
                    Session sessionQ = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    TextMessage message = sessionQ.createTextMessage();                            

                    message.setText(email+":"+newPassword);
                    
                    MessageProducer messageProducer = sessionQ.createProducer(mailerDestinationResource);

                    messageProducer.send(message);
                    
                    userDAO.updatePasswordByEmail(email, newPassword);

                }
                catch(JMSException e) 
                { 
					System.err.println("Problems connecting to the server. Please try later");
					form.setErrors("message", "Problem connecting to the server. Please try later");
                	e.printStackTrace(); 
                }
			} 
			else form.setErrors("email", "The email address does not exists.");
		}
		
		request.setAttribute("form", form);
		
		if(form.getErrors().isEmpty())
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