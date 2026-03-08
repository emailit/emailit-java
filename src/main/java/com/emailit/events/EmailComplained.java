package com.emailit.events;

import java.util.Map;

public class EmailComplained extends WebhookEvent {

    public static final String EVENT_TYPE = "email.complained";

    public EmailComplained() {
        super();
    }

    public EmailComplained(Map<String, Object> values) {
        super(values);
    }
}
