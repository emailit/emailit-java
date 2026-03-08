package com.emailit.events;

import java.util.Map;

public class TemplateUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "template.updated";

    public TemplateUpdated() {
        super();
    }

    public TemplateUpdated(Map<String, Object> values) {
        super(values);
    }
}
