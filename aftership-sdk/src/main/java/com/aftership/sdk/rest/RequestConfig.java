package com.aftership.sdk.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestConfig {
    private HttpMethod method;
    private String path;
}
