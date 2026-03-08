package com.emailit.events;

import java.util.Map;

public class SuppressionDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "suppression.deleted";

    public SuppressionDeleted() {
        super();
    }

    public SuppressionDeleted(Map<String, Object> values) {
        super(values);
    }
}
