package com.aftership.sdk.rest;

public enum HttpMethod {
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    GET("GET"),
    DELETE("DELETE");

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
