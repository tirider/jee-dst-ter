package com.dst.services;

import javax.ws.rs.ApplicationPath;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/")
public class JerseyContainer extends ResourceConfig 
{
    public JerseyContainer()
    {
    	/**
    	 * Packages to scan automatically.
    	 */
    	packages("com.dst.services");
    	
    	/**
    	 * Servlet registration.
    	 */
    	register(UploadFileService.class);
    	register(IndexationService.class);
        register(org.glassfish.jersey.server.filter.UriConnegFilter.class);
        register(org.glassfish.jersey.server.validation.ValidationFeature.class);
        register(org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainerProvider.class);
        register(org.glassfish.jersey.server.mvc.MvcFeature.class);
        register(MoxyJsonFeature.class);
        register(MOXyJsonProvider.class);
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        
        /**
         * The mechanism of feature auto-discovery in Jersey that described above is 
         * enabled by default. It can be disabled by using special (common/server/client) properties: 
         */
        property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
        property(MarshallerProperties.JSON_NAMESPACE_SEPARATOR, ".");
    }
}