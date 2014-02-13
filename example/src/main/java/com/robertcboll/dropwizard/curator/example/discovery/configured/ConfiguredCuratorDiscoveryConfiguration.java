package com.robertcboll.dropwizard.curator.example.discovery.configured;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.robertcboll.dropwizard.curator.discovery.config.DiscoveryConfiguration;
import com.robertcboll.dropwizard.curator.example.basic.BasicCuratorConfiguration;
import com.robertcboll.dropwizard.curator.example.discovery.service.FooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class ConfiguredCuratorDiscoveryConfiguration extends BasicCuratorConfiguration implements DiscoveryConfiguration<FooService> {

    @JsonProperty
    private FooService service;

    @Override
    public Optional<FooService> getService() {
        return Optional.fromNullable(service);
    }
}
