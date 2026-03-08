package com.emailit;

import com.emailit.exception.*;
import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

class AudienceServiceTest {
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
    void createReturnsAudience() throws Exception {
        Map<String, Object> audienceBody = new LinkedHashMap<>();
        audienceBody.put("object", "audience");
        audienceBody.put("id", "aud_123");
        audienceBody.put("name", "My Audience");
        server.enqueue(TestHelpers.jsonResponse(200, audienceBody));

        AudienceCreateParams params = AudienceCreateParams.builder().setName("My Audience").build();
        var result = client.audiences().create(params);

        assertThat(result).isInstanceOf(Audience.class);
        assertThat(result.getString("id")).isEqualTo("aud_123");
        assertThat(result.getString("name")).isEqualTo("My Audience");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/audiences");
        Map<String, Object> body = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(body.get("name")).isEqualTo("My Audience");
    }

    @Test
    void getReturnsAudience() throws Exception {
        Map<String, Object> audienceBody = new LinkedHashMap<>();
        audienceBody.put("object", "audience");
        audienceBody.put("id", "aud_123");
        audienceBody.put("name", "My Audience");
        server.enqueue(TestHelpers.jsonResponse(200, audienceBody));

        var result = client.audiences().get("aud_123");

        assertThat(result).isInstanceOf(Audience.class);
        assertThat(result.getString("id")).isEqualTo("aud_123");
        assertThat(result.getString("name")).isEqualTo("My Audience");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/v2/audiences/aud_123");
    }

    @Test
    void updateReturnsAudience() throws Exception {
        Map<String, Object> audienceBody = new LinkedHashMap<>();
        audienceBody.put("object", "audience");
        audienceBody.put("id", "aud_123");
        audienceBody.put("name", "Updated Audience");
        server.enqueue(TestHelpers.jsonResponse(200, audienceBody));

        AudienceUpdateParams params = AudienceUpdateParams.builder().setName("Updated Audience").build();
        var result = client.audiences().update("aud_123", params);

        assertThat(result).isInstanceOf(Audience.class);
        assertThat(result.getString("id")).isEqualTo("aud_123");
        assertThat(result.getString("name")).isEqualTo("Updated Audience");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/audiences/aud_123");
        Map<String, Object> body = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(body.get("name")).isEqualTo("Updated Audience");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> audience1 = new LinkedHashMap<>();
        audience1.put("object", "audience");
        audience1.put("id", "aud_123");
        audience1.put("name", "My Audience");
        List<Map<String, Object>> data = new ArrayList<>();
        data.add(audience1);
        Map<String, Object> listBody = new LinkedHashMap<>();
        listBody.put("object", "list");
        listBody.put("data", data);
        server.enqueue(TestHelpers.jsonResponse(200, listBody));

        var result = client.audiences().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.getData()).hasSize(1);
        assertThat(result.getData().get(0)).isInstanceOf(Audience.class);
        assertThat(result.getData().get(0).getString("id")).isEqualTo("aud_123");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/v2/audiences");
    }

    @Test
    void deleteReturnsAudience() throws Exception {
        Map<String, Object> audienceBody = new LinkedHashMap<>();
        audienceBody.put("object", "audience");
        audienceBody.put("id", "aud_123");
        audienceBody.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, audienceBody));

        var result = client.audiences().delete("aud_123");

        assertThat(result).isInstanceOf(Audience.class);
        assertThat(result.getString("id")).isEqualTo("aud_123");
        assertThat(result.getBoolean("deleted")).isTrue();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("DELETE");
        assertThat(request.getPath()).isEqualTo("/v2/audiences/aud_123");
    }
}
