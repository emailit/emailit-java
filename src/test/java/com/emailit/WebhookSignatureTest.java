package com.emailit;

import com.emailit.events.WebhookEvent;
import com.emailit.exception.EmailitException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class WebhookSignatureTest {

    private static final String SECRET = "whsec_test_secret";
    private static final String PAYLOAD = "{\"type\":\"email.delivered\",\"data\":{\"id\":\"em_123\"}}";

    @Test
    void verifyValidSignature() throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = WebhookSignature.computeSignature(PAYLOAD, timestamp, SECRET);

        WebhookEvent event = WebhookSignature.verify(PAYLOAD, signature, timestamp, SECRET);

        assertThat(event).isNotNull();
        assertThat(event.getType()).isEqualTo("email.delivered");
    }

    @Test
    void verifyRejectsInvalidSignature() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        assertThatThrownBy(() ->
                WebhookSignature.verify(PAYLOAD, "invalidsignature", timestamp, SECRET))
                .isInstanceOf(EmailitException.class)
                .hasMessageContaining("Webhook signature verification failed");
    }

    @Test
    void verifyRejectsOldTimestamp() {
        long oldTimestamp = (System.currentTimeMillis() / 1000) - 600;
        String timestamp = String.valueOf(oldTimestamp);
        String signature = WebhookSignature.computeSignature(PAYLOAD, timestamp, SECRET);

        assertThatThrownBy(() ->
                WebhookSignature.verify(PAYLOAD, signature, timestamp, SECRET))
                .isInstanceOf(EmailitException.class)
                .hasMessageContaining("replay attack");
    }

    @Test
    void verifySkipsReplayCheckWhenToleranceNull() throws Exception {
        long oldTimestamp = (System.currentTimeMillis() / 1000) - 600;
        String timestamp = String.valueOf(oldTimestamp);
        String signature = WebhookSignature.computeSignature(PAYLOAD, timestamp, SECRET);

        WebhookEvent event = WebhookSignature.verify(PAYLOAD, signature, timestamp, SECRET, null);
        assertThat(event).isNotNull();
        assertThat(event.getType()).isEqualTo("email.delivered");
    }

    @Test
    void verifyWithCustomTolerance() throws Exception {
        long recentTimestamp = (System.currentTimeMillis() / 1000) - 10;
        String timestamp = String.valueOf(recentTimestamp);
        String signature = WebhookSignature.computeSignature(PAYLOAD, timestamp, SECRET);

        WebhookEvent event = WebhookSignature.verify(PAYLOAD, signature, timestamp, SECRET, 60);
        assertThat(event).isNotNull();
    }

    @Test
    void computeSignatureProducesConsistentResult() {
        String sig1 = WebhookSignature.computeSignature(PAYLOAD, "1234567890", SECRET);
        String sig2 = WebhookSignature.computeSignature(PAYLOAD, "1234567890", SECRET);

        assertThat(sig1).isEqualTo(sig2);
        assertThat(sig1).hasSize(64); // SHA-256 hex string
    }

    @Test
    void computeSignatureChangesWithPayload() {
        String sig1 = WebhookSignature.computeSignature("body1", "1234567890", SECRET);
        String sig2 = WebhookSignature.computeSignature("body2", "1234567890", SECRET);

        assertThat(sig1).isNotEqualTo(sig2);
    }

    @Test
    void computeSignatureChangesWithTimestamp() {
        String sig1 = WebhookSignature.computeSignature(PAYLOAD, "1111111111", SECRET);
        String sig2 = WebhookSignature.computeSignature(PAYLOAD, "2222222222", SECRET);

        assertThat(sig1).isNotEqualTo(sig2);
    }

    @Test
    void computeSignatureChangesWithSecret() {
        String sig1 = WebhookSignature.computeSignature(PAYLOAD, "1234567890", "secret1");
        String sig2 = WebhookSignature.computeSignature(PAYLOAD, "1234567890", "secret2");

        assertThat(sig1).isNotEqualTo(sig2);
    }
}
