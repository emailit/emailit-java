package com.emailit.events;

import java.util.Map;

public class ContactUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "contact.updated";

    public ContactUpdated() {
        super();
    }

    public ContactUpdated(Map<String, Object> values) {
        super(values);
    }
}
