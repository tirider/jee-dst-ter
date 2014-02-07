package com.container.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WelcomeController
 */
@WebServlet(name="WelcomeController", urlPatterns="/WelcomeService")
public class WelcomeController extends HttpServlet 
{
	// VUE ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/web/welcome/index.jsp";
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */     
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
}