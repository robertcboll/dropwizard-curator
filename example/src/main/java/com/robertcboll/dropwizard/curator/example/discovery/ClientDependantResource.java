package com.robertcboll.dropwizard.curator.example.discovery;

import com.robertcboll.dropwizard.curator.example.discovery.service.FooClient;
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
@Path("/client")
public class ClientDependantResource {

    private final static Logger log = LoggerFactory.getLogger(ClientDependantResource.class);

    private final FooClient client;

    public ClientDependantResource(final FooClient client) {
        this.client = client;
        log.debug("Set client to {}.", client);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() throws Exception {
        return Response.ok(client.get()).build();
    }
}
