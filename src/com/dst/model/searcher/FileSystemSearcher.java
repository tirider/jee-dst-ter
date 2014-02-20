package com.dst.model.searcher;

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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.dst.model.filter.BloomFilter;
import com.dst.model.javabeans.IndexedLuceneDocument;
import com.dst.model.text.PDFTextExtractor;

/**  **/
public class FileSystemSearcher 
{
	private Directory indexDirectory		= null;
	private DirectoryReader indexReader		= null;
	private IndexSearcher indexSearcher 	= null;
	private Analyzer analyzer				= null;
	private MultiFieldQueryParser multiFieldParser = null;
	
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
		
		try
		{
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
		catch(Exception e)
		{
			System.err.println("Error trying to read the index directoty, it could be empty...");
		}
	}

	/**
	 * search
	 * @param queryString
	 * @param resultSize
	 * @param bloomfilter
	 * @return
	 */
	public List<IndexedLuceneDocument> search(String queryString, int resultSize, BloomFilter bloomfilter)
	{
		// RESULT STOKER
		List<IndexedLuceneDocument> documentSet = new ArrayList<IndexedLuceneDocument>();
		
		try 
		{
			// CREATE QUERY FROM THE INCOMMING QUERY STRING.
			Query query = this.multiFieldParser.parse(this.formateQuery(queryString));
			
			// GETTING DOCUMENTS RESULT
			TopDocs hits = this.indexSearcher.search(query, resultSize);
			
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
			    	String contentpattern = PDFTextExtractor.extractPatternContent(document.get(IndexedLuceneDocument.CONTENT), queryString);
			    	indexedLuceneDocument.setContent(contentpattern);
			    	indexedLuceneDocument.setUrl(document.get(IndexedLuceneDocument.URL));
			    	indexedLuceneDocument.setScore(articleScore);
			    	
			    	documentSet.add( indexedLuceneDocument);
		    	}
		    }	
		}
		catch(Exception e)
		{
			System.err.println("Error tryng to search into the index directory...");
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
