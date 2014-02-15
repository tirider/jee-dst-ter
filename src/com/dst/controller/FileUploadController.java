package com.dst.controller;

import java.io.File; 
import java.io.IOException; 
import java.io.PrintWriter;
import java.util.List; 

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 

import com.dst.beans.User;
import com.dst.global.Global;

/**
 * Servlet implementation class FileUploadController
 */
@WebServlet( name="FileUploadController", urlPatterns = "/UploadAction" )
@MultipartConfig
public class FileUploadController extends HttpServlet
{
	// ATTRIBUTES DE LA REQUETE
	private static final String ATTR_FILE              = "file";
	private static final String ATTR_SESSION           = "session";	
	private static final String ATTR_UPLOADS_DIRECTORY = "temps-directory";
	
	// VUE ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1  = "/WEB-INF/web/upload/upload.jsp";
	private static final String VIEW2  = "/WEB-INF/web/indexation/indexation.jsp";
	private static final String VIEW3  = "/LoginService";
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	// HOLD FILE UPLOAD LOCATION/MESSAGE TEXT CONTROL	
	private static String TEMP_DIRECTORY = new String();
		
	// OBJET POUR LE CONTROL DE LA TAILLE DU FICHIER
	private static final int THRESHOLD_SIZE   = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE    = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB	
	
	/**
	 * @see HttpServlet#init()
	 */
	public void init() throws ServletException
	{
		// RECUPERATION DU CHEMAIN (REPERTOIRE) POUR LES FICHIERS TEMPORAIRES
		TEMP_DIRECTORY = this.getServletContext().getInitParameter(ATTR_UPLOADS_DIRECTORY);
		
		Global.createDirectory(TEMP_DIRECTORY);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        // REDIRECTION VERS LA VUE CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	{
//		/*// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute(ATTR_SESSION);
//		
//		if(user != null && new File(TEMP_DIRECTORY).exists())
//		{
//			// OBJET METIER SET POUR UPLOADER UN FICHIER
//			AbstractFileUpload pdfuploader = new PDFFileUpload(TEMP_DIRECTORY);
//			
//			if(ServletFileUpload.isMultipartContent(request)) 
//	        {
//				try 
//	            { 
//					// PRETRAITEMENT POUR CONTROLER LA TAILLER DU FICHIER
//					DiskFileItemFactory factory = new DiskFileItemFactory();
//					factory.setSizeThreshold(THRESHOLD_SIZE);
//					factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
//					
//					ServletFileUpload upload = new ServletFileUpload(factory);
//					upload.setFileSizeMax(MAX_FILE_SIZE);
//					upload.setSizeMax(MAX_REQUEST_SIZE);
//				
//					// OBTIEN LISTE DE ITEMS(INPUTs) DU FORMULAIIRE ENVOYES PAR POST
//					List<FileItem> itemsSet = upload.parseRequest(request);
//					
//					int itemsSetSize = itemsSet.size();
//					
//					System.err.println("Yes................."+itemsSetSize);
//					
//					for(int i = 0; i < itemsSetSize; i++)
//					{	
//						if (! itemsSet.get(i).isFormField()) 
//		                {
//		                	pdfuploader.upload(itemsSet.get(i)); // OBJET METIER ENCHARGER D'UPLOADER LE FICHIER
//		                }
//					}
//	            }
//				catch(Exception e)
//				{
//					// ON PDFUPLOADER ERROR LIST
//					pdfuploader.setErrors("size", "The selected file exceeds the size allowed by the server");
//					
//					// ON SERVER
//					System.out.println(this.getClass().getName()+".multipart():\n"+e.getMessage());
//				}
//				
//				// AJOUT D'ATTRIBUTES A LA REQUETE
//				request.setAttribute(ATTR_FILE, pdfuploader);
//				
//				// REDIRECTION VERS LA VUE CORRESPONDANTE
//	        	if(!pdfuploader.getErrors().isEmpty())
//	        		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
//	        	else
//	        		this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
//	        }			
//		}
//		else
//		{
//			this.getServletContext().getRequestDispatcher(VIEW3).forward(request, response);
//		}*/
//	}
}																																																																																																																																																																														