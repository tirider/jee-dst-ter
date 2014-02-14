package com.dst.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
import com.dst.beans.User;
import com.dst.dao.UserDAO;
import com.dst.form.ChangePasswordForm;

/**
 * Servlet implementation class ChangePasswordController
 */
@WebServlet(name="ChangePasswordController", urlPatterns = "/ChangePasswordService") 
public class ChangePasswordController extends HttpServlet
{	
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_FORM 		= "form";
	private static final String ATTR_MESSAGE 	= "message";
	private static final String ATTR_SESSION  	= "session";
	
	//CHAMPS DU FORMULAIRE
	private static final String FIELD1 = "currentpassword";
	private static final String FIELD2 = "newpassword";
	private static final String FIELD3 = "newpasswordconfirmation";
	
	// VUES ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/web/security/passwordUpdate.jsp";
	
	// INTERFACE D'ACCES AUX DONNEES
	private String message = new String();
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	 * INTERFACE D'ACCES AUX DONNEES USER
	 */
	@EJB UserDAO userDAO;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
		HttpSession session = request.getSession();
		User user  = (User) session.getAttribute(ATTR_SESSION);
		
		// RECUPERATION DES CHAPMS (PARAMETRES)  DU FORMAULAIRE
		String currentPassword    = request.getParameter(FIELD1);
		String newPassword        = request.getParameter(FIELD2);
		String newPasswordConfirm = request.getParameter(FIELD3);
		
		// OBJET METIER
		ChangePasswordForm form = new ChangePasswordForm();
		form.validate(currentPassword, newPassword, newPasswordConfirm);
		
		if(form.getErrors().isEmpty())
		{
			if(userDAO.updatePasswordByEmail(user.getEmail(), newPassword))
				message = "success";
			else
				message = "failed";
		}
		else
		{
			message = "failed";
		}
		
		// TRANSFERE VERS LA REQUETE
		request.setAttribute(ATTR_FORM, form);
		request.setAttribute(ATTR_MESSAGE, message);
		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
}