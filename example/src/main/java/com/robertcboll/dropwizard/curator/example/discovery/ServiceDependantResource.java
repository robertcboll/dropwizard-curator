package com.robertcboll.dropwizard.curator.example.discovery;

import org.apache.curator.x.discovery.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 *
 */
@Path("/provider")
public class ServiceDependantResource<T> {

    private final static Logger log = LoggerFactory.getLogger(ServiceDependantResource.class);

    private final ServiceProvider<T> provider;

    public ServiceDependantResource(ServiceProvider<T> provider) {
        this.provider = provider;
        log.debug("Set provider to {}.", provider);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(provider).build();
    }
}
