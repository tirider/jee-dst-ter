package com.container.models.textextractor;

public interface InterfaceArticleTextExtractor 
{
	// METHODE QUI EXECUTE LA EXT*RACTION DU TEXT DEPUIS UN FICHIER TEXT
	public void extract() throws Exception;
	
	// RENDS LE TITRE DU DOCUMENT
	public String getTitle() throws Exception;
	
	// RENDS LE ABSTRACT DU DOCUMENT
	public String getSummary() throws Exception;
	
	// RENDS LE FULL TEXT (CONTENUE) EXTRAIT
	public String getText() throws Exception;
}
