package com.dst.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the article database table.
 * 
 */
@Entity
@Table(name="article")
public class Article implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.DATE)
	private Date creationDate;

	private String format;

	private String name;

	private int numberOfPages;

	private int size;

	//bi-directional many-to-one association to Reference
	@OneToMany(mappedBy="article")
	private List<Reference> references;

	public Article() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfPages() {
		return this.numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Reference> getReferences() {
		return this.references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public Reference addReference(Reference reference) {
		getReferences().add(reference);
		reference.setArticle(this);

		return reference;
	}

	public Reference removeReference(Reference reference) {
		getReferences().remove(reference);
		reference.setArticle(null);

		return reference;
	}

}