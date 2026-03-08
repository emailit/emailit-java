package com.emailit.events;

import java.util.Map;

public class DomainUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "domain.updated";

    public DomainUpdated() {
        super();
    }

    public DomainUpdated(Map<String, Object> values) {
        super(values);
    }
}
