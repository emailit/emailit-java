package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Audience extends ApiResource {

    public static final String OBJECT_NAME = "audience";

    public Audience() {
        super();
    }

    public Audience(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
