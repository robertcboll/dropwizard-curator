package com.robertcboll.dropwizard.curator;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Curators {

    private static Curators INSTANCE;

    private final Map<String, CuratorFramework> curators;

    private Curators(final Collection<ManagedCurator> managedCurators) {
        final Map<String, CuratorFramework> local = Maps.newHashMap();
        for (final ManagedCurator curator : managedCurators) {
            local.put(curator.name(), curator.curator());
        }
        this.curators = ImmutableMap.copyOf(local);
    }

    public static void bootstrap(final Collection<ManagedCurator> curators) {
        INSTANCE = new Curators(curators);
    }

    public static CuratorFramework named(final String name) {
        Preconditions.checkNotNull(INSTANCE, "Curators has not been bootstrapped.");
        return Preconditions.checkNotNull(INSTANCE.curators.get(name), "No curator bound for name %s.", name);
    }

    public static Map<String, CuratorFramework> allWithNames() {
        Preconditions.checkNotNull(INSTANCE, "Curators has not been bootstrapped.");
        return INSTANCE.curators;
    }

    public static Collection<CuratorFramework> all() {
        Preconditions.checkNotNull(INSTANCE, "Curators has not been bootstrapped.");
        return INSTANCE.curators.values();
    }

    public static Set<String> available() {
        Preconditions.checkNotNull(INSTANCE, "Curators has not been bootstrapped.");
        return INSTANCE.curators.keySet();
    }
}
