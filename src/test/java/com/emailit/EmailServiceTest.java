package com.emailit;

import com.emailit.exception.*;
import com.emailit.params.*;
import com.emailit.resources.Email;
import okhttp3.mockwebserver.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailServiceTest {

    private MockWebServer server;
    private EmailitClient client;

    @BeforeEach
    void setUp() throws Exception {
        server = TestHelpers.startMockServer();
        client = TestHelpers.mockClient(server);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (server != null) {
            try {
                server.shutdown();
            } catch (Exception ignored) {
                // Server may already be shut down (e.g. connection failure test)
            }
        }
    }

    // ──────────────────────────────────────────────────
    // send()
    // ──────────────────────────────────────────────────

    @Test
    void send_returnsEmailResource() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc123");
        responseBody.put("status", "pending");
        responseBody.put("from", "hello@example.com");
        responseBody.put("to", Collections.singletonList("user@example.com"));
        responseBody.put("subject", "Hello World");

        server.enqueue(TestHelpers.jsonResponse(201, responseBody));

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("hello@example.com")
                .setTo(Collections.singletonList("user@example.com"))
                .setSubject("Hello World")
                .setHtml("<h1>Welcome!</h1>")
                .build();

        var email = client.emails().send(params);

        assertThat(email).isInstanceOf(Email.class);
        assertThat(email.getString("id")).isEqualTo("em_abc123");
        assertThat(email.getString("status")).isEqualTo("pending");
        assertThat(email.getString("from")).isEqualTo("hello@example.com");
        assertThat(email.get("to")).isEqualTo(Collections.singletonList("user@example.com"));
        assertThat(email.getString("subject")).isEqualTo("Hello World");
        assertThat(email.get("id")).isEqualTo("em_abc123");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/emails");

        Map<String, Object> sentBody = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(sentBody.get("from")).isEqualTo("hello@example.com");
        assertThat(sentBody.get("html")).isEqualTo("<h1>Welcome!</h1>");
    }

    @Test
    void send_withTemplateAndVariables() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_tpl1");
        responseBody.put("status", "pending");

        server.enqueue(TestHelpers.jsonResponse(201, responseBody));

        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("name", "John");
        variables.put("company", "Acme");

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("hello@example.com")
                .setTo("user@example.com")
                .setTemplate("welcome_email")
                .setVariables(variables)
                .build();

        var email = client.emails().send(params);

        assertThat(email).isInstanceOf(Email.class);
        assertThat(email.getString("id")).isEqualTo("em_tpl1");

        RecordedRequest request = server.takeRequest();
        Map<String, Object> sentBody = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(sentBody.get("template")).isEqualTo("welcome_email");
        assertThat(sentBody.get("variables")).isEqualTo(variables);
    }

    @Test
    void send_withAttachments() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_att1");
        responseBody.put("status", "pending");

        server.enqueue(TestHelpers.jsonResponse(201, responseBody));

        Map<String, Object> attachment = new LinkedHashMap<>();
        attachment.put("filename", "invoice.pdf");
        attachment.put("content", "base64data");
        attachment.put("content_type", "application/pdf");
        List<Map<String, Object>> attachments = Collections.singletonList(attachment);

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("invoices@example.com")
                .setTo("customer@example.com")
                .setSubject("Invoice")
                .setHtml("<p>See attached.</p>")
                .setAttachments(attachments)
                .build();

        client.emails().send(params);

        RecordedRequest request = server.takeRequest();
        Map<String, Object> sentBody = TestHelpers.parseJson(request.getBody().readUtf8());
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> sentAttachments = (List<Map<String, Object>>) sentBody.get("attachments");
        assertThat(sentAttachments).hasSize(1);
        assertThat(sentAttachments.get(0).get("filename")).isEqualTo("invoice.pdf");
    }

    @Test
    void send_withScheduledAt() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_sch1");
        responseBody.put("status", "scheduled");

        server.enqueue(TestHelpers.jsonResponse(201, responseBody));

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("reminders@example.com")
                .setTo("user@example.com")
                .setSubject("Reminder")
                .setHtml("<p>Tomorrow at 2 PM.</p>")
                .setScheduledAt("2026-01-10T09:00:00Z")
                .build();

        var email = client.emails().send(params);

        assertThat(email.getString("status")).isEqualTo("scheduled");
    }

    @Test
    void send_withTrackingOptions() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_trk1");
        responseBody.put("status", "pending");

        server.enqueue(TestHelpers.jsonResponse(201, responseBody));

        Map<String, Object> tracking = new LinkedHashMap<>();
        tracking.put("loads", true);
        tracking.put("clicks", true);

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("hello@example.com")
                .setTo(Collections.singletonList("user@example.com"))
                .setSubject("Tracked")
                .setHtml("<p>Hi</p>")
                .setTracking(tracking)
                .build();

        client.emails().send(params);

        RecordedRequest request = server.takeRequest();
        Map<String, Object> sentBody = TestHelpers.parseJson(request.getBody().readUtf8());
        assertThat(sentBody.get("tracking")).isEqualTo(tracking);
    }

    // ──────────────────────────────────────────────────
    // list()
    // ──────────────────────────────────────────────────

    @Test
    void list_returnsCollectionOfEmails() throws Exception {
        Map<String, Object> em1 = new LinkedHashMap<>();
        em1.put("object", "email");
        em1.put("id", "em_1");
        em1.put("status", "delivered");
        Map<String, Object> em2 = new LinkedHashMap<>();
        em2.put("object", "email");
        em2.put("id", "em_2");
        em2.put("status", "pending");

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("data", Arrays.asList(em1, em2));
        responseBody.put("next_page_url", "/v2/emails?page=2&limit=10");
        responseBody.put("previous_page_url", null);

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        EmailListParams params = EmailListParams.builder()
                .setPage(1)
                .setLimit(10)
                .build();

        var list = client.emails().list(params);

        assertThat(list).isInstanceOf(Collection.class);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.hasMore()).isTrue();
        assertThat(list.getData().get(0)).isInstanceOf(Email.class);
        assertThat(list.getData().get(0).getString("id")).isEqualTo("em_1");
        assertThat(list.getData().get(1).getString("id")).isEqualTo("em_2");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).contains("page=1");
        assertThat(request.getPath()).contains("limit=10");
    }

    @Test
    void list_withoutParamsSendsNoQuery() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("data", Collections.emptyList());
        responseBody.put("next_page_url", null);
        responseBody.put("previous_page_url", null);

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var list = client.emails().list();

        assertThat(list).isInstanceOf(Collection.class);
        assertThat(list.size()).isEqualTo(0);
        assertThat(list.hasMore()).isFalse();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).doesNotContain("?");
    }

    @Test
    void list_withParamsSendsQueryString() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("data", Collections.emptyList());
        responseBody.put("next_page_url", null);
        responseBody.put("previous_page_url", null);

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        EmailListParams params = EmailListParams.builder()
                .setPage(1)
                .setLimit(10)
                .build();

        client.emails().list(params);

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).contains("page=1");
        assertThat(request.getPath()).contains("limit=10");
    }

    // ──────────────────────────────────────────────────
    // get()
    // ──────────────────────────────────────────────────

    @Test
    void get_returnsEmail() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc123");
        responseBody.put("type", "outbound");
        responseBody.put("status", "delivered");
        responseBody.put("from", "sender@example.com");
        responseBody.put("to", "recipient@example.com");
        responseBody.put("subject", "Hello World");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var email = client.emails().get("em_abc123");

        assertThat(email).isInstanceOf(Email.class);
        assertThat(email.getString("id")).isEqualTo("em_abc123");
        assertThat(email.getString("type")).isEqualTo("outbound");
        assertThat(email.getString("status")).isEqualTo("delivered");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123");
    }

    // ──────────────────────────────────────────────────
    // getRaw()
    // ──────────────────────────────────────────────────

    @Test
    void getRaw_returnsObjectWithRawProperty() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc123");
        responseBody.put("raw", "From: sender@example.com\r\nTo: recipient@example.com\r\nSubject: Hello\r\n\r\nBody");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var email = client.emails().getRaw("em_abc123");

        assertThat(email).isInstanceOf(Email.class);
        assertThat(email.getString("raw")).contains("From: sender@example.com");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123/raw");
    }

    // ──────────────────────────────────────────────────
    // getAttachments()
    // ──────────────────────────────────────────────────

    @Test
    void getAttachments_returnsCollection() throws Exception {
        Map<String, Object> attachment = new LinkedHashMap<>();
        attachment.put("filename", "doc.pdf");
        attachment.put("content_type", "application/pdf");
        attachment.put("size", 12345);

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "list");
        responseBody.put("data", Collections.singletonList(attachment));

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var attachments = client.emails().getAttachments("em_abc123");

        assertThat(attachments).isInstanceOf(Collection.class);
        assertThat(attachments.size()).isEqualTo(1);
        assertThat(attachments.getData().get(0).getString("filename")).isEqualTo("doc.pdf");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123/attachments");
    }

    // ──────────────────────────────────────────────────
    // getBody()
    // ──────────────────────────────────────────────────

    @Test
    void getBody_returnsEmailitObjectWithTextAndHtml() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("text", "Plain text content");
        responseBody.put("html", "<html><body><h1>Welcome!</h1></body></html>");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var body = client.emails().getBody("em_abc123");

        assertThat(body).isInstanceOf(EmailitObject.class);
        assertThat(body.getString("text")).isEqualTo("Plain text content");
        assertThat(body.getString("html")).contains("<h1>Welcome!</h1>");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123/body");
    }

    // ──────────────────────────────────────────────────
    // getMeta()
    // ──────────────────────────────────────────────────

    @Test
    void getMeta_returnsEmailWithHeadersAndAttachments() throws Exception {
        Map<String, Object> headers = new LinkedHashMap<>();
        headers.put("From", "sender@example.com");
        headers.put("Subject", "Hello");
        Map<String, Object> att = new LinkedHashMap<>();
        att.put("filename", "doc.pdf");
        att.put("size", 12345);

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc123");
        responseBody.put("headers", headers);
        responseBody.put("attachments", Collections.singletonList(att));

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var meta = client.emails().getMeta("em_abc123");

        assertThat(meta).isInstanceOf(Email.class);
        assertThat(meta.getMap("headers").get("From")).isEqualTo("sender@example.com");
        assertThat(meta.getList("attachments")).hasSize(1);

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123/meta");
    }

    // ──────────────────────────────────────────────────
    // update()
    // ──────────────────────────────────────────────────

    @Test
    void update_returnsEmail() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc123");
        responseBody.put("status", "scheduled");
        responseBody.put("scheduled_at", "2026-01-10T15:00:00.000Z");
        responseBody.put("message", "Email schedule has been updated successfully");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        EmailUpdateParams params = EmailUpdateParams.builder()
                .setScheduledAt("2026-01-10T15:00:00Z")
                .build();

        var email = client.emails().update("em_abc123", params);

        assertThat(email).isInstanceOf(Email.class);
        assertThat(email.getString("status")).isEqualTo("scheduled");
        assertThat(email.getString("message")).contains("updated successfully");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123");
    }

    // ──────────────────────────────────────────────────
    // cancel()
    // ──────────────────────────────────────────────────

    @Test
    void cancel_returnsEmail() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc123");
        responseBody.put("status", "canceled");
        responseBody.put("message", "Email has been canceled successfully");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var email = client.emails().cancel("em_abc123");

        assertThat(email).isInstanceOf(Email.class);
        assertThat(email.getString("status")).isEqualTo("canceled");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123/cancel");
    }

    // ──────────────────────────────────────────────────
    // retry()
    // ──────────────────────────────────────────────────

    @Test
    void retry_returnsEmailWithOriginalId() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_new789");
        responseBody.put("original_id", "em_abc123");
        responseBody.put("status", "pending");
        responseBody.put("message", "Email has been queued for retry");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var email = client.emails().retry("em_abc123");

        assertThat(email).isInstanceOf(Email.class);
        assertThat(email.getString("original_id")).isEqualTo("em_abc123");
        assertThat(email.getString("id")).isEqualTo("em_new789");
        assertThat(email.getString("status")).isEqualTo("pending");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_abc123/retry");
    }

    // ──────────────────────────────────────────────────
    // Resource features
    // ──────────────────────────────────────────────────

    @Test
    void emailSupportsToMap() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc");
        responseBody.put("status", "sent");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var email = client.emails().get("em_abc");

        Map<String, Object> map = email.toMap();
        assertThat(map.get("object")).isEqualTo("email");
        assertThat(map.get("id")).isEqualTo("em_abc");
        assertThat(map.get("status")).isEqualTo("sent");
    }

    @Test
    void emailIsJsonSerializable() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc");
        responseBody.put("status", "sent");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var email = client.emails().get("em_abc");

        assertThat(email.toJson()).isEqualTo("{\"object\":\"email\",\"id\":\"em_abc\",\"status\":\"sent\"}");
    }

    @Test
    void getLastResponse_returnsApiResponse() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_abc");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var email = client.emails().get("em_abc");

        assertThat(email.getLastResponse()).isInstanceOf(ApiResponse.class);
        assertThat(email.getLastResponse().getStatusCode()).isEqualTo(200);
    }

    @Test
    void collectionIsIterable() throws Exception {
        Map<String, Object> em1 = new LinkedHashMap<>();
        em1.put("object", "email");
        em1.put("id", "em_1");
        Map<String, Object> em2 = new LinkedHashMap<>();
        em2.put("object", "email");
        em2.put("id", "em_2");

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("data", Arrays.asList(em1, em2));
        responseBody.put("next_page_url", null);
        responseBody.put("previous_page_url", null);

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        var list = client.emails().list();
        var ids = new ArrayList<String>();

        for (EmailitObject item : list) {
            ids.add(item.getString("id"));
        }

        assertThat(ids).containsExactly("em_1", "em_2");
    }

    // ──────────────────────────────────────────────────
    // URL encoding
    // ──────────────────────────────────────────────────

    @Test
    void urlEncodingInPath_idWithSpaces() throws Exception {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("object", "email");
        responseBody.put("id", "em_with spaces");

        server.enqueue(TestHelpers.jsonResponse(200, responseBody));

        client.emails().get("em_with spaces");

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/v2/emails/em_with+spaces");
    }

    // ──────────────────────────────────────────────────
    // Error handling
    // ──────────────────────────────────────────────────

    @Test
    void status401_throwsAuthenticationException() throws Exception {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", "Unauthorized");
        errorBody.put("message", "Invalid API key");

        server.enqueue(TestHelpers.jsonResponse(401, errorBody));

        assertThatThrownBy(() -> client.emails().list())
                .isInstanceOf(AuthenticationException.class)
                .hasMessageContaining("Unauthorized")
                .hasMessageContaining("Invalid API key");
    }

    @Test
    void status400_throwsInvalidRequestException() throws Exception {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", "Validation failed");
        errorBody.put("message", "Missing required field: from");

        server.enqueue(TestHelpers.jsonResponse(400, errorBody));

        EmailSendParams params = EmailSendParams.builder()
                .setTo(Collections.singletonList("user@example.com"))
                .build();

        assertThatThrownBy(() -> client.emails().send(params))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Validation failed")
                .hasMessageContaining("Missing required field: from");
    }

    @Test
    void status404_throwsInvalidRequestException() throws Exception {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", "Email not found");
        errorBody.put("message", "Email with ID 'em_fake' not found");

        server.enqueue(TestHelpers.jsonResponse(404, errorBody));

        assertThatThrownBy(() -> client.emails().get("em_fake"))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Email not found");
    }

    @Test
    void status422_throwsUnprocessableEntityException() throws Exception {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", "Cannot cancel email");
        errorBody.put("message", "Current status: 'sent'");

        server.enqueue(TestHelpers.jsonResponse(422, errorBody));

        assertThatThrownBy(() -> client.emails().cancel("em_sent"))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessageContaining("Cannot cancel email");
    }

    @Test
    void status429_throwsRateLimitException() throws Exception {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", "Rate limit exceeded");
        errorBody.put("message", "Too many requests");

        server.enqueue(TestHelpers.jsonResponse(429, errorBody));

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("a@b.com")
                .setTo("c@d.com")
                .setSubject("Hi")
                .setHtml("Hi")
                .build();

        assertThatThrownBy(() -> client.emails().send(params))
                .isInstanceOf(RateLimitException.class)
                .hasMessageContaining("Rate limit exceeded")
                .hasMessageContaining("Too many requests");
    }

    @Test
    void errorExceptionCarriesFullResponseData() throws Exception {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", "Email not found");
        errorBody.put("message", "Not found in workspace");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("X-Request-Id", "req_test123");

        server.enqueue(TestHelpers.jsonResponse(404, errorBody, headers));

        InvalidRequestException e = assertThrows(InvalidRequestException.class,
                () -> client.emails().get("em_nonexistent"));

        assertThat(e.getHttpStatus()).isEqualTo(404);
        assertThat(e.getJsonBody()).isEqualTo(errorBody);
        assertThat(e.getHttpBody()).contains("Email not found");
        assertThat(e.getHttpHeaders()).containsKey("X-Request-Id");
    }

    @Test
    void nestedErrorFormatIsExtracted() throws Exception {
        Map<String, Object> errorObj = new LinkedHashMap<>();
        errorObj.put("type", "validation_error");
        errorObj.put("message", "The recipient email address is invalid");
        errorObj.put("param", "to");
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", errorObj);

        server.enqueue(TestHelpers.jsonResponse(400, errorBody));

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("a@b.com")
                .setTo("invalid")
                .setSubject("Hi")
                .setHtml("Hi")
                .build();

        InvalidRequestException e = assertThrows(InvalidRequestException.class,
                () -> client.emails().send(params));

        assertThat(e.getMessage()).isEqualTo("The recipient email address is invalid");
    }

    @Test
    void nonJsonErrorBodyUsesFallbackMessage() throws Exception {
        server.enqueue(TestHelpers.rawResponse(502, "Bad Gateway"));

        EmailitException e = assertThrows(EmailitException.class,
                () -> client.emails().list());

        assertThat(e.getMessage()).isEqualTo("API request failed with status 502");
        assertThat(e.getHttpBody()).isEqualTo("Bad Gateway");
    }

    @Test
    void connectionFailure_throwsApiConnectionException() throws Exception {
        server.shutdown();

        assertThatThrownBy(() -> client.emails().list())
                .isInstanceOf(ApiConnectionException.class)
                .hasMessageContaining("Could not connect to the Emailit API");
    }
}
