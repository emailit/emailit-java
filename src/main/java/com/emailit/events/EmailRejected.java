package com.emailit.events;

import java.util.Map;

public class EmailRejected extends WebhookEvent {

    public static final String EVENT_TYPE = "email.rejected";

    public EmailRejected() {
        super();
    }

    public EmailRejected(Map<String, Object> values) {
        super(values);
    }
}
