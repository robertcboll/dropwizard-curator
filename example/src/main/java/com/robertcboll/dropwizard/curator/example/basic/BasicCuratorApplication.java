package com.robertcboll.dropwizard.curator.example.basic;

import com.robertcboll.dropwizard.curator.CuratorBundle;
import com.robertcboll.dropwizard.curator.example.SimpleMessageResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 *
 */
public class BasicCuratorApplication extends Application<BasicCuratorConfiguration> {

    @Override
    public void initialize(Bootstrap<BasicCuratorConfiguration> bootstrap) {
        bootstrap.addBundle(new CuratorBundle<>());
    }

    @Override
    public void run(BasicCuratorConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new SimpleMessageResource("hello curator"));
    }
}
