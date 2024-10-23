package com.ilnaz.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConfiguration {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
