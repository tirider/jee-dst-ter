package com.dst.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dst.beans.User;

/**
 * Servlet implementation class WelcomeController
 */
@WebServlet(name="WelcomeController", urlPatterns="/WelcomeService")
public class WelcomeController extends HttpServlet 
{
	// ATTRIBUTES DE LA REQUETE
	private static final String  ATTR_SESSION  = "session";
	
	// VUE ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/web/welcome/index.jsp";
	private static final String VIEW2 = "/LoginService";	
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */     
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
		HttpSession session = request.getSession();
		User user  = (User) session.getAttribute(ATTR_SESSION);
		
		if(user != null)
		{
			this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
		}
		else
		{
			this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
		}
	}
}