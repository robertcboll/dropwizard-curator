package com.robertcboll.dropwizard.curator.example.discovery.simple;

import com.google.common.collect.ImmutableList;
import com.robertcboll.dropwizard.curator.Curators;
import com.robertcboll.dropwizard.curator.discovery.ManagedServiceProvider;
import com.robertcboll.dropwizard.curator.discovery.SimpleDiscoveryBundle;
import com.robertcboll.dropwizard.curator.example.SimpleMessageResource;
import com.robertcboll.dropwizard.curator.example.basic.BasicCuratorConfiguration;
import com.robertcboll.dropwizard.curator.example.discovery.ClientDependantResource;
import com.robertcboll.dropwizard.curator.example.discovery.ServiceDependantResource;
import com.robertcboll.dropwizard.curator.example.discovery.service.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.curator.x.discovery.ServiceProvider;

/**
 *
 *
 */
public class SimpleCuratorDiscoveryApplication extends Application<BasicCuratorConfiguration> {

    @Override
    public void initialize(Bootstrap<BasicCuratorConfiguration> bootstrap) {
        bootstrap.addBundle(new SimpleDiscoveryBundle(ImmutableList.of(new FooService(), new BarService())));
    }

    @Override
    public void run(BasicCuratorConfiguration configuration, Environment environment) throws Exception {
        // bar provider and dependant resource
        ServiceProvider<BarService> barProvider = ManagedServiceProvider.create(BarService.class, new BarService(), environment, Curators.named("default"));
        environment.jersey().register(new ServiceDependantResource<>(barProvider));

        // foo provider, foo service and dependant resource via client
        ServiceProvider<FooService> fooProvider = ManagedServiceProvider.create(FooService.class, new FooService(), environment, Curators.named("default"));
        environment.jersey().register(new FooResource(new Foo("simple-foo", "a simple foo to test the foo client.")));
        environment.jersey().register(new ClientDependantResource(new FooClient(fooProvider)));

        environment.jersey().register(new SimpleMessageResource("hello curator discovery"));
    }
}
