package com.robertcboll.dropwizard.curator.app;

import com.robertcboll.dropwizard.curator.ManagedCuratorFactory;
import com.robertcboll.dropwizard.curator.config.CuratorConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 *
 */
public class CuratorTestConfiguration extends Configuration implements CuratorConfiguration {

    @Valid
    @NotNull
    private ManagedCuratorFactory curator = new ManagedCuratorFactory();


    public ManagedCuratorFactory getCuratorFactory() {
        return curator;
    }

    public void setCuratorFactory(ManagedCuratorFactory curator) {
        this.curator = curator;
    }

}
