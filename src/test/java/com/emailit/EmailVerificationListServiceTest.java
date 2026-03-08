package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class EmailVerificationListServiceTest {

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
    void createReturnsEmailVerificationList() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "email_verification_list");
        body.put("id", "evl_123");
        body.put("name", "My List");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        EmailVerificationListCreateParams params = EmailVerificationListCreateParams.builder()
                .setName("My List")
                .setEmails(Collections.singletonList("test@example.com"))
                .build();
        var result = client.emailVerificationLists().create(params);

        assertThat(result).isInstanceOf(EmailVerificationList.class);
        assertThat(((EmailVerificationList) result).getId()).isEqualTo("evl_123");
        assertThat(result.getString("name")).isEqualTo("My List");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/email-verification-lists");
    }

    @Test
    void listReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "email_verification_list");
        item.put("id", "evl_123");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.emailVerificationLists().list();

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(EmailVerificationList.class);

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/email-verification-lists");
    }

    @Test
    void getReturnsEmailVerificationList() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "email_verification_list");
        body.put("id", "evl_123");
        body.put("name", "My List");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.emailVerificationLists().get("evl_123");

        assertThat(result).isInstanceOf(EmailVerificationList.class);
        assertThat(((EmailVerificationList) result).getId()).isEqualTo("evl_123");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/email-verification-lists/evl_123");
    }

    @Test
    void resultsReturnsCollection() throws Exception {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("object", "email_verification");
        item.put("id", "ev_1");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "list");
        body.put("data", Collections.singletonList(item));
        server.enqueue(TestHelpers.jsonResponse(200, body));

        var result = client.emailVerificationLists().results("evl_123");

        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isInstanceOf(EmailVerification.class);

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/email-verification-lists/evl_123/results");
    }

    @Test
    void exportReturnsApiResponseWithBody() throws Exception {
        server.enqueue(TestHelpers.rawResponse(200, "binary-content"));

        var response = client.emailVerificationLists().export("evl_123");

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualTo("binary-content");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("GET");
        assertThat(req.getPath()).isEqualTo("/v2/email-verification-lists/evl_123/export");
    }
}
