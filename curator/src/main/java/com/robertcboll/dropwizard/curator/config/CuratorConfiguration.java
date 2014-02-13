package com.robertcboll.dropwizard.curator.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robertcboll.dropwizard.curator.ManagedCuratorFactory;

/**
 *
 *
 */
public interface CuratorConfiguration {

    @JsonProperty("curator")
    ManagedCuratorFactory getCuratorFactory();
}
