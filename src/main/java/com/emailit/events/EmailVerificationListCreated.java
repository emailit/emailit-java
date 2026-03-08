package com.emailit.events;

import java.util.Map;

public class EmailVerificationListCreated extends WebhookEvent {

    public static final String EVENT_TYPE = "email_verification_list.created";

    public EmailVerificationListCreated() {
        super();
    }

    public EmailVerificationListCreated(Map<String, Object> values) {
        super(values);
    }
}
