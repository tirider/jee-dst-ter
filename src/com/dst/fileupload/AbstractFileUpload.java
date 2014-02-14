package com.dst.fileupload;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public abstract class AbstractFileUpload 
{
	protected String uploadsdirectory;
	protected String filecode;	
	protected String filename;
	protected String filetempname;
	protected String fileformat;
	protected int   filenumberofpages;		
	protected int   filesize;	
	protected String message;
	protected boolean unsigned;
	private Map<String, String> errors = new HashMap<String, String>();

	// CONSTRUCTEUR
	public AbstractFileUpload(){}
	
	public AbstractFileUpload(String uploaddirectory) 
	{
		this.uploadsdirectory = uploaddirectory;
		this.filecode = new String();
		this.filename = new String();
		this.filetempname = new String();
		this.fileformat = new String();
		this.filenumberofpages = 0;		
		this.filesize = 0;
		this.message = new String();
		this.unsigned = false;
	}

	public abstract void upload(FileItem fileItem) throws Exception;

	public String getUploadsdirectory() { return uploadsdirectory; 	}
	public void setUploadsdirectory(String uploadsdirectory) { this.uploadsdirectory = uploadsdirectory; }	

	public String getFilecode() { return filecode; }
	public void setFilecode(String filecode) { this.filecode = filecode; }

	public String getFilename() {	return filename; }
	public void setFilename(String filename) { this.filename = filename; }

	public String getFiletempname() {
		return filetempname;
	}

	public void setFiletempname(String filetempname) {
		this.filetempname = filetempname;
	}

	public String getFileformat() {
		return fileformat;
	}

	public void setFileformat(String fileformat) {
		this.fileformat = fileformat;
	}

	public int getFilenumberofpages() {
		return filenumberofpages;
	}

	public void setFilenumberofpages(int filenumberofpages) {
		this.filenumberofpages = filenumberofpages;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isUnsigned() {
		return unsigned;
	}

	public void setUnsigned(boolean unsigned) {
		this.unsigned = unsigned;
	}
	
	public Map<String, String> getErrors() { return errors; }

	public void setErrors(String id, String message) { this.errors.put(id, message); }	
}
