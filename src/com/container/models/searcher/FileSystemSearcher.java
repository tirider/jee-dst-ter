package com.container.models.searcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.container.filters.BloomFilter;
import com.container.models.beans.IndexedLuceneDocument;
import com.container.models.textextractor.PDFArticleTextExtractor;

/**  **/
public class FileSystemSearcher 
{
	private Directory indexDirectory;
	private DirectoryReader indexReader;
	private IndexSearcher indexSearcher;
	private Analyzer analyzer;
	private MultiFieldQueryParser multiFieldParser;
	
	private static final Version LUCENE_ANALYZER = Version.LUCENE_42;
	private static final String[] FIELDS         = {"title","content"};
	
	/**
	 * FileSystemSearcher
	 * @param indexDirectoryPath
	 * @throws IOException
	 */
	public FileSystemSearcher(String indexDirectoryPath) throws IOException
	{
		super();
		
		// CREATE THE FILE PATH DIRECTORY
		File directoryPath = new File(indexDirectoryPath);
		
		// CREATE A LUCENE INDEX DIRECTORY
		this.indexDirectory = FSDirectory.open(directoryPath);
		
		// STARTING THE DIRECTORY READER
		this.indexReader = DirectoryReader.open(this.indexDirectory);
		
		// HOLDS THE INDEX DIRECTORY
		this.indexSearcher = new IndexSearcher(indexReader);
		
		// DEFINITION D UN ANALIZEUR POUR LA CREATION ET LA RECHERCHE D UN INDEX
		this.analyzer = new StandardAnalyzer(LUCENE_ANALYZER);
		
		// INITIALISER
		this.multiFieldParser = new MultiFieldQueryParser(LUCENE_ANALYZER, FIELDS, analyzer);
	}

	/**
	 * search
	 * @param queryString
	 * @param resultSize
	 * @param bloomfilter
	 * @return
	 * @throws IOException
	 * @throws org.apache.lucene.queryparser.classic.ParseException
	 */
	public List<IndexedLuceneDocument> search(String queryString, int resultSize, BloomFilter bloomfilter)
	{
		// RESULT STOKER
		List<IndexedLuceneDocument> documentSet = new ArrayList<IndexedLuceneDocument>();
		
		// CREATE QUERY FROM THE INCOMMING QUERY STRING.
		Query query = null;
				
		try 
		{
			query = this.multiFieldParser.parse(this.formateQuery(queryString));
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		// GETTING DOCUMENTS RESULT
		TopDocs hits = null;
		try 
		{
			hits = this.indexSearcher.search(query, resultSize);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ScoreDoc[] resultSet = hits.scoreDocs;
		
		// SETTING UP RETRIVAL
	    for(int i = 0; i < resultSet.length; i++) 
	    {
	    	IndexedLuceneDocument indexedLuceneDocument = new IndexedLuceneDocument();
	    	
	    	int   articleId    = resultSet[i].doc;
		    	
	    	// METADONNEES DU DOCUMENT
	    	Document document  = null;
	    	try 
	    	{
				document  = this.indexSearcher.doc(articleId);
			} 
	    	catch (IOException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	    	if(! bloomfilter.exists(document.get(IndexedLuceneDocument.ID)))
	    	{
	    		// RECUPEATION SCORE DU DOCUMENT
		    	float articleScore = resultSet[i].score;
		    	
		    	// PREPARATION D UN DOCUMENT INDEXE
		    	indexedLuceneDocument.setId(document.get(IndexedLuceneDocument.ID));
		    	indexedLuceneDocument.setFormat(document.get(IndexedLuceneDocument.FORMAT));
		    	indexedLuceneDocument.setTitle(document.get(IndexedLuceneDocument.TITLE));
		    	String contentpattern = PDFArticleTextExtractor.extractPatternContent(document.get(IndexedLuceneDocument.CONTENT), queryString);
		    	indexedLuceneDocument.setContent(contentpattern);
		    	indexedLuceneDocument.setUrl(document.get(IndexedLuceneDocument.URL));
		    	indexedLuceneDocument.setScore(articleScore);
		    	
		    	documentSet.add( indexedLuceneDocument);
	    	}
	    }

		return documentSet;
	}
	
	/**
	 * formateQuery
	 * @param queryString
	 * @return
	 */
	private String formateQuery(String queryString)
	{
		String newQueryString = new String();
		StringTokenizer st  = new StringTokenizer(queryString);
		while (st.hasMoreTokens())
		{
			newQueryString += st.nextToken()+"~"+" OR .";
		}
		
		return newQueryString;
	}
}
