package com.robertcboll.dropwizard.curator.example;

import com.robertcboll.dropwizard.curator.example.basic.BasicCuratorApplication;
import com.robertcboll.dropwizard.curator.example.discovery.configured.ConfiguredCuratorDiscoveryApplication;
import com.robertcboll.dropwizard.curator.example.discovery.simple.SimpleCuratorDiscoveryApplication;

/**
 *
 *
 */
public class ExampleMain {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            invalid();
        } else {
            String appname = args[0];
            switch (appname) {
                case "basic":                   basic();
                                                break;
                case "configured-discovery":    configuredDiscovery();
                                                break;
                case "simple-discovery":        simpleDiscovery();
                                                break;
                default:                        invalid();
                                                break;
            }
        }
    }

    private static void basic() throws Exception {
        new BasicCuratorApplication().run(new String[] {"server", "conf/curator-basic.yaml"});
    }

    private static void configuredDiscovery() throws Exception {
        new ConfiguredCuratorDiscoveryApplication().run(new String[] {"server", "conf/curator-configured-discovery.yaml"});
    }

    private static void simpleDiscovery() throws Exception {
        new SimpleCuratorDiscoveryApplication().run(new String[]{"server", "conf/curator-simple-discovery.yaml"});
    }

    private static void invalid() throws Exception {
        throw new IllegalArgumentException("Valid arguments are: {'basic', 'configured-discovery', 'simple-discovery'}.");
    }
}
