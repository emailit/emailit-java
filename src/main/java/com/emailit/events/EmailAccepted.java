package com.emailit.events;

import java.util.Map;

public class EmailAccepted extends WebhookEvent {

    public static final String EVENT_TYPE = "email.accepted";

    public EmailAccepted() {
        super();
    }

    public EmailAccepted(Map<String, Object> values) {
        super(values);
    }
}
