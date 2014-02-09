package com.dst.beans;

import java.util.Date;

public class User 
{
	private String id;
	private String username;
	private String password;
	private String sexe;
	private String email;
	private String groupid;
	private Date inscriptiondate;
	
	public User()
	{
		this.id = new String();
		this.username = new String();
		this.password = new String();
		this.sexe = new String();
		this.email = new String();
		this.groupid = new String();
		this.inscriptiondate = new Date();
	}
	
	public String getId() { 	return id; }
	public void setId(String id) { this.id = id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public String getPassword() { return password; }
	public void setPassword(String userpassword) { this.password = userpassword; }
	
	public String getSexe() { return sexe; }
	public void setSexe(String sexe) { this.sexe = sexe; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getGroupid() { return groupid; }
	public void setGroupid(String groupid) { this.groupid = groupid; }

	public Date getInscriptiondate() { return inscriptiondate; }
	public void setInscriptiondate(Date inscriptiondate) { this.inscriptiondate = inscriptiondate; }
}