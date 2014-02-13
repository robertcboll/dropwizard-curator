package com.robertcboll.dropwizard.curator.example.discovery.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A simple resource returning a foo instance.
 */
@Path("/foo")
public class FooResource {

    private final Foo response;

    public FooResource(final Foo response) {
        this.response = response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }
}
