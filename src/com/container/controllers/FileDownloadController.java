package com.container.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** SERVLET IMPLEMENTATION FOR DONLOADING FILES **/
@WebServlet(name="/FileDownloadController", urlPatterns="/DownloadService")
public class FileDownloadController extends HttpServlet 
{
	// ATTRIBUTES DE LA REQUETE
	private static final String  ATTR_DOCUMENTS_DIRECTORY = "documents-directory";
	
	// HOLD FILE UPLOAD/INDEX/DOCUMENTS LOCATION
	private String  documentsdirectory  = new String(); 
	
	// DEFAULT SERIAL VERSION
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
		
		ServletOutputStream stream = null; 
		BufferedInputStream buf = null; 
		
		try 
		{ 
			stream = response.getOutputStream();
			// set response headers 
			response.setContentType("application/pdf"); 
			response.setDateHeader("Expires", 0); 
			response.addHeader("Content-Disposition", "attachment; filename="+filename);
			response.setContentLength((int) file.length()); 
			buf = new BufferedInputStream(new FileInputStream(file)); 
			int readBytes = 0; 
			
			while ((readBytes = buf.read()) != -1) 
				stream.write(readBytes); 
		} 
		finally 
		{ 
			if (stream != null) stream.flush(); 
		
			stream.close(); 
		
			if (buf != null) buf.close(); 
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
