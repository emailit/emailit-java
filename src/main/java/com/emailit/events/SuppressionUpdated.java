package com.emailit.events;

import java.util.Map;

public class SuppressionUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "suppression.updated";

    public SuppressionUpdated() {
        super();
    }

    public SuppressionUpdated(Map<String, Object> values) {
        super(values);
    }
}
