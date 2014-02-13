package com.robertcboll.dropwizard.curator.example;

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
@Path("/")
public class SimpleMessageResource {

    private final static Logger log = LoggerFactory.getLogger(SimpleMessageResource.class);

    private final String message;

    public SimpleMessageResource(final String message) {
        this.message = message;
        log.debug("Set message to {}.", message);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(message).build();
    }
}
