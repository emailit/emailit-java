package com.emailit.events;

import com.emailit.EmailitObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WebhookEvent extends EmailitObject {

    private static final Map<String, Class<? extends WebhookEvent>> EVENT_MAP;

    static {
        Map<String, Class<? extends WebhookEvent>> m = new HashMap<>();
        m.put(EmailAccepted.EVENT_TYPE, EmailAccepted.class);
        m.put(EmailScheduled.EVENT_TYPE, EmailScheduled.class);
        m.put(EmailDelivered.EVENT_TYPE, EmailDelivered.class);
        m.put(EmailBounced.EVENT_TYPE, EmailBounced.class);
        m.put(EmailAttempted.EVENT_TYPE, EmailAttempted.class);
        m.put(EmailFailed.EVENT_TYPE, EmailFailed.class);
        m.put(EmailRejected.EVENT_TYPE, EmailRejected.class);
        m.put(EmailSuppressed.EVENT_TYPE, EmailSuppressed.class);
        m.put(EmailReceived.EVENT_TYPE, EmailReceived.class);
        m.put(EmailComplained.EVENT_TYPE, EmailComplained.class);
        m.put(EmailClicked.EVENT_TYPE, EmailClicked.class);
        m.put(EmailLoaded.EVENT_TYPE, EmailLoaded.class);
        m.put(DomainCreated.EVENT_TYPE, DomainCreated.class);
        m.put(DomainUpdated.EVENT_TYPE, DomainUpdated.class);
        m.put(DomainDeleted.EVENT_TYPE, DomainDeleted.class);
        m.put(AudienceCreated.EVENT_TYPE, AudienceCreated.class);
        m.put(AudienceUpdated.EVENT_TYPE, AudienceUpdated.class);
        m.put(AudienceDeleted.EVENT_TYPE, AudienceDeleted.class);
        m.put(SubscriberCreated.EVENT_TYPE, SubscriberCreated.class);
        m.put(SubscriberUpdated.EVENT_TYPE, SubscriberUpdated.class);
        m.put(SubscriberDeleted.EVENT_TYPE, SubscriberDeleted.class);
        m.put(ContactCreated.EVENT_TYPE, ContactCreated.class);
        m.put(ContactUpdated.EVENT_TYPE, ContactUpdated.class);
        m.put(ContactDeleted.EVENT_TYPE, ContactDeleted.class);
        m.put(TemplateCreated.EVENT_TYPE, TemplateCreated.class);
        m.put(TemplateUpdated.EVENT_TYPE, TemplateUpdated.class);
        m.put(TemplateDeleted.EVENT_TYPE, TemplateDeleted.class);
        m.put(SuppressionCreated.EVENT_TYPE, SuppressionCreated.class);
        m.put(SuppressionUpdated.EVENT_TYPE, SuppressionUpdated.class);
        m.put(SuppressionDeleted.EVENT_TYPE, SuppressionDeleted.class);
        m.put(EmailVerificationCreated.EVENT_TYPE, EmailVerificationCreated.class);
        m.put(EmailVerificationUpdated.EVENT_TYPE, EmailVerificationUpdated.class);
        m.put(EmailVerificationDeleted.EVENT_TYPE, EmailVerificationDeleted.class);
        m.put(EmailVerificationListCreated.EVENT_TYPE, EmailVerificationListCreated.class);
        m.put(EmailVerificationListUpdated.EVENT_TYPE, EmailVerificationListUpdated.class);
        m.put(EmailVerificationListDeleted.EVENT_TYPE, EmailVerificationListDeleted.class);
        EVENT_MAP = Collections.unmodifiableMap(m);
    }

    public WebhookEvent() {
        super();
    }

    public WebhookEvent(Map<String, Object> values) {
        super(values);
    }

    public static WebhookEvent constructFrom(Map<String, Object> payload) {
        Object type = payload.get("type");

        if (type instanceof String) {
            Class<? extends WebhookEvent> cls = EVENT_MAP.get(type);
            if (cls != null) {
                try {
                    WebhookEvent event = cls.getDeclaredConstructor(Map.class).newInstance(payload);
                    return event;
                } catch (Exception e) {
                    // fall through to generic
                }
            }
        }

        return new WebhookEvent(payload);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getEventData() {
        Object data = get("data");
        if (data instanceof Map) {
            Map<String, Object> dataMap = (Map<String, Object>) data;
            Object obj = dataMap.get("object");
            if (obj instanceof Map) {
                return (Map<String, Object>) obj;
            }
            return dataMap;
        }
        return null;
    }

    public String getType() {
        return getString("type");
    }

    public String getEventId() {
        return getString("event_id");
    }
}
