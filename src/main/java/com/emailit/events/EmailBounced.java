package com.emailit.events;

import java.util.Map;

public class EmailBounced extends WebhookEvent {

    public static final String EVENT_TYPE = "email.bounced";

    public EmailBounced() {
        super();
    }

    public EmailBounced(Map<String, Object> values) {
        super(values);
    }
}
