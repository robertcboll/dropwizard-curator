package com.robertcboll.dropwizard.curator.discovery.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.base.Preconditions;
import com.robertcboll.dropwizard.curator.discovery.DiscoverableService;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class ProviderHealthCheck<T extends DiscoverableService> extends HealthCheck {

    private static final String MESSAGE_NULL = "provided instance was null";
    private static final String MESSAGE_HEALTHY = "provider is healthy";

    private final ServiceProvider<T> provider;

    public ProviderHealthCheck(final ServiceProvider<T> provider) {
        super();
        this.provider = provider;
    }

    @Override
    protected Result check() throws Exception {
        try {
            ServiceInstance<T> instance = provider.getInstance();
            Preconditions.checkNotNull(instance, MESSAGE_NULL);
            return Result.healthy(MESSAGE_HEALTHY);
        } catch (Exception e) {
            return Result.unhealthy(e.getMessage());
        }

    }
}
