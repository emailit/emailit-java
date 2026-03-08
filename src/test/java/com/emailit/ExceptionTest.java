package com.emailit;

import com.emailit.exception.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ExceptionTest {

    private MockWebServer server;

    @AfterEach
    void tearDown() throws IOException {
        if (server != null) {
            server.shutdown();
        }
    }

    @Test
    void factoryReturnsAuthenticationExceptionFor401() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "authentication_error");
        body.put("message", "Invalid API key");
        server.enqueue(TestHelpers.jsonResponse(401, body));

        assertThatThrownBy(() -> client.request("GET", "/v2/emails", null))
                .isInstanceOf(AuthenticationException.class)
                .satisfies(ex -> {
                    AuthenticationException authEx = (AuthenticationException) ex;
                    assertThat(authEx.getHttpStatus()).isEqualTo(401);
                    assertThat(authEx.getHttpBody()).contains("authentication_error");
                    assertThat(authEx.getJsonBody()).containsEntry("error", "authentication_error");
                });
    }

    @Test
    void factoryReturnsRateLimitExceptionFor429() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "rate_limit_error");
        server.enqueue(TestHelpers.jsonResponse(429, body));

        assertThatThrownBy(() -> client.request("GET", "/v2/emails", null))
                .isInstanceOf(RateLimitException.class)
                .satisfies(ex -> {
                    RateLimitException rateEx = (RateLimitException) ex;
                    assertThat(rateEx.getHttpStatus()).isEqualTo(429);
                });
    }

    @Test
    void factoryReturnsUnprocessableEntityExceptionFor422() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "validation_error");
        body.put("message", "Invalid email");
        server.enqueue(TestHelpers.jsonResponse(422, body));

        assertThatThrownBy(() -> client.request("POST", "/v2/emails", null))
                .isInstanceOf(UnprocessableEntityException.class)
                .satisfies(ex -> {
                    UnprocessableEntityException unprocEx = (UnprocessableEntityException) ex;
                    assertThat(unprocEx.getHttpStatus()).isEqualTo(422);
                });
    }

    @Test
    void factoryReturnsInvalidRequestExceptionFor400() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "invalid_request");
        server.enqueue(TestHelpers.jsonResponse(400, body));

        assertThatThrownBy(() -> client.request("GET", "/v2/emails", null))
                .isInstanceOf(InvalidRequestException.class)
                .satisfies(ex -> {
                    InvalidRequestException invEx = (InvalidRequestException) ex;
                    assertThat(invEx.getHttpStatus()).isEqualTo(400);
                });
    }

    @Test
    void factoryReturnsInvalidRequestExceptionFor404() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "not_found");
        server.enqueue(TestHelpers.jsonResponse(404, body));

        assertThatThrownBy(() -> client.request("GET", "/v2/emails/em_nonexistent", null))
                .isInstanceOf(InvalidRequestException.class)
                .satisfies(ex -> {
                    InvalidRequestException invEx = (InvalidRequestException) ex;
                    assertThat(invEx.getHttpStatus()).isEqualTo(404);
                });
    }

    @Test
    void factoryReturnsEmailitExceptionFor500() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "internal_server_error");
        server.enqueue(TestHelpers.jsonResponse(500, body));

        assertThatThrownBy(() -> client.request("GET", "/v2/emails", null))
                .isInstanceOf(EmailitException.class)
                .isNotInstanceOf(InvalidRequestException.class)
                .satisfies(ex -> {
                    EmailitException emailitEx = (EmailitException) ex;
                    assertThat(emailitEx.getHttpStatus()).isEqualTo(500);
                });
    }

    @Test
    void exceptionCarriesHttpStatusHttpBodyJsonBodyHttpHeaders() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "authentication_error");
        body.put("message", "Bad key");
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Request-Id", "req_abc");
        server.enqueue(TestHelpers.jsonResponse(401, body, headers));

        assertThatThrownBy(() -> client.request("GET", "/v2/emails", null))
                .isInstanceOf(AuthenticationException.class)
                .satisfies(ex -> {
                    AuthenticationException authEx = (AuthenticationException) ex;
                    assertThat(authEx.getHttpStatus()).isEqualTo(401);
                    assertThat(authEx.getHttpBody()).contains("authentication_error");
                    assertThat(authEx.getJsonBody()).isNotNull();
                    assertThat(authEx.getJsonBody().get("message")).isEqualTo("Bad key");
                    assertThat(authEx.getHttpHeaders()).containsEntry("X-Request-Id", "req_abc");
                });
    }

    @Test
    void apiConnectionExceptionWithMessage() {
        ApiConnectionException ex = new ApiConnectionException("Connection failed");

        assertThat(ex).hasMessage("Connection failed");
        assertThat(ex).isInstanceOf(EmailitException.class);
    }

    @Test
    void apiConnectionExceptionWithCause() {
        Throwable cause = new RuntimeException("Underlying error");
        ApiConnectionException ex = new ApiConnectionException("Connection failed", cause);

        assertThat(ex).hasMessage("Connection failed");
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
