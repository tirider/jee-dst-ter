package com.dst.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dst.model.beans.User;
import com.dst.model.dao.ArticleDAO;
import com.dst.model.dao.ReferenceDAO;
import com.dst.model.filter.BloomFilter;
import com.dst.model.indexer.FileSystemIndexer;

/**
 * Servlet implementation class DeleteDocumentController
 */
@WebServlet(name="DeleteDocumentController", urlPatterns="/DeleteDocumentAction")
public class DeleteDocumentController extends HttpServlet 
{
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1  = "/DocumentsInboxService";
	
	// HOLD FILE UPLOAD/INDEX/DOCUMENTS LOCATION
	private static String  CORPUS_DIRECTORY  = new String();
	private static String  INDEX_DIRECTORY   = new String();	

	// DEFAULT SERIAL VERSION
	private static final long serialVersionUID = 1L;
	
	/**
	 * INTERFACE D'ACCES AUX DONNEES USER
	 */
	@EJB ArticleDAO articleDAO;
	
	/**
	 * INTERFACE D'ACCES AUX DONNEES REFERENCE
	 */
	@EJB ReferenceDAO referenceDAO;
	
	/**
	 * @see HttpServlet#init()
	 */
	public void init() throws ServletException
	{
		// RECUPERATION L'ADRESSE UO TROUVER OU STOCKER LES ATICLES+INDEX
		CORPUS_DIRECTORY = this.getServletContext().getInitParameter("documents-directory");
		INDEX_DIRECTORY  = this.getServletContext().getInitParameter("index-directory");
	}	

	/** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) 
	**/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// RECUPERATION D'UNE INSTANCE DE LA SESSION
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("session");
		
		String docId   = request.getParameter("dc");
		String docName = request.getParameter("dn");
		String strpage = request.getParameter("page");
		
		if(user != null && docId != null && docName !=null && strpage!=null)
		{
			// RECUPERATION DE LA DENIERE PAGE VISITE
			int page   = Integer.parseInt(strpage);
			
			// LISTE DE UIDs ASSOCIEES A DOCID
			List<String> usersSet = this.referenceDAO.findUsersByArticleId(docId);
			int size = usersSet.size();
			
			if(size != 0)
			{
				// SI LE NOMBRE D UTILISATEURS QUI PARTAGENT LE DOCUMENT EST (1)
				// ALORS ON SUPPRIME DE PAR TOUT
				if(size == 1)
				{
					// SUPPRIMER LA REFERENCE DE LA TABLE ARTICLES
					articleDAO.delete(docId);
					
					// RE-CONSTRUCTION DU FICHIER
					File documentpathname = new File(CORPUS_DIRECTORY+"/"+docId+".pdf");
					
					// SUPPRIMER LA REFERENCE DE L INDEX
					FileSystemIndexer fsi = new FileSystemIndexer(INDEX_DIRECTORY);
					fsi.deleteFromIndex(documentpathname.getAbsolutePath());
					
					// SUPPRIMER LE DOCUMENT DU REPERTOIRE CORPUS			
					documentpathname.delete();
				}

				// SUPPRIMER LA REF. DE LA TABLE
				referenceDAO.delete(user.getId(), docId, docName);
				
				// SUPPRIMER DE BLOOM FILTER
				((BloomFilter) session.getAttribute("bloomfilter")).deleteFromFilter(docId);
				
				// REDIRECTION VERS LA PAGE CORRSPONDANTE
				this.getServletContext().getRequestDispatcher(VIEW1+"?service=paging&page="+page+"&lang=en").forward(request, response);							
			}		
			else this.getServletContext().getRequestDispatcher(VIEW1+"?service=paging&page="+page+"&lang=en").forward(request, response);
		}
		else this.getServletContext().getRequestDispatcher("/").forward(request, response);
	}
}