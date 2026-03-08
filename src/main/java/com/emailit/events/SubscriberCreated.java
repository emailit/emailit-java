package com.emailit.events;

import java.util.Map;

public class SubscriberCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "subscriber.created";

    public SubscriberCreated() {
        super();
    }

    public SubscriberCreated(Map<String, Object> values) {
        super(values);
    }
}
