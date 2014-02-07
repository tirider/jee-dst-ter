package com.container.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.container.models.fileupload.AbstractFileUpload;
import com.container.models.fileupload.PDFFileUpload;
import com.model.User;

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
	private static final String ATTR_UPLOADS_DIRECTORY = "uploads-directory";
	
	// VUE ASSOCIEE AU CONTROLLEUR
	private static final String VIEW1  = "/WEB-INF/web/upload/upload.jsp";
	private static final String VIEW2  = "/WEB-INF/web/indexation/indexation.jsp";
	
	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	// HOLD FILE UPLOAD LOCATION/MESSAGE TEXT CONTROL	
	private String uploadsdirectory = new String();
		
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
		this.uploadsdirectory = this.getServletContext().getInitParameter(ATTR_UPLOADS_DIRECTORY);
		
		// VERIFICATION DE L EXISTENCE DU REPERTOIRE UPLOADS
		this.directoryCreator(this.uploadsdirectory);
	}
	
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
		User user = (User) session.getAttribute(ATTR_SESSION);
		
		if(user != null && new File(this.uploadsdirectory).exists())
		{
			// OBJET METIER SET POUR UPLOADER UN FICHIER
			AbstractFileUpload pdfuploader = new PDFFileUpload(this.uploadsdirectory);
			
			if(ServletFileUpload.isMultipartContent(request)) 
	        {
				try 
	            { 
					// PRETRAITEMENT POUR CONTROLER LA TAILLER DU FICHIER
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setSizeThreshold(THRESHOLD_SIZE);
					factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
					
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setFileSizeMax(MAX_FILE_SIZE);
					upload.setSizeMax(MAX_REQUEST_SIZE);
				
					// OBTIEN LISTE DE ITEMS(INPUTs) DU FORMULAIIRE ENVOYES PAR POST
					List<FileItem> itemsSet = upload.parseRequest(request);
					
					int itemsSetSize = itemsSet.size();
					
					System.err.println("Yes................."+itemsSetSize);
					
					for(int i = 0; i < itemsSetSize; i++)
					{	
						if (! itemsSet.get(i).isFormField()) 
		                {
		                	pdfuploader.upload(itemsSet.get(i)); // OBJET METIER ENCHARGER D'UPLOADER LE FICHIER
		                }
					}
	            }
				catch(Exception e)
				{
					// ON PDFUPLOADER ERROR LIST
					pdfuploader.setErrors("size", "The selected file exceeds the size allowed by the server");
					
					// ON SERVER
					System.out.println(this.getClass().getName()+".multipart():\n"+e.getMessage());
				}
				
				// AJOUT D'ATTRIBUTES A LA REQUETE
				request.setAttribute(ATTR_FILE, pdfuploader);
				
				// REDIRECTION VERS LA VUE CORRESPONDANTE
	        	if(!pdfuploader.getErrors().isEmpty())
	        		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	        	else
	        		this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);
	        }			
		}
		else
		{
			this.getServletContext().getRequestDispatcher("/LoginService").forward(request, response);
		}
	}
	
	/**
	 * METHODE QUI GERE LA CREATION DE REPERTOIRES
	 * @param directoryname
	 */
	private void directoryCreator(String directoryname)
	{
		if(! new File(directoryname).exists())
		{
			System.out.println("Creating "+directoryname+" directory...");
			
			try
			{
				// CREATION DU REPERTOIRE
				new File(directoryname).mkdir();
			}
			catch(Exception e)
			{
				// REPPORTINF ERROR ON SERVER
				System.out.println(this.getClass().getName()+".directoryCreator():\n"+e.getMessage());
			}			
		}
	}	
}																																																																																																																																																																														