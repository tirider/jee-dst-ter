package com.container.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.container.models.beans.Reference;
import com.container.models.dao.DAOInterface;
import com.container.models.dao.ReferenceDAO;
import com.container.models.dao.ReferenceDAOImpl;
import com.model.User;

/** CONTROLLEUR DE PAGINATION DES DOCUMENTS **/
@WebServlet(name="/DocumentsController", urlPatterns="/DocumentsInboxService")
public class MyDocumentsController extends HttpServlet 
{
	// ATTRIBUTES DE LA REQUETE
	private static final String  ATTR_DAO = "dao";
	private static final String  ATTR_SESSION  = "session";
	private static final String  ATTR_PAGE     = "page";
	private static final String  ATTR_LIST     = "documents";
	private static final String  ATTR_NO_PAGES = "noOfPages";
	private static final String  ATTR_CURRENT_PAGE = "currentPage";
	private static final String  ATTR_AFFECTED_ROWS = "affectedRows";
	
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1  = "/WEB-INF/web/mydocuments/mydocuments.jsp";
	private static final String VIEW2  = "/WelcomeService";
	
	// INTERFACE D'ACCES AUX DONNEES
	private ReferenceDAO referenceDAO;

	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	// POINT D'ENTRE AU SERVLET / LIKE A CONSTRUCTOR
	public void init() throws ServletException
	{
		// RECUPERATION DES L'OBJETS QUI CONTIENT L'ACCES AUX DONNEES
		DAOInterface dao  = (DAOInterface) getServletContext().getAttribute(ATTR_DAO);
		
		this.referenceDAO = new ReferenceDAOImpl(dao);
	}
	   
	// HTTP GET REQUEST
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
		HttpSession session = request.getSession();
		User user  = (User) session.getAttribute(ATTR_SESSION);
		
		if(user != null)
		{
			// PAR DEFAULT
			int page = 1;
			
			// RECUPERATION DU NUMERO DE PAGE A AFFICHER
			if(! (request.getParameter(ATTR_PAGE) == null))
				page = Integer.parseInt(request.getParameter(ATTR_PAGE));
			
			// MAX DE DOCUMENT PAR PAGES
			int maxRecordsPerPages = 10;
			
			// EXEC PAGINATION
			//List<Reference> references = referenceDAO.findByUserId(user.getId(), ((page - 1) * maxRecordsPerPages), maxRecordsPerPages);
			
			int affectedRows  = referenceDAO.getAffectedRows();
			int numberOfpages = (int) Math.ceil((float) affectedRows/maxRecordsPerPages);
			
			// SETTING UP ATTRs
			//request.setAttribute(ATTR_LIST, references);
	        request.setAttribute(ATTR_NO_PAGES, numberOfpages);
	        request.setAttribute(ATTR_CURRENT_PAGE, page);
	        request.setAttribute(ATTR_AFFECTED_ROWS, affectedRows);
			
			// REDIRECTION VERS LA VU CORRESPONDANTE
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
		}
	}

	// HTTP POST REQUEST
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// REDIRECTION VERS LA VU CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
	}
}