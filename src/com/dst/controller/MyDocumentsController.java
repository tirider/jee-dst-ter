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

import com.dst.beans.Reference;
import com.dst.beans.User;
import com.dst.dao.ReferenceDAO;
import com.dst.dao.UserDAO;
import com.dst.filter.BloomFilter;

/**
 * Servlet implementation class MyDocumentsController
 */
@WebServlet(name="/DocumentsController", urlPatterns="/DocumentsInboxService")
public class MyDocumentsController extends HttpServlet 
{
	// ATTRIBUTES DE LA REQUETE
	private static final String  ATTR_SESSION  		= "session";
	private static final String  ATTR_PAGE     		= "page";
	private static final String  ATTR_LIST     		= "documents";
	private static final String  ATTR_NO_PAGES 		= "noOfPages";
	private static final String  ATTR_CURRENT_PAGE	= "currentPage";
	private static final String  ATTR_AFFECTED_ROWS = "affectedRows";
	private static final String  ATTR_SESSION_USER_DOCS   = "bloomfilter";
	
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1  = "/WEB-INF/web/mydocuments/mydocuments.jsp";
	private static final String VIEW3  = "/LoginService";
	
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
		HttpSession session = request.getSession();
		User user  = (User) session.getAttribute(ATTR_SESSION);
		
		if(user != null)
		{
			// PAR DEFAULT
			int page = 1;
			
			// MAX DE DOCUMENT PAR PAGES
			int maxRecordsPerPages = 10;
						
			// RECUPERATION DU NUMERO DE PAGE A AFFICHER
			if(! (request.getParameter(ATTR_PAGE) == null))
				page = Integer.parseInt(request.getParameter(ATTR_PAGE));
			
			// EXEC PAGINATION
			List<Reference> references = referenceDAO.findByUserId(user.getId(), ((page - 1) * maxRecordsPerPages), maxRecordsPerPages);
			
			int affectedRows  = ((BloomFilter) session.getAttribute(ATTR_SESSION_USER_DOCS)).getFilter().size();
			int numberOfpages = (int) Math.ceil((float) affectedRows/maxRecordsPerPages);
			
			// SETTING UP ATTRs
			request.setAttribute(ATTR_LIST, references);
	        request.setAttribute(ATTR_NO_PAGES, numberOfpages);
	        request.setAttribute(ATTR_CURRENT_PAGE, page);
	        request.setAttribute(ATTR_AFFECTED_ROWS, affectedRows);
			
			// REDIRECTION VERS LA VU CORRESPONDANTE
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
		}
		else
		{
			this.getServletContext().getRequestDispatcher(VIEW3).forward(request, response);
		}
	}
}