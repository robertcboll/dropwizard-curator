package com.robertcboll.dropwizard.curator.discovery;

import com.google.common.base.Optional;
import com.robertcboll.dropwizard.curator.CuratorBundle;
import com.robertcboll.dropwizard.curator.discovery.config.DiscoveryConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ConfiguredDiscoveryBundle<T extends DiscoverableService> extends CuratorBundle<DiscoveryConfiguration<T>> {

    private final static Logger log = LoggerFactory.getLogger(ConfiguredDiscoveryBundle.class);

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        log.debug("Initializing {}.", ConfiguredDiscoveryBundle.class.getSimpleName());
        super.initialize(bootstrap);
    }

    @Override
    public void run(DiscoveryConfiguration<T> configuration,  Environment environment) throws Exception {
        log.debug("Running {}.", ConfiguredDiscoveryBundle.class.getSimpleName());
        super.run(configuration, environment);

        final Optional<T> optional = configuration.getService();
        if (optional.isPresent())
            advertise(optional.get(), environment);
        else
            log.warn("Not advertising from {} because service not defined in configuration.",
                    ConfiguredDiscoveryBundle.class.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    private void advertise(final T service, final Environment environment) throws Exception {
        final Class<T> type = (Class<T>) service.getClass();
        ManagedServiceDiscovery.advertise(type, service, environment);
    }
}
