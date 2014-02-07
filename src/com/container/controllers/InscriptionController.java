package com.container.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.container.models.dao.DAOInterface;
import com.container.models.dao.GroupDAO;
import com.container.models.dao.GroupDAOImpl;
import com.container.models.dao.UserDAO;
import com.container.models.dao.UserDAOImpl;
import com.container.models.beans.Group;
import com.container.models.beans.User;
import com.container.models.forms.InscriptionForm;

@WebServlet(name="InscriptionController", urlPatterns = "/InscriptionService") 
public class InscriptionController extends HttpServlet
{	
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_DAO = "dao";
	private static final String ATTR_USER = "user";
	private static final String ATTR_FORM = "form";
	private static final String ATTR_MESSAGE = "message";
	private static final String ATTR_GROUPS = "groups";
	
	// VUES ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/outset/inscription/inscription.jsp";
	private static final String VIEW2 = "/WEB-INF/outset/inscription/inscriptionMessage.jsp";
	
	// INTERFACE D'ACCES AUX DONNEES
	private UserDAO userDAO;
	private GroupDAO groupDAO;
	private List<Group> groups = null;
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	// POINT D'ENTRE AU SERVLET - LIKE A CONSTRUCTOR
	public void init() throws ServletException
	{
		// RECUPERATION L'OBJET D'ACCES AUX DONNEES
		DAOInterface dao = (DAOInterface) getServletContext().getAttribute(ATTR_DAO);
		
		this.userDAO  = new UserDAOImpl(dao);
		this.groupDAO = new GroupDAOImpl(dao);
		
		// RECUPERATION DE LA LISTE DE GROUPES
		this.groups = this.groupDAO.findAll();
	}
	
	// HTTP GET METHOD
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		// ADD A LA REQUEST
		request.setAttribute(ATTR_GROUPS, this.groups);
		
        // REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
	
	// HTTP POST METHOD
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// OBJET METIER
		InscriptionForm form = new InscriptionForm(this.userDAO);
        
        //TRAITEMENT DE LA REQUETE ET PREPARATION D UN BEAN
		User user = form.inscrireUser(request);
		
		// TRANSFERE VERS LA REQUETE
		request.setAttribute(ATTR_FORM, form);
		request.setAttribute(ATTR_USER, user);
		request.setAttribute(ATTR_MESSAGE, form.getResultat());
		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		if(form.getErrors().isEmpty())
		{
			this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
		}
		else
		{
			// ADD A LA REQUEST
			request.setAttribute(ATTR_GROUPS, this.groups); 
			
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
		}
	}
}