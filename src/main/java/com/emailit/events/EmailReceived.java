package com.emailit.events;

import java.util.Map;

public class EmailReceived extends WebhookEvent {

    public static final String EVENT_TYPE = "email.received";

    public EmailReceived() {
        super();
    }

    public EmailReceived(Map<String, Object> values) {
        super(values);
    }
}
