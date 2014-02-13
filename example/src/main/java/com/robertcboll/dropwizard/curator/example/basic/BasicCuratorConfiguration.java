package com.robertcboll.dropwizard.curator.example.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robertcboll.dropwizard.curator.ManagedCuratorFactory;
import com.robertcboll.dropwizard.curator.config.CuratorConfiguration;
import io.dropwizard.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 *
 */
public class BasicCuratorConfiguration extends Configuration implements CuratorConfiguration {

    @Valid @NotNull
    @JsonProperty
    private ManagedCuratorFactory curator = new ManagedCuratorFactory();

    @Override
    public ManagedCuratorFactory getCuratorFactory() {
        return curator;
    }
}
