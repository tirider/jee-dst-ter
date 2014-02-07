package com.container.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.container.filters.BloomFilter;
import com.container.models.beans.LuceneDocument;
import com.container.models.indexer.FileSystemIndexer;
import com.container.models.textextractor.InterfaceArticleTextExtractor;
import com.container.models.textextractor.PDFArticleTextExtractor;
import com.dao.ArticleDAO;
import com.dao.ReferenceDAO;
import com.model.Article;
import com.model.Reference;
import com.model.User;

/**
 * Servlet implementation class IndexationController
 */
@WebServlet(name="IndexationController", urlPatterns = "/IndexationService")
public class IndexationController extends HttpServlet
{
	// ATTRIBUTES DE LA REQUETE
	private static final String  ATTR_UPLOADS_DIRECTORY   = "uploads-directory";
	private static final String  ATTR_DOCUMENTS_DIRECTORY = "documents-directory";
	private static final String  ATTR_INDEX_DIRECTORY     = "index-directory";
	private static final String  ATTR_SESSION             = "session";
	private static final String  ATTR_SESSION_USER_DOCS   = "bloomfilter";
	
	// VUES ASSOCIEES AU CONTROLLEUR
	private static final String VIEW1  = "/UploadAction";
	private static final String VIEW2  = "/WEB-INF/web/indexation/indexationMessage.jsp";
	
	// HOLD FILE UPLOAD/INDEX/DOCUMENTS LOCATION
	private String  uploadsdirectory    = new String();
	private String  documentsdirectory  = new String();
	private String  indexdirectory      = new String();	

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
		this.uploadsdirectory   = this.getServletContext().getInitParameter(ATTR_UPLOADS_DIRECTORY);
		this.documentsdirectory = this.getServletContext().getInitParameter(ATTR_DOCUMENTS_DIRECTORY);
		this.indexdirectory     = this.getServletContext().getInitParameter(ATTR_INDEX_DIRECTORY);

		// VERIFICATION DE L EXISTENCE DES REPERTOIRES
		this.directoryCreator(this.uploadsdirectory);
		this.directoryCreator(this.documentsdirectory);
		this.directoryCreator(this.indexdirectory);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        // REDIRECTION VERS LA VU CORRESPONDANTE
		this.getServletContext().getRequestDispatcher(VIEW1).forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		if(new File(this.uploadsdirectory).exists() || 
		   new File(this.uploadsdirectory).exists() || 
		   new File(this.uploadsdirectory).exists() )
		{	
			// RECUPERATION DE L'IDENTIFIENT DE LA SESSION
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(ATTR_SESSION);
			
			// RECUPERATION DES INFORMATION SUR LE FICHIER UPLODE
			String filecode           = request.getParameter("filecode");      // code sha-1 du fichier pdf
			String fileformat         = request.getParameter("fileformat");
			String generatedfilename  = filecode+"."+fileformat.toLowerCase(); // generated nom final du fichier dans le disque codesha1.pdf
			String currentfilename    = request.getParameter("filename");      // nom actuelle du fichier .pdf
			String tempfilename       = request.getParameter("filetempname");  // new temp name du fichier = code-xx-x.pdf
			Integer filenumberofpages = Integer.parseInt(request.getParameter("filenumberofpages"));
			Integer filesize          = Integer.parseInt(request.getParameter("filesize"));
			
			System.out.println(filecode);
			System.out.println(fileformat);
			System.out.println(generatedfilename);
			System.out.println(currentfilename);
			System.out.println(tempfilename);
			System.out.println(filenumberofpages);
			System.out.println(filesize);
			
			if(user != null)
			{
				// RE-CONSTRUCTION DES FICHIERS
				File tempfilepathname     = new File(this.uploadsdirectory+"/"+tempfilename);
				File lastfilepathname     = new File(this.documentsdirectory+"/"+generatedfilename);
				
				// VERIFIER SI L ARTICLE A INDEXER EXISTE DANS LA BD
				boolean exists = articleDAO.exists(filecode);
				
				if(exists) 
				{
					// SUPPRIMER LE FICHIER CURRANT DU REPERTOIRE UPLOADS-DIRECTORY
					tempfilepathname.delete();
				}
				else
				{
					// INDEXATION
					InterfaceArticleTextExtractor textextractor = new PDFArticleTextExtractor(tempfilepathname);
					LuceneDocument luceneDocument = new LuceneDocument();
					
					try 
					{
						// EXTRACTION DU TEXTE
						textextractor.extract();
						
						// CREATION DU DOCUMENT LUCENE A INDEXER
						luceneDocument.setId(filecode);
						luceneDocument.setFormat(fileformat);
						luceneDocument.setTitle(textextractor.getTitle());
						luceneDocument.setContent(textextractor.getSummary());
						luceneDocument.setUrl(request.getContextPath()+"/documents/"+lastfilepathname.getName());
						
						// COMMITING INDEX
						FileSystemIndexer fsi = new FileSystemIndexer(this.indexdirectory);
						fsi.index(luceneDocument);
					} 
					catch (Exception e) 
					{
						// REPORTING ERROR ON SERVER
						System.out.println(this.getClass().getName()+".indexing():\n"+e.getMessage());
					}
					
					// PREPARATION DU DOCUMENT
					Article article  = new Article();
					article.setId(filecode);
					article.setName(generatedfilename);
					article.setFormat(fileformat);
					article.setSize(filesize);
					article.setNumberOfPages(filenumberofpages);
					article.setCreationDate(new Date());
					
					// SAVING NEW DOCUMENT
					this.articleDAO.create(article);
					
					// UPDATEING THE FALSE BLOOM FILTER
					((BloomFilter) session.getAttribute(ATTR_SESSION_USER_DOCS)).addToFilter(filecode);
					
					// DEPLACEMENT DU DOCUMENT VERS LE CORPUS
					this.directoryMover(tempfilepathname, lastfilepathname);
				}
				
				// EXISTE OU NON LE DOCUMENT ON DOIT BIEN RAJOUTER LA REFERENCE
				// DE L'ARTICLE A L UTILISATEUR SI CELLE-CI N EXISTE DEJA
				// UID -> AID -> ANAME
				exists = this.referenceDAO.find(user.getId(), filecode, currentfilename);
				
				if(! exists)
				{
					// ASSOCIER USERID AVEC DOCIDC (INSERT)			
					Reference reference = new Reference();
					reference.getUser().setId(user.getId());
					reference.getArticle().setId(filecode);
					reference.setName(currentfilename);
					reference.setIndexationDate(new Date());
					
					// COMMITING / SAVE ON DATABASE
					this.referenceDAO.create(reference);
				}
				
				// REDIRECTION VERS LA VU CORRESPONDANTE
				this.getServletContext().getRequestDispatcher(VIEW2).forward(request, response);				
			}
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
	
	/**
	 * METHODE QUI GERE LE DEPLACEMENT D'UN REPERTOIRE VERS UN AUTRE
	 * @param directoryname
	 */	
	private void directoryMover(File a, File b)
	{
		try
		{
			System.out.println("Moving from "+a.getPath()+" to "+b.getPath()+"...");
			
			// DEPLACE L ARTILE COURANT VERS LE CORPUS D ARTICLES
			a.renameTo(b);
		}
		catch(Exception e)
		{
			// REPORTING ERROR ON SERVER
			System.out.println(this.getClass().getName()+".directoryMover():\n"+e.getMessage());
		}		
	}
}
