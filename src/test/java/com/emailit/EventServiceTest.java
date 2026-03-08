package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class EventServiceTest {

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
    void listReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "event");
        item.put("id", "evt_123");
        item.put("type", "email.sent");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.events().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(Event.class);
        assertThat(((Event) result.getData().get(0)).getId()).isEqualTo("evt_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/events");
    }

    @Test
    void getReturnsEvent() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "event");
        body.put("id", "evt_123");
        body.put("type", "email.sent");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.events().get("evt_123");

        assertThat(result).isInstanceOf(Event.class);
        assertThat(((Event) result).getId()).isEqualTo("evt_123");
        assertThat(result.getString("type")).isEqualTo("email.sent");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/events/evt_123");
    }
}
