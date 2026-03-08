package com.emailit;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ApiResponseTest {

    @Test
    void jsonBodyIsParsed() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String body = "{\"id\":\"em_123\",\"object\":\"email\"}";

        ApiResponse response = new ApiResponse(200, headers, body);

        assertThat(response.getJson()).isNotNull();
        assertThat(response.getJson().get("id")).isEqualTo("em_123");
        assertThat(response.getJson().get("object")).isEqualTo("email");
    }

    @Test
    void nonJsonBodyReturnsNullJson() {
        Map<String, String> headers = new HashMap<>();
        String body = "plain text response";

        ApiResponse response = new ApiResponse(200, headers, body);

        assertThat(response.getJson()).isNull();
    }

    @Test
    void emptyBodyReturnsNullJson() {
        ApiResponse response = new ApiResponse(200, Collections.emptyMap(), "");

        assertThat(response.getJson()).isNull();
    }

    @Test
    void statusCodeHeadersBodyArePreserved() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Request-Id", "req_abc");
        headers.put("Content-Type", "application/json");
        String body = "{\"ok\":true}";

        ApiResponse response = new ApiResponse(201, headers, body);

        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getHeaders()).containsEntry("X-Request-Id", "req_abc");
        assertThat(response.getHeaders()).containsEntry("Content-Type", "application/json");
        assertThat(response.getBody()).isEqualTo("{\"ok\":true}");
    }

    @Test
    void nullHeadersTreatedAsEmpty() {
        ApiResponse response = new ApiResponse(200, null, "{}");
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    void nullBodyTreatedAsEmpty() {
        ApiResponse response = new ApiResponse(200, Collections.emptyMap(), null);
        assertThat(response.getBody()).isEmpty();
    }
}
