package com.robertcboll.dropwizard.curator.discovery.config;

import com.google.common.base.Optional;
import com.robertcboll.dropwizard.curator.config.CuratorConfiguration;
import com.robertcboll.dropwizard.curator.discovery.DiscoverableService;

/**
 *
 *
 */
public interface DiscoveryConfiguration<T extends DiscoverableService> extends CuratorConfiguration {

    Optional<T> getService();
}
