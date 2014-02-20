package com.dst.model.text;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFTextExtractor  implements ITextExtractor
{	
	private String title;
	private String summary;

	// DOCUMENT LOADER OBJECT
	protected PDDocument doc;

	// DOCUMENTS EXTRACTOR OBJECT
	protected PDFTextStripper extractor;
	
	/** CONSTRUCTEUR **/
	public PDFTextExtractor(File pdffile)
	{	
		this.title   = new String();
		this.summary = new String();
		
		try
		{
			if(pdffile.exists()) 
			{
				// LOAD THE PDF FILE
				this.doc = PDDocument.load(pdffile);
				
				// SET CHARSET ET READING TWO PAGES FROM PDF
				this.extractor = new PDFTextStripper("UTF-8");
				this.extractor.setStartPage(1);
				this.extractor.setEndPage(3);
			}
		}
		catch(IOException e)
		{
			// REPPORTING ERROR ON SERVER
			System.err.println("File does not exists! \n"+e.getMessage());
		}
	}

	/** CETTE METHODE RECUPERE LE TITRE + SOMMAIRE D UN DOC. PDF**/	
	public void extract() throws Exception
	{
		// GETTING TEXT FROM DOC LOADER
		String pdftext = this.extractor.getText(doc);

		// GET THE TEXT PAR LIGNE (ARRAY DE LIGNES)
		String[] lines = pdftext.split("\n");
		
		// COMPUTTING NOMBRE DE LIGNES ON DOCUMENT
		int size = lines.length;
		
		// RECUPERE UN POSSIBLE TRITRE DU DOCUMENT SUR LES TROIS PREMIERES LIGNES
		if(size >= 1)
			this.title = lines[0]+"\n"+lines[1];
		else
			this.title = "...";
		
		// PARSING PDF TEXT LOOKING FOR KEY WORDS
		int index = getKeyWordLine(lines);
		
		// LOOPING GETTING ABSTRACT SUR LES 3 PREMIER PARAGRAPHES (.)
		int counter = 0;
		for(int i = index; i < size; i++)
		{
			String line   = lines[i].toString();
			
			this.summary += line + "\n";
			
			if(line.replaceAll(" ", "").endsWith("."))
			{
				counter ++;
				
				if(counter == 3) { break; }
			}
		}
		
		// FERMETURE DU DOCUMENT
		doc.close();
	}
	
	
	@Override
	public String getTitle() throws Exception { return this.title; }
	
	@Override
	public String getSummary() throws Exception { return this.summary; }
	
	@Override
	public String getText() throws Exception { return this.title+"\n"+this.summary; }

	/** CETTE METHODE RENDS LA LIGNE OU SE TROUVE UNE OCCURRENCE DU MOTIF **/
	private static int getKeyWordLine(String[] lines)
	{
		int index = 0;
		int size  = lines.length;
		
		for(int i = 0; i < size; i++)
		{
			String line = lines[i];
						
			if(line.matches(".*\\s?^(([Aa][Bb][Ss][Tt][Rr][Aa][Cc][Tt])|([Rr].*[Ss][Uu][Mm].*))\\s??.*"))
				return i;
		}
		
		return index;
	}	
	
	/** CETTE METHODE RECUPERE LES LIGNES OU SE TROUVE UN OCCURRENCE DU MOTIF **/
	public static String extractPatternContent(String content, String pattern)
	{
		String result = new String();
				
		// CREE UN TABLEAU DE LIGNES
		String[] lines = content.split("\n");
		
		// PARSING EACH LINE, TO GET THE PATTERN LINE NUMBER
		int index = getPatternLine(lines, pattern);
		
		// COMPUTING LIST SIZE
		int size = lines.length;
		
		
		if(index == -1 ) 
			result = new String(""); 
		if(index == size)
			result = new String(lines[index].toString());
		if(index > -1 && index < (size-1)) 
			result = new String(lines[index]+"\n"+lines[index+1]);
		
		// FORMATTING THE RESULT STRING
		result = result.replaceAll(pattern, "<b>"+pattern+"</b>");		
		
		return result;
	}	
	
	/** CETTE METHODE CHERCHE AU MOI UNE OCCURRENCE DU MOTIF DANS CHAQUE LIGNE DU DOC. PDF **/
	private static int getPatternLine(String[] lines, String pattern)
	{
		int index = -1;
		int size  = lines.length;
		
		String regex = ".*\\s?^((["+pattern+"].*))\\s??.*";
		
		for(int i = 0; i < size; i++)
		{
			String line = lines[i];
		
			if(line.matches(regex))
				return i;
		}
		
		return index;
	}	
}
