package com.dst.services;
   
import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes; 
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context; 
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dst.fileupload.PDFFileUpload;

@Path("/uploadservice")
public class UploadFileService 
{
	@Context 
	ServletContext context;
	
    /**
     * Retrieves representation of an instance of UploadFileService
     * @return an JSON Object
     */
	@POST
	@Path("/uploadaction") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public PDFFileUpload upload(@Context HttpServletRequest request)
	{
		final String TEMPS  = context.getInitParameter("temps-directory");
		PDFFileUpload pdf 	= new PDFFileUpload(TEMPS);
		
		try 
        { 
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 1024 * 3);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(1024 * 1024 * 40);
			upload.setSizeMax(1024 * 1024 * 50);
			
			List<FileItem> itemsSet = upload.parseRequest(request);
			
			for(FileItem item : itemsSet)
				if(!item.isFormField())
					pdf.upload(item); 
        }
		catch(FileUploadException e)
		{
			pdf.setErrors("size", "The selected file exceeds the size allowed by the server");
			System.err.println("Error when parsing the request...");
		} 
		catch (Exception e) { e.printStackTrace(); }
		
    	if(pdf.getErrors().isEmpty())
    		return pdf;
    	return new PDFFileUpload();
	}
}