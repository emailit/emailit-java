package com.emailit.events;

import java.util.Map;

public class TemplateDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "template.deleted";

    public TemplateDeleted() {
        super();
    }

    public TemplateDeleted(Map<String, Object> values) {
        super(values);
    }
}
