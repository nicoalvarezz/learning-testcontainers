package com.github.nicoalvarezz.learningtestcontainers.configuration;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.NotEmpty;

public class CustomerConfig extends Configuration {

    @NotEmpty
    private String test = "Hello";

    @JsonProperty
    public String test() {
        return test;
    }

    @JsonProperty
    public void test(String name) {
        this.test = name;
    }
}
