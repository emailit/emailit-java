package com.emailit.events;

import java.util.Map;

public class EmailVerificationCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "email_verification.created";

    public EmailVerificationCreated() {
        super();
    }

    public EmailVerificationCreated(Map<String, Object> values) {
        super(values);
    }
}
