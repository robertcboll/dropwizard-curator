package com.robertcboll.dropwizard.curator;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.ensemble.exhibitor.DefaultExhibitorRestClient;
import org.apache.curator.ensemble.exhibitor.ExhibitorEnsembleProvider;
import org.apache.curator.ensemble.exhibitor.Exhibitors;
import org.apache.curator.ensemble.fixed.FixedEnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

/**
 * A wrapper for {@link org.apache.curator.framework.CuratorFramework} instances created and configured by
 * {@link com.robertcboll.dropwizard.curator.ManagedCuratorFactory}.
 */
public class CuratorInstance {

    private final static String EXHIBITOR_REST_ENDPOINT = "/exhibitor/v1/cluster/list";

    /* basic config */
    @NotEmpty
    private String name;
    @NotEmpty
    private String connectionString;
    private String namespace;
    private boolean isDefault = false;

    /* retry policy config */
    private int baseSleepTime = 1000;
    private int maxRetries = 3;
    private RetryPolicy retryPolicy;

    /* exhibitor config */
    @Min(1) @Max(65535)
    private int exhibitorPort;
    private int exhibitorPollingDelay;
    private Collection<String> exhibitors = Lists.newArrayList();

    private final Optional<CuratorFramework> curator;

    public CuratorInstance() {
        curator = Optional.absent();
    }

    public CuratorInstance(String name, CuratorFramework curator) {
        this.name = name;
        this.curator = Optional.of(curator);
    }

    public String getConnectionString() {
        return connectionString;
    }
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getName() {
        return name;
    }
    public void setName(String curatorName) {
        this.name = curatorName;
    }

    public String getNamespace() {
        return this.namespace;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public boolean isDefault() {
        return isDefault;
    }
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getBaseSleepTime() {
        return baseSleepTime;
    }
    public void setBaseSleepTime(int baseSleepTime) {
        this.baseSleepTime = baseSleepTime;
    }

    public int getMaxRetries() {
        return maxRetries;
    }
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public RetryPolicy getRetryPolicy() {
        if (retryPolicy != null)
            return retryPolicy;
        else return new ExponentialBackoffRetry(1000, 3);
    }
    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public int getExhibitorPort() {
        return exhibitorPort;
    }
    public void setExhibitorPort(int exhibitorPort) {
        this.exhibitorPort = exhibitorPort;
    }

    public int getExhibitorPollingDelay() {
        return exhibitorPollingDelay;
    }
    public void setExhibitorPollingDelay(int exhibitorPollingDelay) {
        this.exhibitorPollingDelay = exhibitorPollingDelay;
    }

    public Collection<String> getExhibitors() {
        return exhibitors;
    }
    public void setExhibitors(Collection<String> exhibitors) {
        this.exhibitors = exhibitors;
    }

    /**
     * Builds an instance of {@link com.robertcboll.dropwizard.curator.ManagedCurator} backed by a
     * {@link org.apache.curator.framework.CuratorFramework} based on the configuration, or the instance passed to the
     * constructor. Part of this process involves registering health checks and registering with
     * {@link com.robertcboll.dropwizard.curator.Curators}.
     * @return an instance of {@link com.robertcboll.dropwizard.curator.ManagedCurator} backed by this instance's
     * {@link org.apache.curator.framework.CuratorFramework}
     */
    public ManagedCurator build() {
        final CuratorFramework instance = curator.or(buildCurator());
        return ManagedCurator.build(name, instance);
    }

    private CuratorFramework buildCurator() {
        final EnsembleProvider ensembleProvider = getEnsembleProvider()
                .or(new FixedEnsembleProvider(getConnectionString()));

        return CuratorFrameworkFactory.builder()
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(getNamespace())
                .ensembleProvider(ensembleProvider)
                .build();
    }

    private Optional<EnsembleProvider> getEnsembleProvider() {
        if (getExhibitors().isEmpty()) return Optional.absent();

        Exhibitors exhibitors = new Exhibitors(getExhibitors(), getExhibitorPort(), new Exhibitors.BackupConnectionStringProvider() {

            @Override
            public String getBackupConnectionString() throws Exception {
                return getConnectionString();
            }
        });
        final EnsembleProvider provider = new ExhibitorEnsembleProvider(exhibitors, new DefaultExhibitorRestClient(),
                EXHIBITOR_REST_ENDPOINT, getExhibitorPollingDelay(), getRetryPolicy());
        return Optional.of(provider);
    }
}
