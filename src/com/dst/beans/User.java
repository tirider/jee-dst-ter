package com.dst.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
public class User implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;

	private String email;

	@Temporal(TemporalType.DATE)
	private Date inscriptionDate;

	private String password;

	private String username;

	//bi-directional many-to-one association to Reference
	@OneToMany(mappedBy="user")
	private List<Reference> references;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getInscriptionDate() {
		return this.inscriptionDate;
	}

	public void setInscriptionDate(Date inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Reference> getReferences() {
		return this.references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public Reference addReference(Reference reference) {
		getReferences().add(reference);
		reference.setUser(this);

		return reference;
	}

	public Reference removeReference(Reference reference) {
		getReferences().remove(reference);
		reference.setUser(null);

		return reference;
	}

}