package com.dst.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
import com.dst.model.dao.UserDAO;
import com.dst.model.entitybeans.User;
import com.dst.model.form.ChangePasswordForm;

/**
 * Servlet implementation class ChangePasswordController
 */
@WebServlet(name="ChangePasswordController", urlPatterns = "/ChangePasswordService") 
public class ChangePasswordController extends HttpServlet
{	
	// VUES ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1 = "/WEB-INF/web/security/passwordUpdate.jsp";
	
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
		User user  = (User) session.getAttribute("session");
		
		// RECUPERATION DES CHAPMS (PARAMETRES)  DU FORMAULAIRE
		String currentPassword    = request.getParameter("currentpassword");
		String newPassword        = request.getParameter("newpassword");
		String newPasswordConfirm = request.getParameter("newpasswordconfirmation");
		
		// OBJET METIER
		ChangePasswordForm form = new ChangePasswordForm();
		form.validate(currentPassword, newPassword, newPasswordConfirm);
		
		String message = new String();
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
		request.setAttribute("form", form);
		request.setAttribute("message", message);
		
		// REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
}