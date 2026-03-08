package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Suppression extends ApiResource {

    public static final String OBJECT_NAME = "suppression";

    public Suppression() {
        super();
    }

    public Suppression(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
