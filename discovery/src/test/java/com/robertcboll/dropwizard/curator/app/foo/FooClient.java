package com.robertcboll.dropwizard.curator.app.foo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 *
 *
 */
public class FooClient {

    private final static Logger log = LoggerFactory.getLogger(FooClient.class);
    private final ServiceProvider<FooService> provider;

    public FooClient(ServiceProvider<FooService> provider) {
        this.provider = provider;
    }

    public Foo get() throws Exception {
        ServiceInstance<FooService> instance = provider.getInstance();

        String connectionString = instance.buildUriSpec();
        WebResource fooResource = Client.create().resource(connectionString);

        return fooResource
                .accept(MediaType.APPLICATION_JSON)
                .get(Foo.class);
    }
}
