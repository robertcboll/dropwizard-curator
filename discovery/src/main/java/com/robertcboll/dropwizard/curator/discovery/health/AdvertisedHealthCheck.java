package com.robertcboll.dropwizard.curator.discovery.health;

import com.codahale.metrics.health.HealthCheck;
import com.robertcboll.dropwizard.curator.discovery.DiscoverableService;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class AdvertisedHealthCheck<T extends DiscoverableService> extends HealthCheck {

    private final static String MESSAGE_HEALTHY = "service is advertised";
    private final static String MESSAGE_UNHEALTHY = "service is not advertised";

    private final ServiceDiscovery<T> discovery;
    private final ServiceInstance<T> instance;

    public AdvertisedHealthCheck(ServiceInstance<T> instance, ServiceDiscovery<T> discovery) {
        super();
        this.discovery = discovery;
        this.instance = instance;
    }

    @Override
    protected Result check() throws Exception {
        ServiceInstance<T> queried = discovery.queryForInstance(instance.getName(), instance.getId());
        if (queried != null)
            return Result.healthy(MESSAGE_HEALTHY);
        else
            return Result.unhealthy(MESSAGE_UNHEALTHY);
    }
}
