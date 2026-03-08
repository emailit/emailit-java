package com.emailit.events;

import java.util.Map;

public class TemplateCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "template.created";

    public TemplateCreated() {
        super();
    }

    public TemplateCreated(Map<String, Object> values) {
        super(values);
    }
}
