package com.robertcboll.dropwizard.curator.app;


import com.robertcboll.dropwizard.curator.CuratorBundle;
import com.robertcboll.dropwizard.curator.foo.FooResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 *
 */
public class CuratorTestApplication extends Application<CuratorTestConfiguration> {

    @Override
    public void initialize(Bootstrap<CuratorTestConfiguration> bootstrap) {
        bootstrap.addBundle(new CuratorBundle<>());
    }

    @Override
    public void run(CuratorTestConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new FooResource());
    }
}
