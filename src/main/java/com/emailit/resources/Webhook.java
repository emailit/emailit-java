package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Webhook extends ApiResource {

    public static final String OBJECT_NAME = "webhook";

    public Webhook() {
        super();
    }

    public Webhook(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
