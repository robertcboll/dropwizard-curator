package com.robertcboll.dropwizard.curator.rule;

import com.google.common.base.Throwables;
import com.google.common.io.Closeables;
import org.apache.curator.test.TestingServer;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class ZookeeperTestRule extends ExternalResource {

    private TestingServer zk;
    private final int port;

    public ZookeeperTestRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        zk = new TestingServer(port);
    }

    @Override
    protected void after() {
        try {
            zk.close();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public TestingServer zookeeper() {
        return this.zk;
    }
}
