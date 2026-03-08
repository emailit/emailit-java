package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Event extends ApiResource {

    public static final String OBJECT_NAME = "event";

    public Event() {
        super();
    }

    public Event(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
