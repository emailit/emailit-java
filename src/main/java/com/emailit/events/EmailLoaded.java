package com.emailit.events;

import java.util.Map;

public class EmailLoaded extends WebhookEvent {

    public static final String EVENT_TYPE = "email.loaded";

    public EmailLoaded() {
        super();
    }

    public EmailLoaded(Map<String, Object> values) {
        super(values);
    }
}
