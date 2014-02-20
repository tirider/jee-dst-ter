package com.dst.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dst.model.entitybeans.User;
import com.dst.model.filter.BloomFilter;
import com.dst.model.javabeans.IndexedLuceneDocument;
import com.dst.model.searcher.FileSystemSearcher;
import com.dst.model.util.Global;

/**
 * Servlet implementation class ResearchController
 */
@WebServlet(name="/ResearchController", urlPatterns="/ResearchService")
public class ResearchController extends HttpServlet 
{	
	/**
	 * VUES ASSOCIEES AU CONTROLLEUR.
	 */
	private static final String VIEW1 = "/WEB-INF/web/search/search.jsp";
	private static final String VIEW2 = "/LoginService";	
	
	/**
	 * Index folder path holder.
	 */
	private static String INDEX_DIRECTORY = new String();
	
	/**
	 * DEFAULT SERIAL VERSION
	 */
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#init()
	 */
	public void init() throws ServletException
	{
		// RECUPERATION DU SHEMA DU REPERTOIRE
		INDEX_DIRECTORY = this.getServletContext().getInitParameter("index-directory");	
		
		// VERIFIER L EXISTENCE DU REPERTOIRE
		Global.createDirectory(INDEX_DIRECTORY);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */  
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
		HttpSession session = request.getSession();
		User user  = (User) session.getAttribute("session");
		
		if(user != null)
		{
			// REDIRECTION VERS LA VU CORRESPONDANTE
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
		}
		else
		{
			this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
		HttpSession session = request.getSession();
		
		// GET BLOMFILTER DATA
		BloomFilter bloomfilter = (BloomFilter) session.getAttribute("bloomfilter");
		
		// GETTING REQUESTED TEXT
		String query 	= request.getParameter("text");
		String message 	= new String();
		
		if(!query.trim().isEmpty())
		{
			// PREPARING SEARCHER 
			FileSystemSearcher fss = new FileSystemSearcher(INDEX_DIRECTORY);
			
			// START TIME DE LA RECHERCHE
			long starttime = System.currentTimeMillis();
			
			// EXECUTION DE LA RECHERCHE
			List<IndexedLuceneDocument> documents = fss.search(query, 10, bloomfilter);
			
			// END TIME DE LA RECHERCHE
			long endtime = System.currentTimeMillis();
			
			// TEMPS DE EXECUTION DE L'ALGORITHME
			double executiontime = (double) (endtime - starttime) /1000 ;
			
			int resultsize = documents.size();
			
			if(resultsize  == 0)
			{
				String m1 = String.format(" Your search - %s - did not match any documents.<br />", query).replaceAll(query, "<b>"+query+"</b>"); 
				String m2 = "Suggestions :<br /> ";
				String m3 = "Make sure all words are spelled correctly.<br /> Try different keywords. <br /> Try more general keywords.";
				
				// BUILDING MESSAGE
				message = m1+m2+m3;
			}
			else
			{
				// BUILDING MESSAGE				
				message = String.format("About %s result(s) found(s) in (%.2f seconds).", resultsize, executiontime);
			}
			
			request.setAttribute("text", query);
			request.setAttribute("documents", documents);
			request.setAttribute("message", message);
		}
		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
}