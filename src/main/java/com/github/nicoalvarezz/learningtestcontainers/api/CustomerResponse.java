package com.github.nicoalvarezz.learningtestcontainers.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResponse {

    private String id;
    private String content;


    @SuppressWarnings("unused")
    public CustomerResponse() {
        // Jackson deserialization
    }

    public CustomerResponse(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", content='" + content + '\'' + '}';
    }
}
