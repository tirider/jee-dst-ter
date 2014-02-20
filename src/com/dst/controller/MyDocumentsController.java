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

import com.dst.model.beans.Reference;
import com.dst.model.beans.User;
import com.dst.model.dao.ReferenceDAO;
import com.dst.model.dao.UserDAO;
import com.dst.model.filter.BloomFilter;

/**
 * Servlet implementation class MyDocumentsController
 */
@WebServlet(name="/DocumentsController", urlPatterns="/DocumentsInboxService")
public class MyDocumentsController extends HttpServlet 
{
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
		User user  = (User) session.getAttribute("session");
		
		if(user != null)
		{
			// PAR DEFAULT
			int page = 1;
			
			// MAX DE DOCUMENT PAR PAGES
			int maxRecordsPerPages = 10;
						
			// RECUPERATION DU NUMERO DE PAGE A AFFICHER
			if(! (request.getParameter("page") == null))
				page = Integer.parseInt(request.getParameter("page"));
			
			// EXEC PAGINATION
			List<Reference> references = referenceDAO.findByUserId(user.getId(), ((page - 1) * maxRecordsPerPages), maxRecordsPerPages);
			
			int affectedRows  = ((BloomFilter) session.getAttribute("bloomfilter")).getFilter().size();
			int numberOfpages = (int) Math.ceil((float) affectedRows/maxRecordsPerPages);
			
			// SETTING UP ATTRs
			request.setAttribute("documents", references);
	        request.setAttribute("noOfPages", numberOfpages);
	        request.setAttribute("currentPage", page);
	        request.setAttribute("affectedRows", affectedRows);
			
			// REDIRECTION VERS LA VU CORRESPONDANTE
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
		}
		else
		{
			this.getServletContext().getRequestDispatcher(VIEW3).forward(request, response);
		}
	}
}