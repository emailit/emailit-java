package com.emailit.events;

import java.util.Map;

public class EmailVerificationUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "email_verification.updated";

    public EmailVerificationUpdated() {
        super();
    }

    public EmailVerificationUpdated(Map<String, Object> values) {
        super(values);
    }
}
