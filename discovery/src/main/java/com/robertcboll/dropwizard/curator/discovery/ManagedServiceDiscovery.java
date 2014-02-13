package com.robertcboll.dropwizard.curator.discovery;

import com.google.common.collect.Lists;
import com.robertcboll.dropwizard.curator.Curators;
import com.robertcboll.dropwizard.curator.discovery.health.AdvertisedHealthCheck;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ManagedServiceDiscovery<T extends DiscoverableService> implements Managed {

    private final static Logger log = LoggerFactory.getLogger(ManagedServiceDiscovery.class);

    private final ServiceDiscovery<T> discovery;

    private ManagedServiceDiscovery(final ServiceDiscovery<T> discovery) {
        this.discovery = discovery;
    }

    public static <T extends DiscoverableService> ServiceDiscovery<T> advertise(final Class<T> type, final T service,
                                                                                final Environment environment,
                                                                                final String curatorName,
                                                                                final CuratorFramework curator) throws Exception {
        ServiceInstance<T> instance = service.instance(service);
        ServiceDiscovery<T> discovery = service.discovery(instance, type, curator);

        final String name = String.format("curator-%s-advertise-%s", curatorName, type.getSimpleName());

        environment.lifecycle().manage(new ManagedServiceDiscovery<>(discovery));
        environment.healthChecks().register(name, new AdvertisedHealthCheck<>(instance, discovery));
        return discovery;
    }

    public static <T extends DiscoverableService> Collection<ServiceDiscovery<T>> advertise(final Class<T> type,
                                                                                            final T service,
                                                                                            final Environment environment) throws Exception {
        final List<ServiceDiscovery<T>> discoveries = Lists.newArrayList();
        final Map<String, CuratorFramework> all = Curators.allWithNames();
        for (final String name : all.keySet()) {
            discoveries.add(advertise(type, service, environment, name, all.get(name)));
        }
        return discoveries;
    }

    @Override
    public void start() throws Exception {
        log.debug("Starting discovery.");
        discovery.start();
    }

    @Override
    public void stop() throws Exception {
        log.debug("Stopping discovery.");
        discovery.close();
    }
}
