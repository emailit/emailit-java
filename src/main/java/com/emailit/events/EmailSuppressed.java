package com.emailit.events;

import java.util.Map;

public class EmailSuppressed extends WebhookEvent {

    public static final String EVENT_TYPE = "email.suppressed";

    public EmailSuppressed() {
        super();
    }

    public EmailSuppressed(Map<String, Object> values) {
        super(values);
    }
}
