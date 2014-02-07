package com.container.models.indexer;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import com.container.models.beans.LuceneDocument;

public class FileSystemIndexer 
{
	private IndexWriter indexWriter;
	private Analyzer analyzer;
	
	private static final Version LUCENE_ANALYZER_VERSION = Version.LUCENE_42;
		
	public FileSystemIndexer(String indexDirectoryPath) throws IOException 
	{
		// CREATE THE FILE PATH DIRECTORY
		File directoryPath = new File(indexDirectoryPath);
		
		// CREATE A LUCENE INDEX DIRECTORY
		Directory indexDirectory = FSDirectory.open(directoryPath);		
				
		// DEFINITION D UN ANALIZEUR POUR LA CREATION ET LA RECHERCHE D UN INDEX
		this.analyzer = new StandardAnalyzer(LUCENE_ANALYZER_VERSION);
		
		
		// HOLDS LA CONFIGURATION POUR LA CREATION D UN INDEX-WRITER
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(LUCENE_ANALYZER_VERSION, analyzer);

		this.indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
	}
	
	public void index(LuceneDocument luceneDocument) throws IOException
	{
		// INITIALISATION D UN DOCUMET
		Document document = new Document();
		
		// WRITING DOCUMENT SECTIONS
		document.add(new Field(LuceneDocument.ID, luceneDocument.getId(), StringField.TYPE_STORED));
		document.add(new Field(LuceneDocument.FORMAT, luceneDocument.getFormat(), StringField.TYPE_STORED));
		document.add(new Field(LuceneDocument.TITLE, luceneDocument.getTitle(), StringField.TYPE_STORED));
		document.add(new Field(LuceneDocument.CONTENT, luceneDocument.getContent(), TextField.TYPE_STORED));
		document.add(new Field(LuceneDocument.URL, luceneDocument.getUrl(), StringField.TYPE_STORED));
			
		// IF URL EXIST THEN UPDATE THE INDEX WITH THE NEW DOCUMENT OTHERWISE CREATS IT
		Term term = new Term(LuceneDocument.URL, luceneDocument.getUrl());
		indexWriter.updateDocument(term, document);
		//indexWriter.addDocument(document);
		
		// CLOSING INDEX WRITER
		indexWriter.commit();
		indexWriter.close();
	}
	
	public boolean deleteFromIndex(String url)
	{
		// PREPARATION D UN TERM
		Term term = new Term(LuceneDocument.URL, url);
		
		try 
		{
			// DELETTING ELEMENT FROM INDEX
			indexWriter.deleteDocuments(term);
			
			// CLOSING INDEX WRITER			
			indexWriter.commit();
			indexWriter.close();	
			
			return true;
		} 
		catch (IOException e)
		{
			// REPPORTING ERROR ON SERVER
			System.out.println(this.getClass().getName()+".deleteFomIndex():\n"+e.getMessage());
		}

		return false;
	}
}