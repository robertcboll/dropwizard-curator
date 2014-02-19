package com.robertcboll.dropwizard.curator.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robertcboll.dropwizard.curator.ManagedCuratorFactory;

/**
 * The {@link io.dropwizard.Configuration} of an {@link io.dropwizard.Application} using
 * {@link com.robertcboll.dropwizard.curator.CuratorBundle} must implement this interface and provide a
 * {@link com.robertcboll.dropwizard.curator.ManagedCuratorFactory} for creating Curator instances.
 */
public interface CuratorConfiguration {

    @JsonProperty("curator")
    ManagedCuratorFactory getCuratorFactory();
}
