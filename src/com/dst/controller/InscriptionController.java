package com.dst.controller;

import java.io.IOException;
import java.util.Date; 

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
  
import com.dst.dao.UserDAO;
import com.dst.form.InscriptionForm;
import com.dst.model.User;

/**
 * Servlet implementation class FileUploadController
 */
@WebServlet(name="InscriptionController", urlPatterns = "/InscriptionService") 
public class InscriptionController extends HttpServlet
{	
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_USER 		= "user";
	private static final String ATTR_FORM 		= "form";
	
	// VUES ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/outset/inscription/inscription.jsp";
	private static final String VIEW2 = "/WEB-INF/outset/inscription/inscriptionMessage.jsp";
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	 * INTERFACE D'ACCES AUX DONNEES USER
	 */
	@EJB UserDAO userDAO;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
        // REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DES CHAPMS (PARAMETRES)  DU FORMAULAIRE
		String username              = request.getParameter("username");
		String password              = request.getParameter("password");
		String passwordconfirmation  = request.getParameter("passwordconfirmation");
		String email                 = request.getParameter("email");
		Date inscriptiondate         = new Date();
		
		// VALIDATING FORM DATA
		InscriptionForm form = new InscriptionForm(username, password, passwordconfirmation, email);
        form.validate();
        
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setInscriptionDate(inscriptiondate);
        
		request.setAttribute(ATTR_USER, user);
		
		if(form.getErrors().isEmpty())
		{      
			if(!userDAO.existsEmail(email))
			{
				userDAO.save(user);
				
				// REDIRECTION VERS LA VUE CORRESPONDANTE
				this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
			}
			else
			{
				form.setErrors("email", "The email addresse exists already.");
				
				request.setAttribute(ATTR_FORM, form);
				
				// REDIRECTION VERS LA VUE CORRESPONDANTE
				this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
			}
		}
		else
		{
			request.setAttribute(ATTR_FORM, form);
			
			// REDIRECTION VERS LA VUE CORRESPONDANTE
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
		}
	}
}