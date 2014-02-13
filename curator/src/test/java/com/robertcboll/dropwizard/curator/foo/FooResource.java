package com.robertcboll.dropwizard.curator.foo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A dummy foo class to register during testing to avoid exceptions.
 */
@Path("/")
public class FooResource {

    public final static Foo RESPONSE = new Foo("testing-foo", "a foo used for testing");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        return Response.ok(RESPONSE).type(MediaType.APPLICATION_JSON).build();
    }
}
