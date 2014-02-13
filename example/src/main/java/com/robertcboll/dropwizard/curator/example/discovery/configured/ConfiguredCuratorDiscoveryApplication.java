package com.robertcboll.dropwizard.curator.example.discovery.configured;

import com.robertcboll.dropwizard.curator.discovery.ConfiguredDiscoveryBundle;
import com.robertcboll.dropwizard.curator.example.SimpleMessageResource;
import com.robertcboll.dropwizard.curator.example.discovery.service.FooService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 *
 */
public class ConfiguredCuratorDiscoveryApplication extends Application<ConfiguredCuratorDiscoveryConfiguration> {

    @Override
    public void initialize(Bootstrap<ConfiguredCuratorDiscoveryConfiguration> bootstrap) {
        bootstrap.addBundle(new ConfiguredDiscoveryBundle<FooService>());
    }

    @Override
    public void run(ConfiguredCuratorDiscoveryConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new SimpleMessageResource("hello curator discovery"));
    }
}
