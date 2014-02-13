package com.robertcboll.dropwizard.curator.example.discovery.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;

import javax.ws.rs.core.MediaType;

/**
 *
 *
 */
public class FooClient {

    private final ServiceProvider<FooService> provider;

    public FooClient(ServiceProvider<FooService> provider) {
        this.provider = provider;
    }

    public Foo get() throws Exception {
        ServiceInstance<FooService> instance = provider.getInstance();

        String connectionString = instance.buildUriSpec() + "/foo";
        WebResource fooResource = Client.create().resource(connectionString);

        return fooResource
                .accept(MediaType.APPLICATION_JSON)
                .get(Foo.class);
    }
}
