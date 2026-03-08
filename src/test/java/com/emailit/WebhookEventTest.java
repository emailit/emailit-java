package com.emailit;

import com.emailit.events.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class WebhookEventTest {

    static Stream<Arguments> eventTypes() {
        return Stream.of(
                Arguments.of("email.accepted", EmailAccepted.class),
                Arguments.of("email.scheduled", EmailScheduled.class),
                Arguments.of("email.delivered", EmailDelivered.class),
                Arguments.of("email.bounced", EmailBounced.class),
                Arguments.of("email.attempted", EmailAttempted.class),
                Arguments.of("email.failed", EmailFailed.class),
                Arguments.of("email.rejected", EmailRejected.class),
                Arguments.of("email.suppressed", EmailSuppressed.class),
                Arguments.of("email.received", EmailReceived.class),
                Arguments.of("email.complained", EmailComplained.class),
                Arguments.of("email.clicked", EmailClicked.class),
                Arguments.of("email.loaded", EmailLoaded.class),
                Arguments.of("domain.created", DomainCreated.class),
                Arguments.of("domain.updated", DomainUpdated.class),
                Arguments.of("domain.deleted", DomainDeleted.class),
                Arguments.of("audience.created", AudienceCreated.class),
                Arguments.of("audience.updated", AudienceUpdated.class),
                Arguments.of("audience.deleted", AudienceDeleted.class),
                Arguments.of("subscriber.created", SubscriberCreated.class),
                Arguments.of("subscriber.updated", SubscriberUpdated.class),
                Arguments.of("subscriber.deleted", SubscriberDeleted.class),
                Arguments.of("contact.created", ContactCreated.class),
                Arguments.of("contact.updated", ContactUpdated.class),
                Arguments.of("contact.deleted", ContactDeleted.class),
                Arguments.of("template.created", TemplateCreated.class),
                Arguments.of("template.updated", TemplateUpdated.class),
                Arguments.of("template.deleted", TemplateDeleted.class),
                Arguments.of("suppression.created", SuppressionCreated.class),
                Arguments.of("suppression.updated", SuppressionUpdated.class),
                Arguments.of("suppression.deleted", SuppressionDeleted.class),
                Arguments.of("email_verification.created", EmailVerificationCreated.class),
                Arguments.of("email_verification.updated", EmailVerificationUpdated.class),
                Arguments.of("email_verification.deleted", EmailVerificationDeleted.class),
                Arguments.of("email_verification_list.created", EmailVerificationListCreated.class),
                Arguments.of("email_verification_list.updated", EmailVerificationListUpdated.class),
                Arguments.of("email_verification_list.deleted", EmailVerificationListDeleted.class)
        );
    }

    @ParameterizedTest
    @MethodSource("eventTypes")
    void constructFromReturnsCorrectType(String type, Class<? extends WebhookEvent> expectedClass) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("event_id", "evt_123");
        payload.put("type", type);

        Map<String, Object> data = new HashMap<>();
        data.put("id", "res_123");
        payload.put("data", data);

        WebhookEvent event = WebhookEvent.constructFrom(payload);

        assertThat(event).isInstanceOf(expectedClass);
        assertThat(event.getType()).isEqualTo(type);
        assertThat(event.getEventId()).isEqualTo("evt_123");
    }

    @Test
    void constructFromReturnsGenericForUnknownType() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("type", "unknown.event");
        payload.put("event_id", "evt_456");

        WebhookEvent event = WebhookEvent.constructFrom(payload);

        assertThat(event).isExactlyInstanceOf(WebhookEvent.class);
        assertThat(event.getType()).isEqualTo("unknown.event");
    }

    @Test
    void constructFromHandlesMissingType() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("event_id", "evt_789");

        WebhookEvent event = WebhookEvent.constructFrom(payload);

        assertThat(event).isExactlyInstanceOf(WebhookEvent.class);
        assertThat(event.getEventId()).isEqualTo("evt_789");
    }

    @Test
    void getEventDataReturnsDataMap() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", "em_123");
        data.put("status", "delivered");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("type", "email.delivered");
        payload.put("data", data);

        WebhookEvent event = WebhookEvent.constructFrom(payload);
        Map<String, Object> eventData = event.getEventData();

        assertThat(eventData).isNotNull();
        assertThat(eventData.get("id")).isEqualTo("em_123");
        assertThat(eventData.get("status")).isEqualTo("delivered");
    }

    @Test
    void getEventDataReturnsNestedObject() {
        Map<String, Object> innerObj = new LinkedHashMap<>();
        innerObj.put("id", "em_456");

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("object", innerObj);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("type", "email.delivered");
        payload.put("data", data);

        WebhookEvent event = WebhookEvent.constructFrom(payload);
        Map<String, Object> eventData = event.getEventData();

        assertThat(eventData).isNotNull();
        assertThat(eventData.get("id")).isEqualTo("em_456");
    }

    @Test
    void getEventDataReturnsNullWhenNoData() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("type", "email.delivered");

        WebhookEvent event = WebhookEvent.constructFrom(payload);

        assertThat(event.getEventData()).isNull();
    }
}
