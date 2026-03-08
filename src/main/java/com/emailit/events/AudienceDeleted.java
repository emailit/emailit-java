package com.emailit.events;

import java.util.Map;

public class AudienceDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "audience.deleted";

    public AudienceDeleted() {
        super();
    }

    public AudienceDeleted(Map<String, Object> values) {
        super(values);
    }
}
