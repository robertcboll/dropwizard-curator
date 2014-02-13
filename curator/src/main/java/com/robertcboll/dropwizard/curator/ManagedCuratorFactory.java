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
 *
 *
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

    public void addCurator(final CuratorInstance instance) {
        curators.add(instance);
    }
}
