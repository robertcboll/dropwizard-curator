package com.robertcboll.dropwizard.curator.discovery;

import com.google.common.collect.ImmutableList;
import com.robertcboll.dropwizard.curator.CuratorBundle;
import com.robertcboll.dropwizard.curator.config.CuratorConfiguration;
import io.dropwizard.jackson.Discoverable;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 *
 *
 */
public class SimpleDiscoveryBundle extends CuratorBundle<CuratorConfiguration> {

    private final static Logger log = LoggerFactory.getLogger(SimpleDiscoveryBundle.class);

    private final Collection<DiscoverableService> services;

    public SimpleDiscoveryBundle(final Collection<DiscoverableService> services) {
        this.services = ImmutableList.copyOf(services);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        log.debug("Initializing {}.", SimpleDiscoveryBundle.class.getSimpleName());
        super.initialize(bootstrap);
    }

    @Override
    public void run(CuratorConfiguration configuration,  Environment environment) throws Exception {
        log.debug("Running {}.", SimpleDiscoveryBundle.class.getSimpleName());
        super.run(configuration, environment);

        for (final DiscoverableService service : services) {
            advertise(service, environment);
        }
    }

    @SuppressWarnings("unchecked")
    private void advertise(final DiscoverableService service, final Environment environment) throws Exception {
        final Class type = service.getClass();
        ManagedServiceDiscovery.advertise(type, service, environment);
    }
}
