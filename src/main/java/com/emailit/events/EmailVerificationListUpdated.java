package com.emailit.events;

import java.util.Map;

public class EmailVerificationListUpdated extends WebhookEvent {

    public static final String EVENT_TYPE = "email_verification_list.updated";

    public EmailVerificationListUpdated() {
        super();
    }

    public EmailVerificationListUpdated(Map<String, Object> values) {
        super(values);
    }
}
