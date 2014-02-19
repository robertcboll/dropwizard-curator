package com.robertcboll.dropwizard.curator.health;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests curator health check for proper response based on state of curator.
 */
public class CuratorHealthCheckTest {

    private final static HealthCheckRegistry REGISTRY = new HealthCheckRegistry();

    @Test
    public void testWithLatent() {
        HealthCheck.Result hcResult = resultForState(CuratorFrameworkState.LATENT);
        assertFalse("healthcheck is failing", hcResult.isHealthy());
        assertEquals("client state is LATENT", hcResult.getMessage());
    }

    @Test
    public void testWithStopped() {
        HealthCheck.Result hcResult = resultForState(CuratorFrameworkState.STOPPED);
        assertFalse("healthcheck is failing", hcResult.isHealthy());
        assertEquals("client state is STOPPED", hcResult.getMessage());
    }

    @Test
    public void testWithStarted() {
        HealthCheck.Result hcResult = resultForState(CuratorFrameworkState.STARTED);
        assertTrue("healthcheck is passing", hcResult.isHealthy());
        assertEquals("client state is STARTED", hcResult.getMessage());
    }

    @Test
    public void testWithNull() {
        CuratorHealthCheck hc = new CuratorHealthCheck(null);
        REGISTRY.register("testWithNull", hc);
        HealthCheck.Result hcResult = REGISTRY.runHealthCheck("testWithNull");
        assertFalse("healthcheck is failing", hcResult.isHealthy());
        assertEquals("client is null", hcResult.getMessage());
    }

    private HealthCheck.Result resultForState(CuratorFrameworkState state) {
        final CuratorFramework curator = mock(CuratorFramework.class);
        when(curator.getState()).thenReturn(state);

        final CuratorHealthCheck hc = new CuratorHealthCheck(curator);
        REGISTRY.register("testWith"+state, hc);
        return REGISTRY.runHealthCheck("testWith"+state);
    }
}
