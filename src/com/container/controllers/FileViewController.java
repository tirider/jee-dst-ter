package com.container.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** SERVLET IMPLEMENTATION TO VIEW A FILE **/
@WebServlet(name="/FileViewController", urlPatterns="/FileViewerService")
public class FileViewController extends HttpServlet 
{
	// ATTRIBUTES DE LA REQUETE
	private static final String  ATTR_DOCUMENTS_DIRECTORY = "documents-directory";
	
	// HOLD FILE UPLOAD/INDEX/DOCUMENTS LOCATION
	private String  documentsdirectory  = new String();
	
	private static final long serialVersionUID = 1L;
       
	// POINT D'ENTRE AU SERVLET / LIKE A CONSTRUCTOR
	public void init() throws ServletException
	{
		// RECUPERATION DE L'ADRESSE UO TROUVER OU STOCKER LES ATICLES+INDEX
		this.documentsdirectory = this.getServletContext().getInitParameter(ATTR_DOCUMENTS_DIRECTORY);
	}	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String docid        = request.getParameter("dc");
		String filename     = docid + ".pdf";
		String filepathname = this.documentsdirectory+"/"+filename;
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

}
