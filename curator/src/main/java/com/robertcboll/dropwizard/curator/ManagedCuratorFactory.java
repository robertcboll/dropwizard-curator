package com.robertcboll.dropwizard.curator;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.robertcboll.dropwizard.curator.health.CuratorHealthCheck;
import io.dropwizard.setup.Environment;
import org.apache.curator.framework.CuratorFramework;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Factory provided by {@link com.robertcboll.dropwizard.curator.config.CuratorConfiguration} for applications who
 * use {@link com.robertcboll.dropwizard.curator.CuratorBundle}. Builds
 * {@link org.apache.curator.framework.CuratorFramework} instances, manages them, and registers them with health checks
 * and with the {@link com.robertcboll.dropwizard.curator.Curators} registry.
 */
public class ManagedCuratorFactory {
    
    private Collection<CuratorInstance> curators;

    public Collection<ManagedCurator> build(final Environment environment) {
        List<ManagedCurator> managedCurators = Lists.newArrayList();

        for (final CuratorInstance instance : curators) {
            ManagedCurator managed = instance.build();
            managedCurators.add(managed);

            environment.lifecycle().manage(managed);
            environment.healthChecks().register("curator-connection-" + instance.getName(), new CuratorHealthCheck(managed.curator()));
        }
        Curators.bootstrap(managedCurators);
        return managedCurators;
    }

    public void setCurators(final List<CuratorInstance> curators) {
        this.curators = curators;
    }

    /**
     * Allows adding programmatically built {@link com.robertcboll.dropwizard.curator.CuratorInstance} instances for in
     * depth configuration.
     * @param instance the instance to manage
     */
    public void addCurator(final CuratorInstance instance) {
        curators.add(instance);
    }
}
