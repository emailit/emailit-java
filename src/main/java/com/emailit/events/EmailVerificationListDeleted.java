package com.emailit.events;

import java.util.Map;

public class EmailVerificationListDeleted extends WebhookEvent {

    public static final String EVENT_TYPE = "email_verification_list.deleted";

    public EmailVerificationListDeleted() {
        super();
    }

    public EmailVerificationListDeleted(Map<String, Object> values) {
        super(values);
    }
}
