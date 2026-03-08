package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Contact extends ApiResource {

    public static final String OBJECT_NAME = "contact";

    public Contact() {
        super();
    }

    public Contact(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
