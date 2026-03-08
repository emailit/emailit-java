package com.emailit.util;

import com.emailit.ApiResource;
import com.emailit.resources.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ObjectTypes {

    private static final Map<String, Supplier<ApiResource>> MAPPING;

    static {
        Map<String, Supplier<ApiResource>> m = new HashMap<>();
        m.put(Email.OBJECT_NAME, Email::new);
        m.put(Domain.OBJECT_NAME, Domain::new);
        m.put(ApiKey.OBJECT_NAME, ApiKey::new);
        m.put(Audience.OBJECT_NAME, Audience::new);
        m.put(Subscriber.OBJECT_NAME, Subscriber::new);
        m.put(Template.OBJECT_NAME, Template::new);
        m.put(Suppression.OBJECT_NAME, Suppression::new);
        m.put(EmailVerification.OBJECT_NAME, EmailVerification::new);
        m.put(EmailVerificationList.OBJECT_NAME, EmailVerificationList::new);
        m.put(Webhook.OBJECT_NAME, Webhook::new);
        m.put(Contact.OBJECT_NAME, Contact::new);
        m.put(Event.OBJECT_NAME, Event::new);
        MAPPING = Collections.unmodifiableMap(m);
    }

    private ObjectTypes() {
    }

    public static Map<String, Supplier<ApiResource>> mapping() {
        return MAPPING;
    }
}
