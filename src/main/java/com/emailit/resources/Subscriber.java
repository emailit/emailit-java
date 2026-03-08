package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Subscriber extends ApiResource {

    public static final String OBJECT_NAME = "subscriber";

    public Subscriber() {
        super();
    }

    public Subscriber(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
