package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class EmailVerification extends ApiResource {

    public static final String OBJECT_NAME = "email_verification";

    public EmailVerification() {
        super();
    }

    public EmailVerification(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
