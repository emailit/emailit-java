package com.emailit.events;

import java.util.Map;

public class SubscriberUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "subscriber.updated";

    public SubscriberUpdated() {
        super();
    }

    public SubscriberUpdated(Map<String, Object> values) {
        super(values);
    }
}
