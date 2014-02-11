package com.dst.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileViewController
 */
@WebServlet(name="/FileViewController", urlPatterns="/FileViewerService")
public class FileViewController extends HttpServlet 
{
	// ATTRIBUTES DE LA REQUETE
	private static final String  ATTR_DOCUMENTS_DIRECTORY = "documents-directory";
	
	// HOLD FILE UPLOAD/INDEX/DOCUMENTS LOCATION
	private static String CORPUS_DIRECTORY  = new String();
	
	// VUE ASSOCIEE AU CONTROLLEUR
	private static final String VIEW3  = "/LoginService";
	
	//DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#init()
	 */
	public void init() throws ServletException
	{
		// RECUPERATION DE L'ADRESSE UO TROUVER OU STOCKER LES ATICLES+INDEX
		CORPUS_DIRECTORY = this.getServletContext().getInitParameter(ATTR_DOCUMENTS_DIRECTORY);
	}	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String docid        = request.getParameter("dc");
		String filename     = docid + ".pdf";
		String filepathname = CORPUS_DIRECTORY+"/"+filename;
		File file           = new File(filepathname); 
		
		if(!docid.isEmpty() && !filename.isEmpty() && file.exists())
		{
			byte[] filedata     = new byte[(int)file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(filedata);
			
			ServletOutputStream outputStream = null;
			try
			{
				response.reset();
		        response.setContentType("application/pdf");
		        response.setContentLength(filedata.length);
		        response.setHeader("Content-Disposition", "inline;filename="+filename);
		       
		        outputStream = response.getOutputStream();
		        outputStream.write(filedata); 	
			}
			finally
			{
				outputStream.flush();
		        outputStream.close();
		        
		        fis.close();
			}
		}
		else this.getServletContext().getRequestDispatcher(VIEW3).forward(request, response);
	} 
}
