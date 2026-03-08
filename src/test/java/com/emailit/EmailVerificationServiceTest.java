package com.emailit;

import com.emailit.params.*;
import com.emailit.resources.*;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class EmailVerificationServiceTest {

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
    void createReturnsEmailVerification() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("object", "email_verification");
        body.put("id", "ev_123");
        body.put("email", "verify@example.com");
        body.put("status", "sent");
        server.enqueue(TestHelpers.jsonResponse(200, body));

        EmailVerificationCreateParams params = EmailVerificationCreateParams.builder()
                .setEmail("verify@example.com")
                .build();
        var result = client.emailVerifications().create(params);

        assertThat(result).isInstanceOf(EmailVerification.class);
        assertThat(((EmailVerification) result).getId()).isEqualTo("ev_123");
        assertThat(result.getString("email")).isEqualTo("verify@example.com");
        assertThat(result.getString("status")).isEqualTo("sent");

        RecordedRequest req = server.takeRequest();
        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getPath()).isEqualTo("/v2/email-verifications");
    }
}
