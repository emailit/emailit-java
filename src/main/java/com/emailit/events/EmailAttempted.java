package com.emailit.events;

import java.util.Map;

public class EmailAttempted extends WebhookEvent {

    public static final String EVENT_TYPE = "email.attempted";

    public EmailAttempted() {
        super();
    }

    public EmailAttempted(Map<String, Object> values) {
        super(values);
    }
}
