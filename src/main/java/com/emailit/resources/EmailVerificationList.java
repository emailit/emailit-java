package com.emailit.resources;

import com.emailit.ApiResource;

import java.util.Map;

public class EmailVerificationList extends ApiResource {

    public static final String OBJECT_NAME = "email_verification_list";

    public EmailVerificationList() {
        super();
    }

    public EmailVerificationList(Map<String, Object> values) {
        super(values);
    }

    @Override
    public String getObjectName() {
        return OBJECT_NAME;
    }
}
