package com.emailit.events;

import java.util.Map;

public class EmailClicked extends WebhookEvent {

    public static final String EVENT_TYPE = "email.clicked";

    public EmailClicked() {
        super();
    }

    public EmailClicked(Map<String, Object> values) {
        super(values);
    }
}
