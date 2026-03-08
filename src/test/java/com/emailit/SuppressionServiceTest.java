package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class SuppressionServiceTest {

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
    void createReturnsSuppression() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "suppression");
        body.put("id", "sup_123");
        body.put("email", "bounce@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        SuppressionCreateParams params = SuppressionCreateParams.builder()
                .setEmail("bounce@example.com")
                .build();
        var result = client.suppressions().create(params);

        assertThat(result).isInstanceOf(Suppression.class);
        assertThat(((Suppression) result).getId()).isEqualTo("sup_123");
        assertThat(result.getString("email")).isEqualTo("bounce@example.com");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/suppressions");
    }

    @Test
    void getReturnsSuppression() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "suppression");
        body.put("id", "sup_123");
        body.put("email", "bounce@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.suppressions().get("sup_123");

        assertThat(result).isInstanceOf(Suppression.class);
        assertThat(((Suppression) result).getId()).isEqualTo("sup_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/suppressions/sup_123");
    }

    @Test
    void updateReturnsSuppression() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "suppression");
        body.put("id", "sup_123");
        body.put("reason", "updated");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        SuppressionUpdateParams params = SuppressionUpdateParams.builder()
                .setReason("updated")
                .build();
        var result = client.suppressions().update("sup_123", params);

        assertThat(result).isInstanceOf(Suppression.class);
        assertThat(((Suppression) result).getId()).isEqualTo("sup_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/suppressions/sup_123");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "suppression");
        item.put("id", "sup_123");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.suppressions().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(Suppression.class);
        assertThat(((Suppression) result.getData().get(0)).getId()).isEqualTo("sup_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/suppressions");
    }

    @Test
    void deleteReturnsSuppression() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "suppression");
        body.put("id", "sup_123");
        body.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.suppressions().delete("sup_123");

        assertThat(result).isInstanceOf(Suppression.class);
        assertThat(((Suppression) result).getId()).isEqualTo("sup_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("DELETE");
        assertThat(req.getPath()).isEqualTo("/v2/suppressions/sup_123");
    }
}
