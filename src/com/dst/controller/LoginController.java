package com.dst.controller;

import java.io.IOException; 
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dst.beans.User;
import com.dst.dao.ReferenceDAO;
import com.dst.dao.UserDAO;
import com.dst.filter.BloomFilter;
import com.dst.form.LoginForm;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(name="LoginController", urlPatterns = "/LoginService") 
public class LoginController extends HttpServlet
{	
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_FORM    			= "form";
	private static final String ATTR_SESSION_USER      	= "session";
	private static final String ATTR_SESSION_USER_DOCS 	= "bloomfilter";
	
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1 = "/WelcomeService";
	private static final String VIEW2 = "/WEB-INF/outset/login/login.jsp";
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	 * INTERFACE D'ACCES AUX DONNEES USER
	 */
	@EJB UserDAO userDAO;
	
	/**
	 * INTERFACE D'ACCES AUX DONNEES REFERENCE
	 */
	@EJB ReferenceDAO referenceDAO;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
	}
	
	/** 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		// PREPARATION  D'UNE INSTANCE SESSION
		HttpSession session = request.getSession(true);
		
		// RECUPERATION DES DONNÉES DU FORMAULAIRE
		String email 	= request.getParameter("email");
		String password = request.getParameter("password");
		
		// INITIALISATION DU FORMULAIRE
		LoginForm form = new LoginForm(email,password);
		
		// VALIDATES LOGIN FORM
        form.validate();
        		
		if (form.getErrors().isEmpty()) 
		{	
			// CHECKS WHETHER USER EXISTS
			User user = userDAO.findByEmailPassword(email, password);
			
			if(user != null)
			{
				List<String> resultSet 	= referenceDAO.findArticlesByUserId(user.getId());
				BloomFilter bloomfilter = new BloomFilter(resultSet);
				
				// CREATION DE LA SESSION
				session.setAttribute(ATTR_SESSION_USER, user);	
				session.setAttribute(ATTR_SESSION_USER_DOCS, bloomfilter);	
				
				// REDIRECTION SANS ERREURS
				response.sendRedirect(response.encodeURL(request.getContextPath()+VIEW1));				
			}
			else 
			{ 
				// UNABLE TO CREATE A SESSION
				session.setAttribute(ATTR_SESSION_USER, null);
				
				// REGISTER FORM
				form.setErrors("auth", "Authentication unsuccessful."); 
				request.setAttribute(ATTR_FORM, form);
				
				// REQUEST THE VIEW
				this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
			}
		}
		else
		{	
			// UNABLE TO CREATE A SESSION
			session.setAttribute(ATTR_SESSION_USER, null);
			
			// REGISTER FORM
			request.setAttribute(ATTR_FORM, form);
			
			// REQUEST THE VIEW
			this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
		}
	}
}