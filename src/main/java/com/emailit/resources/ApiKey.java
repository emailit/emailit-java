package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class ApiKey extends ApiResource {

    public static final String OBJECT_NAME = "api_key";

    public ApiKey() {
        super();
    }

    public ApiKey(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
