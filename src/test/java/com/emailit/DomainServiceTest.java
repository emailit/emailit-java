package com.emailit;

import com.emailit.exception.*;
import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

class DomainServiceTest {
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
    void createReturnsDomain() throws Exception {
        Map<String, Object> domainBody = new LinkedHashMap<>();
        domainBody.put("object", "domain");
        domainBody.put("id", "dom_123");
        domainBody.put("name", "example.com");
        server.enqueue(TestHelpers.jsonResponse(200, domainBody));

        DomainCreateParams params = DomainCreateParams.builder().setName("example.com").build();
        var result = client.domains().create(params);

        assertThat(result).isInstanceOf(Domain.class);
        assertThat(result.getString("id")).isEqualTo("dom_123");
        assertThat(result.getString("name")).isEqualTo("example.com");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/domains");
        Map<String, Object> body = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(body.get("name")).isEqualTo("example.com");
    }

    @Test
    void getReturnsDomain() throws Exception {
        Map<String, Object> domainBody = new LinkedHashMap<>();
        domainBody.put("object", "domain");
        domainBody.put("id", "dom_123");
        domainBody.put("name", "example.com");
        server.enqueue(TestHelpers.jsonResponse(200, domainBody));

        var result = client.domains().get("dom_123");

        assertThat(result).isInstanceOf(Domain.class);
        assertThat(result.getString("id")).isEqualTo("dom_123");
        assertThat(result.getString("name")).isEqualTo("example.com");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/v2/domains/dom_123");
    }

    @Test
    void verifyReturnsDomain() throws Exception {
        Map<String, Object> domainBody = new LinkedHashMap<>();
        domainBody.put("object", "domain");
        domainBody.put("id", "dom_123");
        domainBody.put("name", "example.com");
        domainBody.put("verified", true);
        server.enqueue(TestHelpers.jsonResponse(200, domainBody));

        var result = client.domains().verify("dom_123");

        assertThat(result).isInstanceOf(Domain.class);
        assertThat(result.getString("id")).isEqualTo("dom_123");
        assertThat(result.getBoolean("verified")).isTrue();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/domains/dom_123/verify");
    }

    @Test
    void updateReturnsDomain() throws Exception {
        Map<String, Object> domainBody = new LinkedHashMap<>();
        domainBody.put("object", "domain");
        domainBody.put("id", "dom_123");
        domainBody.put("name", "example.com");
        domainBody.put("track_loads", true);
        server.enqueue(TestHelpers.jsonResponse(200, domainBody));

        DomainUpdateParams params = DomainUpdateParams.builder().setTrackLoads(true).build();
        var result = client.domains().update("dom_123", params);

        assertThat(result).isInstanceOf(Domain.class);
        assertThat(result.getString("id")).isEqualTo("dom_123");
        assertThat(result.getBoolean("track_loads")).isTrue();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("PATCH");
        assertThat(request.getPath()).isEqualTo("/v2/domains/dom_123");
        Map<String, Object> body = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(body.get("track_loads")).isEqualTo(true);
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> domain1 = new LinkedHashMap<>();
        domain1.put("object", "domain");
        domain1.put("id", "dom_123");
        domain1.put("name", "example.com");
        List<Map<String, Object>> data = new ArrayList<>();
        data.add(domain1);
        Map<String, Object> listBody = new LinkedHashMap<>();
        listBody.put("object", "list");
        listBody.put("data", data);
        server.enqueue(TestHelpers.jsonResponse(200, listBody));

        var result = client.domains().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.getData()).hasSize(1);
        assertThat(result.getData().get(0)).isInstanceOf(Domain.class);
        assertThat(result.getData().get(0).getString("id")).isEqualTo("dom_123");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/v2/domains");
    }

    @Test
    void deleteReturnsDomain() throws Exception {
        Map<String, Object> domainBody = new LinkedHashMap<>();
        domainBody.put("object", "domain");
        domainBody.put("id", "dom_123");
        domainBody.put("deleted", true);
        server.enqueue(TestHelpers.jsonResponse(200, domainBody));

        var result = client.domains().delete("dom_123");

        assertThat(result).isInstanceOf(Domain.class);
        assertThat(result.getString("id")).isEqualTo("dom_123");
        assertThat(result.getBoolean("deleted")).isTrue();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("DELETE");
        assertThat(request.getPath()).isEqualTo("/v2/domains/dom_123");
    }
}
