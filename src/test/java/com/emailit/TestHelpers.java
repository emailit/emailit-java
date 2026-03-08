package com.emailit;

import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.io.IOException;
import java.util.Map;

public final class TestHelpers {

    private static final Gson GSON = new Gson();

    private TestHelpers() {
    }

    public static MockWebServer startMockServer() throws IOException {
        MockWebServer server = new MockWebServer();
        server.start();
        return server;
    }

    public static EmailitClient mockClient(MockWebServer server) {
        String baseUrl = server.url("").toString();
        // Remove trailing slash
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        return new EmailitClient("em_test_key", baseUrl, null);
    }

    public static MockResponse jsonResponse(int status, Map<String, Object> body) {
        return new MockResponse()
                .setResponseCode(status)
                .setHeader("Content-Type", "application/json")
                .setBody(GSON.toJson(body));
    }

    public static MockResponse jsonResponse(int status, Map<String, Object> body, Map<String, String> headers) {
        MockResponse response = jsonResponse(status, body);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                response.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return response;
    }

    public static MockResponse rawResponse(int status, String body) {
        return new MockResponse()
                .setResponseCode(status)
                .setBody(body);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseJson(String json) {
        return GSON.fromJson(json, Map.class);
    }
}
