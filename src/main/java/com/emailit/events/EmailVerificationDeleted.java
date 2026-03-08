package com.emailit.events;

import java.util.Map;

public class EmailVerificationDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "email_verification.deleted";

    public EmailVerificationDeleted() {
        super();
    }

    public EmailVerificationDeleted(Map<String, Object> values) {
        super(values);
    }
}
