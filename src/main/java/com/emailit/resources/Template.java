package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Template extends ApiResource {

    public static final String OBJECT_NAME = "template";

    public Template() {
        super();
    }

    public Template(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
