package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class WebhookServiceTest {

    private MockWebServer server;
    private EmailitClient client;

    @BeforeEach
    void setUp() throws Exception {
        server = TestHelpers.startMockServer();
        client = TestHelpers.mockClient(server);
    }

    @AfterEach
    void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    void createReturnsWebhook() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "webhook");
        body.put("id", "wh_123");
        body.put("url", "https://example.com/webhook");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        WebhookCreateParams params = WebhookCreateParams.builder()
                .setName("My Webhook")
                .setUrl("https://example.com/webhook")
                .build();
        var result = client.webhooks().create(params);

        assertThat(result).isInstanceOf(Webhook.class);
        assertThat(((Webhook) result).getId()).isEqualTo("wh_123");
        assertThat(result.getString("url")).isEqualTo("https://example.com/webhook");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/webhooks");
    }

    @Test
    void getReturnsWebhook() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "webhook");
        body.put("id", "wh_123");
        body.put("url", "https://example.com/webhook");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.webhooks().get("wh_123");

        assertThat(result).isInstanceOf(Webhook.class);
        assertThat(((Webhook) result).getId()).isEqualTo("wh_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/webhooks/wh_123");
    }

    @Test
    void updateReturnsWebhook() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "webhook");
        body.put("id", "wh_123");
        body.put("url", "https://example.com/webhook-updated");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        WebhookUpdateParams params = WebhookUpdateParams.builder()
                .setUrl("https://example.com/webhook-updated")
                .build();
        var result = client.webhooks().update("wh_123", params);

        assertThat(result).isInstanceOf(Webhook.class);
        assertThat(((Webhook) result).getId()).isEqualTo("wh_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/webhooks/wh_123");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "webhook");
        item.put("id", "wh_123");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.webhooks().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(Webhook.class);
        assertThat(((Webhook) result.getData().get(0)).getId()).isEqualTo("wh_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/webhooks");
    }

    @Test
    void deleteReturnsWebhook() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "webhook");
        body.put("id", "wh_123");
        body.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.webhooks().delete("wh_123");

        assertThat(result).isInstanceOf(Webhook.class);
        assertThat(((Webhook) result).getId()).isEqualTo("wh_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("DELETE");
        assertThat(req.getPath()).isEqualTo("/v2/webhooks/wh_123");
    }
}
