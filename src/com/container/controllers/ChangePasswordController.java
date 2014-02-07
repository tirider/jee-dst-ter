package com.container.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.container.models.dao.DAOInterface;
import com.container.models.dao.UserDAO;
import com.container.models.dao.UserDAOImpl;
import com.container.models.beans.User;
import com.container.models.forms.ChangePasswordForm;

@WebServlet(name="ChangePasswordController", urlPatterns = "/ChangePasswordService") 
public class ChangePasswordController extends HttpServlet
{	
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_DAO  = "dao";
	private static final String ATTR_USER = "user";
	private static final String ATTR_FORM = "form";
	private static final String ATTR_MESSAGE = "message";
	
	// VUES ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/web/security/passwordUpdate.jsp";
	
	// INTERFACE D'ACCES AUX DONNEES
	private UserDAO userDAO;
	private String message = new String();
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	// POINT D'ENTRE AU SERVLET - LIKE A CONSTRUCTOR
	public void init() throws ServletException
	{
		// RECUPERATION L'OBJET D'ACCES AUX DONNEES
		DAOInterface dao = (DAOInterface) getServletContext().getAttribute(ATTR_DAO);
		
		this.userDAO = new UserDAOImpl(dao);
	}
	
	// HTTP GET METHOD
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
	
	// HTTP POST METHOD
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// OBJET METIER
		ChangePasswordForm form = new ChangePasswordForm(this.userDAO);
	        
	    //TRAITEMENT DE LA REQUETE ET PREPARATION D UN BEAN
		User user = form.change(request);
		
		if(form.getErrors().isEmpty())
			message = "success";
		else
			message = "failed";
		
		// TRANSFERE VERS LA REQUETE
		request.setAttribute(ATTR_FORM, form);
		request.setAttribute(ATTR_USER, user);
		request.setAttribute(ATTR_MESSAGE, message);
		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
}