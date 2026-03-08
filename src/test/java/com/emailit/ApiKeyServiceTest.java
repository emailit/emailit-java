package com.emailit;

import com.emailit.exception.*;
import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

class ApiKeyServiceTest {
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
    void createReturnsApiKey() throws Exception {
        Map<String, Object> apiKeyBody = new LinkedHashMap<>();
        apiKeyBody.put("object", "api_key");
        apiKeyBody.put("id", "key_123");
        apiKeyBody.put("name", "My API Key");
        apiKeyBody.put("scope", "sending");
        server.enqueue(TestHelpers.jsonResponse(200, apiKeyBody));

        ApiKeyCreateParams params = ApiKeyCreateParams.builder()
                .setName("My API Key")
                .setScope("sending")
                .build();
        var result = client.apiKeys().create(params);

        assertThat(result).isInstanceOf(ApiKey.class);
        assertThat(result.getString("id")).isEqualTo("key_123");
        assertThat(result.getString("name")).isEqualTo("My API Key");
        assertThat(result.getString("scope")).isEqualTo("sending");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/api-keys");
        Map<String, Object> body = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(body.get("name")).isEqualTo("My API Key");
        assertThat(body.get("scope")).isEqualTo("sending");
    }

    @Test
    void getReturnsApiKey() throws Exception {
        Map<String, Object> apiKeyBody = new LinkedHashMap<>();
        apiKeyBody.put("object", "api_key");
        apiKeyBody.put("id", "key_123");
        apiKeyBody.put("name", "My API Key");
        server.enqueue(TestHelpers.jsonResponse(200, apiKeyBody));

        var result = client.apiKeys().get("key_123");

        assertThat(result).isInstanceOf(ApiKey.class);
        assertThat(result.getString("id")).isEqualTo("key_123");
        assertThat(result.getString("name")).isEqualTo("My API Key");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/v2/api-keys/key_123");
    }

    @Test
    void updateReturnsApiKey() throws Exception {
        Map<String, Object> apiKeyBody = new LinkedHashMap<>();
        apiKeyBody.put("object", "api_key");
        apiKeyBody.put("id", "key_123");
        apiKeyBody.put("name", "Updated API Key");
        apiKeyBody.put("scope", "admin");
        server.enqueue(TestHelpers.jsonResponse(200, apiKeyBody));

        ApiKeyUpdateParams params = ApiKeyUpdateParams.builder()
                .setName("Updated API Key")
                .setScope("admin")
                .build();
        var result = client.apiKeys().update("key_123", params);

        assertThat(result).isInstanceOf(ApiKey.class);
        assertThat(result.getString("id")).isEqualTo("key_123");
        assertThat(result.getString("name")).isEqualTo("Updated API Key");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/api-keys/key_123");
        Map<String, Object> body = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(body.get("name")).isEqualTo("Updated API Key");
        assertThat(body.get("scope")).isEqualTo("admin");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> apiKey1 = new LinkedHashMap<>();
        apiKey1.put("object", "api_key");
        apiKey1.put("id", "key_123");
        apiKey1.put("name", "My API Key");
        List<Map<String, Object>> data = new ArrayList<>();
        data.add(apiKey1);
        Map<String, Object> listBody = new LinkedHashMap<>();
        listBody.put("object", "list");
        listBody.put("data", data);
        server.enqueue(TestHelpers.jsonResponse(200, listBody));

        var result = client.apiKeys().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.getData()).hasSize(1);
        assertThat(result.getData().get(0)).isInstanceOf(ApiKey.class);
        assertThat(result.getData().get(0).getString("id")).isEqualTo("key_123");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/v2/api-keys");
    }

    @Test
    void deleteReturnsApiKey() throws Exception {
        Map<String, Object> apiKeyBody = new LinkedHashMap<>();
        apiKeyBody.put("object", "api_key");
        apiKeyBody.put("id", "key_123");
        apiKeyBody.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, apiKeyBody));

        var result = client.apiKeys().delete("key_123");

        assertThat(result).isInstanceOf(ApiKey.class);
        assertThat(result.getString("id")).isEqualTo("key_123");
        assertThat(result.getBoolean("deleted")).isTrue();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("DELETE");
        assertThat(request.getPath()).isEqualTo("/v2/api-keys/key_123");
    }
}
