package com.emailit.events;

import java.util.Map;

public class EmailFailed extends WebhookEvent {

    public static final String EVENT_TYPE = "email.failed";

    public EmailFailed() {
        super();
    }

    public EmailFailed(Map<String, Object> values) {
        super(values);
    }
}
