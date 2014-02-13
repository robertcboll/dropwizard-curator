package com.robertcboll.dropwizard.curator.discovery;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.curator.x.discovery.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 *
 */
public class ServiceProviders {

    private static final Map<Class, ServiceProvider> map = Maps.newConcurrentMap();

    public static <T extends DiscoverableService> void add(final Class<T> type, final ServiceProvider<T> provider) {
        map.put(type, provider);
    }

    @SuppressWarnings("unchecked")
    public static <T extends DiscoverableService> ServiceProvider<T> forClass(final Class<T> type) throws Exception {
        return Preconditions.checkNotNull(map.get(type), "No provider bound for type %s.", type.getSimpleName());
    }
}
