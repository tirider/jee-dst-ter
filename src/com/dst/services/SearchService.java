package com.dst.services;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/searchservice")
public class SearchService 
{
    @Context
	ServletContext context;

    /**
     * Retrieves representation of an instance of SearchService
     * @return an instance of String
     */
    @GET
    @Path("/searchaction")
	@Produces(MediaType.APPLICATION_JSON)    
    public String search() {
        return "";
    }
}