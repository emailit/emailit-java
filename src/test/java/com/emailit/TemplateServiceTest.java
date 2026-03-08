package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class TemplateServiceTest {

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
    void createReturnsTemplate() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "template");
        body.put("id", "tpl_123");
        body.put("name", "My Template");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        TemplateCreateParams params = TemplateCreateParams.builder()
                .setName("My Template")
                .build();
        var result = client.templates().create(params);

        assertThat(result).isInstanceOf(Template.class);
        assertThat(((Template) result).getId()).isEqualTo("tpl_123");
        assertThat(result.getString("name")).isEqualTo("My Template");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/templates");
    }

    @Test
    void getReturnsTemplate() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "template");
        body.put("id", "tpl_123");
        body.put("name", "My Template");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.templates().get("tpl_123");

        assertThat(result).isInstanceOf(Template.class);
        assertThat(((Template) result).getId()).isEqualTo("tpl_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/templates/tpl_123");
    }

    @Test
    void updateReturnsTemplate() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "template");
        body.put("id", "tpl_123");
        body.put("name", "Updated Template");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        TemplateUpdateParams params = TemplateUpdateParams.builder()
                .setName("Updated Template")
                .build();
        var result = client.templates().update("tpl_123", params);

        assertThat(result).isInstanceOf(Template.class);
        assertThat(((Template) result).getId()).isEqualTo("tpl_123");
        assertThat(result.getString("name")).isEqualTo("Updated Template");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/templates/tpl_123");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "template");
        item.put("id", "tpl_123");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.templates().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(Template.class);
        assertThat(((Template) result.getData().get(0)).getId()).isEqualTo("tpl_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/templates");
    }

    @Test
    void deleteReturnsTemplate() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "template");
        body.put("id", "tpl_123");
        body.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.templates().delete("tpl_123");

        assertThat(result).isInstanceOf(Template.class);
        assertThat(((Template) result).getId()).isEqualTo("tpl_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("DELETE");
        assertThat(req.getPath()).isEqualTo("/v2/templates/tpl_123");
    }

    @Test
    void publishReturnsTemplate() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "template");
        body.put("id", "tpl_123");
        body.put("status", "published");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.templates().publish("tpl_123");

        assertThat(result).isInstanceOf(Template.class);
        assertThat(((Template) result).getId()).isEqualTo("tpl_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/templates/tpl_123/publish");
    }
}
