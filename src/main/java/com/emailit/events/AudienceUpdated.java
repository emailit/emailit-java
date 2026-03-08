package com.emailit.events;

import java.util.Map;

public class AudienceUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "audience.updated";

    public AudienceUpdated() {
        super();
    }

    public AudienceUpdated(Map<String, Object> values) {
        super(values);
    }
}
