package com.emailit.events;

import java.util.Map;

public class EmailDelivered extends WebhookEvent {

    public static final String EVENT_TYPE = "email.delivered";

    public EmailDelivered() {
        super();
    }

    public EmailDelivered(Map<String, Object> values) {
        super(values);
    }
}
