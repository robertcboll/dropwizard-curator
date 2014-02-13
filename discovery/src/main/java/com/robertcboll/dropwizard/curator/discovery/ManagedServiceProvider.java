package com.robertcboll.dropwizard.curator.discovery;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.robertcboll.dropwizard.curator.Curators;
import com.robertcboll.dropwizard.curator.discovery.health.AdvertisedHealthCheck;
import com.robertcboll.dropwizard.curator.discovery.health.ProviderHealthCheck;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public class ManagedServiceProvider<T extends DiscoverableService> implements Managed {

    private final static Logger log = LoggerFactory.getLogger(ManagedServiceProvider.class);

    private final ServiceProvider<T> provider;
    private final Class<T> type;

    private ManagedServiceProvider(final ServiceProvider<T> provider, final Class<T> type) {
        this.provider = provider;
        this.type = type;
    }

    public static <T extends DiscoverableService> ServiceProvider<T> create(final Class<T> type, final T service,
                                                                            final Environment environment,
                                                                            final CuratorFramework curator) throws Exception {
        ServiceProvider<T> provider = service.provider(type, curator);
        environment.lifecycle().manage(new ManagedServiceProvider<>(provider, type));
        environment.healthChecks().register("curator-provider-" + type.getSimpleName(),
                new ProviderHealthCheck<>(provider));
        return provider;
    }

    public static <T extends DiscoverableService> Collection<ServiceProvider<T>> create(final Class<T> type, final T service,
                                                                            final Environment environment) throws Exception {
        final List<ServiceProvider<T>> providers = Lists.newArrayList();
        for (final CuratorFramework curator : Curators.all()) {
            providers.add(create(type, service, environment, curator));
        }
        return providers;
    }

    @Override
    public void start() throws Exception {
        try {
            log.debug("Starting provider with type {}.", type.getSimpleName());
            provider.start();
            ServiceProviders.add(type, provider);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void stop() throws Exception {
        log.debug("Stopping provider with type {}.", type.getSimpleName());
        provider.close();
    }
}
