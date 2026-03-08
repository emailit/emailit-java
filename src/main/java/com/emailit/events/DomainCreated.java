package com.emailit.events;

import java.util.Map;

public class DomainCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "domain.created";

    public DomainCreated() {
        super();
    }

    public DomainCreated(Map<String, Object> values) {
        super(values);
    }
}
