package com.emailit.events;

import java.util.Map;

public class DomainDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "domain.deleted";

    public DomainDeleted() {
        super();
    }

    public DomainDeleted(Map<String, Object> values) {
        super(values);
    }
}
