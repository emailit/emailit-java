package com.emailit.events;

import java.util.Map;

public class SuppressionCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "suppression.created";

    public SuppressionCreated() {
        super();
    }

    public SuppressionCreated(Map<String, Object> values) {
        super(values);
    }
}
