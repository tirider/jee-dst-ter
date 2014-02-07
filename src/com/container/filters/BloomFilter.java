package com.container.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Blum filter class
 * @author tirider
 */
public class BloomFilter 
{
	/**
	 * Blom filter set holder.
	 */
	private List<String> filter = new ArrayList<String>();

	/**
	 * Constructor with param.
	 * @param filter
	 */
	public BloomFilter(List<String> filter) 
	{
		super();
		this.filter.addAll(filter);
	}

	// ACCESORS
	public List<String> getFilter() { return filter; }
	public void addToFilter(String docId) { this.filter.add(docId); }
	public void deleteFromFilter(String docId) { this.filter.remove(docId); }
	
	/**
	 * Checks for existence.
	 * @param docId
	 * @return
	 */
	public boolean exists(String docId) { return this.filter.contains(docId); }
}
