package com.container.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet(name="LogoutController", urlPatterns="/LogoutAction")
public class LogoutController extends HttpServlet 
{
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DE LA SESSION
		HttpSession session = request.getSession();
		
		// DESTRUCTION DE LA SESSION
		session.invalidate();
		
		// CONTROL DE REDIRECTION (REDIRECTION VERS LA PAGE D'AUTHENTICATION)
		response.sendRedirect(request.getContextPath());
	}
}
