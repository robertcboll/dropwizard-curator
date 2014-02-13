package com.robertcboll.dropwizard.curator.health;

import com.codahale.metrics.health.HealthCheck;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;

/**
 *
 *
 */
public class CuratorHealthCheck extends HealthCheck {

    private final static String MESSAGE_STATE = "client state is %s";
    private final static String MESSAGE_CLIENT_NULL = "client is null";

    private final CuratorFramework curator;

    public CuratorHealthCheck(CuratorFramework curator) {
        super();
        this.curator = curator;
    }

    @Override
    protected Result check() throws Exception {
        if (curator == null)
            return Result.unhealthy(MESSAGE_CLIENT_NULL);

        CuratorFrameworkState state = curator.getState();
        if (state != CuratorFrameworkState.STARTED)
            return Result.unhealthy(String.format(MESSAGE_STATE, state));
        else
            return Result.healthy(MESSAGE_STATE, state);
    }
}
