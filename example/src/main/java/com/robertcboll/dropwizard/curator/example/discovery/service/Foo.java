package com.robertcboll.dropwizard.curator.example.discovery.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class Foo {

    @JsonProperty
    private String name;
    @JsonProperty
    private String description;

    public Foo() { }

    public Foo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Foo foo = (Foo) o;

        if (!description.equals(foo.description)) return false;
        if (!name.equals(foo.name)) return false;

        return true;
    }
}
