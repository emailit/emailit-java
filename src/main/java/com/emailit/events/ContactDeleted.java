package com.emailit.events;

import java.util.Map;

public class ContactDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "contact.deleted";

    public ContactDeleted() {
        super();
    }

    public ContactDeleted(Map<String, Object> values) {
        super(values);
    }
}
