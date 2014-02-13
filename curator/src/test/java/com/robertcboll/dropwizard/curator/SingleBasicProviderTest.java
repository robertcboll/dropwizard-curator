package com.robertcboll.dropwizard.curator;

import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import com.robertcboll.dropwizard.curator.app.CuratorTestApplication;
import com.robertcboll.dropwizard.curator.app.CuratorTestConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.test.TestingServer;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

public class SingleBasicProviderTest {

    private static final TestingServer zookeeper;
    static {
        try {
            zookeeper = new TestingServer(59741);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @ClassRule
    public static DropwizardAppRule<CuratorTestConfiguration> APP_RULE = new DropwizardAppRule<>(CuratorTestApplication.class,
            Resources.getResource("curator-basic-single.yaml").getPath());

    @Test
    public void testAvailableSize() throws Exception {
        Collection<String> available = Curators.available();
        assertEquals("one curator is available", 1, available.size());
        assertTrue("curator named test is available", available.contains("test"));
    }

    @Test
    public void testCuratorConfiguration() throws Exception {
        CuratorFramework curator = Curators.named("test");
        assertEquals("curator namespace is empty", "", curator.getNamespace());
    }

    @Test
    public void testHealthchecksRegistered() throws Exception {
        Collection<String> healthchecks = APP_RULE.getEnvironment().healthChecks().getNames();
        assertTrue(healthchecks.contains("curator-connection-test"));
    }

    @Test
    public void testHealthcheckResponse() {
        WebResource health = Client.create().resource("http://localhost:59743/healthcheck");
        ClientResponse response = health
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get(ClientResponse.class);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testReadWrite() throws Exception {
        CuratorFramework curator = Curators.named("test");
        assertEquals("curator namespace is empty", "", curator.getNamespace());

        assertNull(curator.checkExists().forPath("/test"));
        curator.create().forPath("/test");
        assertNotNull(curator.checkExists().forPath("/test"));
        curator.delete().forPath("/test");
        assertNull(curator.checkExists().forPath("/test"));
    }

    @AfterClass
    public static void stopZookeeper() throws IOException {
        zookeeper.close();
    }

   /* @Test
    public void testAdvertised() throws Exception {
        try (ServiceProvider<FooService> provider = new FooService().provider(FooService.class)) {
            provider.start();
            ServiceInstance<FooService> instance = provider.getInstance();
            assertNotNull("instance is not null", instance);
            FooService service = instance.getPayload();
            assertEquals("service description is correct", service.getDescription(), "description test");
        }
    }*/
}
