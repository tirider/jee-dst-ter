package com.dst.services;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/indexationservice")
public class IndexationService 
{
    @Context
	ServletContext context;

    /**
     * Retrieves representation of an instance of IndexationService
     * @return an JSON Object
     */
    @GET
    @Path("/indexaction")
	@Produces(MediaType.APPLICATION_JSON)
    public String index() 
    {
        return "";
    }
}