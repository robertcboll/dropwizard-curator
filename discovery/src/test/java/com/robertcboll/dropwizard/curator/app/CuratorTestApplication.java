package com.robertcboll.dropwizard.curator.app;


import com.robertcboll.dropwizard.curator.CuratorBundle;
import com.robertcboll.dropwizard.curator.app.foo.FooResource;
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

        //bootstrap.addBundle(GuiceBundle.newBuilder().addModule(new CuratorModule(Curators.defaultCurator())).build());
    }

    @Override
    public void run(CuratorTestConfiguration configuration, Environment environment) throws Exception {
        // avoid empty environment errors
        environment.jersey().register(FooResource.class);

        // need to get the curator instance here
        // advertise myself as a service
        //ManagedServiceDiscovery.advertise(FooService.class, service, environment, Curators.named("test"));
        //ServiceProvider<FooService> provider = ManagedServiceProvider.create(FooService.class, service, environment, Curators.named("test"));
        //can do some binding with this if you want!
    }
}
