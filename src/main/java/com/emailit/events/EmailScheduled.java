package com.emailit.events;

import java.util.Map;

public class EmailScheduled extends WebhookEvent {

    public static final String EVENT_TYPE = "email.scheduled";

    public EmailScheduled() {
        super();
    }

    public EmailScheduled(Map<String, Object> values) {
        super(values);
    }
}
