package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class Email extends ApiResource {

    public static final String OBJECT_NAME = "email";

    public Email() {
        super();
    }

    public Email(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
