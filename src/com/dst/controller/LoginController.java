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

import com.dst.model.dao.ReferenceDAO;
import com.dst.model.dao.UserDAO;
import com.dst.model.entitybeans.User;
import com.dst.model.filter.BloomFilter;
import com.dst.model.form.LoginForm;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(name="LoginController", urlPatterns = "/LoginService") 
public class LoginController extends HttpServlet
{	
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1 = "/ResearchService";
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
				session.setAttribute("session", user);	
				session.setAttribute("bloomfilter", bloomfilter);	
				
				// REDIRECTION SANS ERREURS
				response.sendRedirect(response.encodeURL(request.getContextPath()+VIEW1));				
			}
			else 
			{ 
				// UNABLE TO CREATE A SESSION
				session.setAttribute("session", null);
				
				// REGISTER FORM
				form.setErrors("auth", "Authentication unsuccessful."); 
				request.setAttribute("form", form);
				
				// REQUEST THE VIEW
				this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
			}
		}
		else
		{	
			// UNABLE TO CREATE A SESSION
			session.setAttribute("session", null);
			
			// REGISTER FORM
			request.setAttribute("form", form);
			
			// REQUEST THE VIEW
			this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
		}
	}
}