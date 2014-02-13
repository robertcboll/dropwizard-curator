package com.robertcboll.dropwizard.curator.example.discovery.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robertcboll.dropwizard.curator.discovery.DiscoverableService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 *
 */
public class BarService extends DiscoverableService {

    @Valid @NotNull
    public String description;

    @JsonProperty
    public String getDescription() {
        return this.description;
    }
    @JsonProperty
    public void setDescription(String description) {
        this.description = description;
    }
}
