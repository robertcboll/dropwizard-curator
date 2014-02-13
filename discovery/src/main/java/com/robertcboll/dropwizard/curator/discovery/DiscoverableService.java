package com.robertcboll.dropwizard.curator.discovery;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.robertcboll.dropwizard.curator.Curators;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.*;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

import java.util.Collection;

/**
 *
 *
 */
public abstract class DiscoverableService {

    private String name = this.getClass().getName();
    private int port = 8080;
    private int sslPort = -1;
    private String uriSpec = "{scheme}://{address}:{port}";
    private String basePath = "/services";

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public int getSslPort() {
        return this.sslPort;
    }
    public void setSslPort(int sslPort) {
        this.sslPort = sslPort;
    }

    public String getUriSpec() {
        return this.uriSpec;
    }
    public void setUriSpec(String uriSpec) {
        this.uriSpec = uriSpec;
    }

    public String getBasePath() {
        return this.basePath;
    }
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    <T> ServiceInstance<T> instance(final T payload) throws Exception {
        ServiceInstanceBuilder<T> builder = ServiceInstance.<T>builder()
                .name(getName())
                .port(getPort())
                .payload(payload)
                .uriSpec(new UriSpec(getUriSpec()));
        if (getSslPort() > 0)
            builder = builder.sslPort(getSslPort());
        return builder.build();
    }

    <T> ServiceDiscovery<T> discovery(final ServiceInstance<T> instance, final Class<T> type, final CuratorFramework curator) {
        return ServiceDiscoveryBuilder.builder(type)
                .client(curator)
                .basePath(this.getBasePath())
                .thisInstance(instance)
                .build();
    }

    <T> ServiceDiscovery<T> discovery(final Class<T> type, final T payload, final CuratorFramework curator) throws Exception {
        return ServiceDiscoveryBuilder.builder(type)
                .client(curator)
                .basePath(getBasePath())
                .thisInstance(this.instance(payload))
                .build();
    }

    <T> ServiceProvider<T> provider(final Class<T> type, final ProviderStrategy<T> strategy, final CuratorFramework curator) throws Exception {
        return discovery(type, null, curator).serviceProviderBuilder()
                .serviceName(getName())
                .providerStrategy(strategy)
                .build();
    }

    <T> ServiceProvider<T> provider(final Class<T> type, final CuratorFramework curator) throws Exception {
        return discovery(type, null, curator).serviceProviderBuilder()
                .serviceName(getName())
                .providerStrategy(new RandomStrategy<T>())
                .build();
    }

    @VisibleForTesting public <T> Collection<ServiceProvider<T>> provider(final Class<T> type) throws Exception {
        final Collection<ServiceProvider<T>> providers = Lists.newArrayList();
        for (CuratorFramework curator : Curators.all()) {
            providers.add(provider(type, curator));
        }
        return providers;
    }
}
