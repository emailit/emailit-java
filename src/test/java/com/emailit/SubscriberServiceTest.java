package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class SubscriberServiceTest {

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
    void createReturnsSubscriber() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "subscriber");
        body.put("id", "sub_456");
        body.put("email", "test@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        SubscriberCreateParams params = SubscriberCreateParams.builder()
                .setEmail("test@example.com")
                .build();
        var result = client.subscribers().create("aud_123", params);

        assertThat(result).isInstanceOf(Subscriber.class);
        assertThat(((Subscriber) result).getId()).isEqualTo("sub_456");
        assertThat(result.getString("email")).isEqualTo("test@example.com");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/audiences/aud_123/subscribers");
    }

    @Test
    void getReturnsSubscriber() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "subscriber");
        body.put("id", "sub_456");
        body.put("email", "test@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.subscribers().get("aud_123", "sub_456");

        assertThat(result).isInstanceOf(Subscriber.class);
        assertThat(((Subscriber) result).getId()).isEqualTo("sub_456");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/audiences/aud_123/subscribers/sub_456");
    }

    @Test
    void updateReturnsSubscriber() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "subscriber");
        body.put("id", "sub_456");
        body.put("email", "updated@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        SubscriberUpdateParams params = SubscriberUpdateParams.builder()
                .setEmail("updated@example.com")
                .build();
        var result = client.subscribers().update("aud_123", "sub_456", params);

        assertThat(result).isInstanceOf(Subscriber.class);
        assertThat(((Subscriber) result).getId()).isEqualTo("sub_456");
        assertThat(result.getString("email")).isEqualTo("updated@example.com");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/audiences/aud_123/subscribers/sub_456");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "subscriber");
        item.put("id", "sub_456");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.subscribers().list("aud_123");

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(Subscriber.class);
        assertThat(((Subscriber) result.getData().get(0)).getId()).isEqualTo("sub_456");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/audiences/aud_123/subscribers");
    }

    @Test
    void deleteReturnsSubscriber() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "subscriber");
        body.put("id", "sub_456");
        body.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.subscribers().delete("aud_123", "sub_456");

        assertThat(result).isInstanceOf(Subscriber.class);
        assertThat(((Subscriber) result).getId()).isEqualTo("sub_456");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("DELETE");
        assertThat(req.getPath()).isEqualTo("/v2/audiences/aud_123/subscribers/sub_456");
    }
}
