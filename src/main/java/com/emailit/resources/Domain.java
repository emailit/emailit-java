package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Domain extends ApiResource {

    public static final String OBJECT_NAME = "domain";

    public Domain() {
        super();
    }

    public Domain(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
