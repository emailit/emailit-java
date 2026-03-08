package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ContactServiceTest {

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
    void createReturnsContact() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "contact");
        body.put("id", "con_123");
        body.put("email", "contact@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        ContactCreateParams params = ContactCreateParams.builder()
                .setEmail("contact@example.com")
                .build();
        var result = client.contacts().create(params);

        assertThat(result).isInstanceOf(Contact.class);
        assertThat(((Contact) result).getId()).isEqualTo("con_123");
        assertThat(result.getString("email")).isEqualTo("contact@example.com");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/contacts");
    }

    @Test
    void getReturnsContact() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "contact");
        body.put("id", "con_123");
        body.put("email", "contact@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.contacts().get("con_123");

        assertThat(result).isInstanceOf(Contact.class);
        assertThat(((Contact) result).getId()).isEqualTo("con_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/contacts/con_123");
    }

    @Test
    void updateReturnsContact() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "contact");
        body.put("id", "con_123");
        body.put("email", "updated@example.com");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        ContactUpdateParams params = ContactUpdateParams.builder()
                .setEmail("updated@example.com")
                .build();
        var result = client.contacts().update("con_123", params);

        assertThat(result).isInstanceOf(Contact.class);
        assertThat(((Contact) result).getId()).isEqualTo("con_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/contacts/con_123");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "contact");
        item.put("id", "con_123");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.contacts().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(Contact.class);
        assertThat(((Contact) result.getData().get(0)).getId()).isEqualTo("con_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/contacts");
    }

    @Test
    void deleteReturnsContact() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "contact");
        body.put("id", "con_123");
        body.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.contacts().delete("con_123");

        assertThat(result).isInstanceOf(Contact.class);
        assertThat(((Contact) result).getId()).isEqualTo("con_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("DELETE");
        assertThat(req.getPath()).isEqualTo("/v2/contacts/con_123");
    }
}
