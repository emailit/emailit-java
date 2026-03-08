package com.emailit.events;

import java.util.Map;

public class SubscriberDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "subscriber.deleted";

    public SubscriberDeleted() {
        super();
    }

    public SubscriberDeleted(Map<String, Object> values) {
        super(values);
    }
}
