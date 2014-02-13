package com.robertcboll.dropwizard.curator.app;

import com.google.common.base.Optional;
import com.robertcboll.dropwizard.curator.ManagedCuratorFactory;
import com.robertcboll.dropwizard.curator.config.CuratorConfiguration;
import com.robertcboll.dropwizard.curator.discovery.config.DiscoveryConfiguration;
import com.robertcboll.dropwizard.curator.foo.FooService;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 *
 */
public class CuratorTestConfiguration extends Configuration implements CuratorConfiguration, DiscoveryConfiguration<FooService> {

    @Valid
    @NotNull
    private ManagedCuratorFactory curator = new ManagedCuratorFactory();

    @Valid
    @NotNull
    private FooService service = new FooService();

    public ManagedCuratorFactory getCuratorFactory() {
        return curator;
    }

    public void setCuratorFactory(ManagedCuratorFactory curator) {
        this.curator = curator;
    }

    public Optional<FooService> getService() {
        return Optional.of(service);
    }

    public void setService(FooService service) {
        this.service = service;
    }
}
