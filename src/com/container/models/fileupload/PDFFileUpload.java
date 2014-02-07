package com.container.models.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.container.models.encryption.Encryptor;

public class PDFFileUpload extends AbstractFileUpload
{	
	// CONSTRUCTEUR
	public PDFFileUpload(String uploaddirectory) 
	{	
		super(uploaddirectory);
	}	
	
	// UPLOAD LE FICHIER VERS LE UN DOSSIER TEMP HOLDING FOR INDEXATION
	public void upload(FileItem pdffile) throws Exception
	{
		// RECUPERATION DU TYPE DE FICHIER 
		this.fileformat   = pdffile.getContentType().toUpperCase();
		this.fileformat   = this.fileformat.substring(this.fileformat.lastIndexOf("/")+1); 
		
		if(this.fileformat.matches("[pP][dD][fF]"))
		{
			// RECUPERATION DU CODE SHA-1 DU FICHIER (CONTENUE)
			this.filecode     = Encryptor.ecryptToSha1(pdffile.getString());
			
			// RECUPERATION DU NOM DU FICHIER
			this.filename     =  pdffile.getName();
			
			// NOM TEMPORAIRE ASSOSIE AU FICHIER
			this.filetempname = Encryptor.genUniqueFileName(this.fileformat.toLowerCase());
			
			// RECUPERATION DE LA TAILLE DU FICHIER
			this.filesize     = (int) pdffile.getSize();
			
			// RE-CONSTRCTION DU FICHIER
	        File filepathname  = new File(this.uploadsdirectory +"/"+this.filetempname);
	        
			// COMMITING / WRITTING FILE ON SERVER
	        pdffile.write(filepathname);
	        
	        // VERIFICATION DU FORMAT DU FICHIER ET NOMBRE DE PAGES
	        try
	        {
		        if(! this.isPDF(filepathname))
		        {
		        	filepathname.delete();     
		        	
		        	this.unsigned  = true;
		        }
		        else
		        {
		        	// COMPUTE LE NOMBRE DE PAGES DU DOCUMENT
		            this.filenumberofpages = this.computeNumberOfPages(filepathname);
		            
		            this.unsigned  = false;
		        	this.message   = "Your document has been uploaded successfully";
		        }
	        }
	        catch(Exception e)
	        {
	        	// REPPORTING ERROR
	        	this.setErrors("signature", "Your file is unreadable. It is probably the result of a scan");
	        	
	        	// SUPPRIME LE FICHIER DU REPERTOIRE UPLOAD
	        	filepathname.delete(); 
	        }
		}
		else
		{
			// REPPORTING ERROR
			this.setErrors("signature", "Your file is unreadable. Please try a pdf file");
		}
    }

	// CALCUL LE NOMBRE DE PAGES
	private int computeNumberOfPages(File file)
	{
		PDDocument document = null;
		
		try 
		{
			document = PDDocument.load(file);
			return document.getNumberOfPages();
		} 
		catch (Exception e) 
		{
			// REPPORTING ERROR
			this.setErrors("pages", "It was not possible to compute the number of pages");
			
			// REPPORTING ERROR ON SERVER
			System.out.println(this.getClass().getName()+".computeNumberOfPages() : \n"+e.getMessage());
		}
		finally
		{
			try 
			{
				document.close();
			}
			catch (IOException e) 
			{
				// REPPORTING ERROR ON SERVER				
				System.out.println(this.getClass().getName()+" : \n"+e.getMessage());
			}
		}
		
		return 0;
	}
	
	// VERIFICATION D'AUTHENCITE DU FICHIER
	public boolean isPDF(File file) throws Exception
	{
		FileInputStream fis = new FileInputStream(file);
		PDDocument document = null;
		
		try
		{
			// PARSING THE FILE
			PDFParser pdfparser       = new PDFParser(fis);
			pdfparser.parse();
			
			document                  = PDDocument.load(file);
			PDFTextStripper extractor = new PDFTextStripper();
			String documentstring     = extractor.getText(document);

			if(documentstring.trim().isEmpty())
			{
				// REPPORTING ERROR
				this.setErrors("signature", "Your file is unreadable. It is probably the result of a scan");
				
				// REPPORTING ERROR ON SERVER
				System.out.println(this.getClass().getName()+":\n"+"Your file is unreadable. It is probably the result of a scan");
				
				return false;
			}
			
			return true;
		}
		catch(Exception e)
		{
			// REPPORTING ERROR
			this.setErrors("signature", "Your file is unreadable. It is probably the result of a scan");
			
			// REPPORTING ERROR ON SERVER
			System.out.println(this.getClass().getName()+".isPDF():\n"+e.getMessage());
		}
		finally
		{
			try 
			{
				fis.close();
				document.close();
			} 
			catch (IOException e) 
			{
				// REPPORTING ERROR ON SERVER
				System.out.println(this.getClass().getName()+": \n"+e.getMessage());
			}
		}
		
		return false;
	}
}