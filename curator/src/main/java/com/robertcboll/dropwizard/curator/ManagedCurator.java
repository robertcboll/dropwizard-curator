package com.robertcboll.dropwizard.curator;

import io.dropwizard.lifecycle.Managed;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class ManagedCurator implements Managed {

    private final static Logger log = LoggerFactory.getLogger(ManagedCurator.class);

    private final CuratorFramework curator;
    private final String name;

    static ManagedCurator build(final String name, final CuratorFramework curator) {
        return new ManagedCurator(name, curator);
    }

    private ManagedCurator(final String name, final CuratorFramework curator) {
        this.name = name;
        this.curator = curator;
    }

    public CuratorFramework curator() {
        return this.curator;
    }

    public String name() {
        return this.name;
    }

    @Override
    public void start() throws Exception {
        log.debug("Starting curator connection.");
        this.curator().start();
    }

    @Override
    public void stop() throws Exception {
        log.debug("Closing curator connection.");
        this.curator().close();
    }
}
