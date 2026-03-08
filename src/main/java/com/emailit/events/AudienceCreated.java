package com.emailit.events;

import java.util.Map;

public class AudienceCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "audience.created";

    public AudienceCreated() {
        super();
    }

    public AudienceCreated(Map<String, Object> values) {
        super(values);
    }
}
