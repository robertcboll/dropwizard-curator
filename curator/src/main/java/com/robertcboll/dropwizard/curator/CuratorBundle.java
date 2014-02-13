package com.robertcboll.dropwizard.curator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.robertcboll.dropwizard.curator.config.CuratorConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 *
 */
public class CuratorBundle<T extends CuratorConfiguration> implements ConfiguredBundle<T> {

    private final static Logger log = LoggerFactory.getLogger(CuratorBundle.class);

    private final Collection<CuratorInstance> extras;

    public CuratorBundle() {
        this.extras = ImmutableList.of();
    }

    public CuratorBundle(final CuratorInstance... extras) {
        this.extras = ImmutableList.copyOf(extras);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        log.debug("Initializing {}.", CuratorBundle.class.getSimpleName());
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        log.debug("Running {}.", CuratorBundle.class.getSimpleName());

        final ManagedCuratorFactory factory = configuration.getCuratorFactory();
        for (CuratorInstance extra : extras) {
            factory.addCurator(extra);
        }
        final Collection<ManagedCurator> curators = factory.build(environment);
    }
}
