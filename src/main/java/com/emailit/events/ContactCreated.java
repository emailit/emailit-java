package com.emailit.events;

import java.util.Map;

public class ContactCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "contact.created";

    public ContactCreated() {
        super();
    }

    public ContactCreated(Map<String, Object> values) {
        super(values);
    }
}
