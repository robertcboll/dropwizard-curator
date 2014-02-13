package com.robertcboll.dropwizard.curator.example.discovery.service;

import com.robertcboll.dropwizard.curator.discovery.DiscoverableService;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 *
 */
public class FooService extends DiscoverableService {

    private String description;

    public FooService() { }

    public FooService(String description) {
        this.description = description;
    }

    @JsonProperty
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }
}
