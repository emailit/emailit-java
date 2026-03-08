package com.emailit;

import com.emailit.exception.ApiConnectionException;
import com.emailit.exception.EmailitException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseEmailitClient {

    public static final String DEFAULT_API_BASE = "https://api.emailit.com";
    static final String SDK_VERSION = "2.0.1";

    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    private static final Gson GSON = new Gson();
    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() {}.getType();

    private final String apiKey;
    private final String apiBase;
    private final OkHttpClient httpClient;

    public BaseEmailitClient(String apiKey) {
        this(apiKey, null, null);
    }

    public BaseEmailitClient(String apiKey, String apiBase, OkHttpClient httpClient) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("api_key is required");
        }

        this.apiKey = apiKey;
        this.apiBase = apiBase != null ? stripTrailingSlash(apiBase) : DEFAULT_API_BASE;

        if (httpClient != null) {
            this.httpClient = httpClient;
        } else {
            this.httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiBase() {
        return apiBase;
    }

    public ApiResponse request(String method, String path, Map<String, Object> params) throws EmailitException {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("User-Agent", "emailit-java/" + SDK_VERSION);

        String url = apiBase + path;

        if ("GET".equalsIgnoreCase(method)) {
            if (params != null && !params.isEmpty()) {
                HttpUrl parsedUrl = HttpUrl.parse(url);
                if (parsedUrl == null) {
                    throw new ApiConnectionException("Invalid URL: " + url);
                }
                HttpUrl.Builder urlBuilder = parsedUrl.newBuilder();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() != null) {
                        urlBuilder.addQueryParameter(entry.getKey(), entry.getValue().toString());
                    }
                }
                url = urlBuilder.build().toString();
            }
            requestBuilder.url(url).get();
        } else {
            String jsonBody = params != null ? GSON.toJson(params) : "{}";
            RequestBody body = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);

            switch (method.toUpperCase()) {
                case "POST":
                    requestBuilder.url(url).post(body);
                    break;
                case "PATCH":
                    requestBuilder.url(url).patch(body);
                    break;
                case "DELETE":
                    requestBuilder.url(url).delete(body);
                    break;
                default:
                    requestBuilder.url(url).method(method.toUpperCase(), body);
                    break;
            }
        }

        Response response;
        try {
            response = httpClient.newCall(requestBuilder.build()).execute();
        } catch (IOException e) {
            throw new ApiConnectionException(
                    "Could not connect to the Emailit API: " + e.getMessage(), e);
        }

        String responseBody;
        try {
            responseBody = response.body() != null ? response.body().string() : "";
        } catch (IOException e) {
            throw new ApiConnectionException(
                    "Communication with Emailit API failed: " + e.getMessage(), e);
        }

        Map<String, String> responseHeaders = new HashMap<>();
        for (String name : response.headers().names()) {
            responseHeaders.put(name, response.header(name));
        }

        int statusCode = response.code();
        ApiResponse apiResponse = new ApiResponse(statusCode, responseHeaders, responseBody);

        if (statusCode >= 400) {
            handleErrorResponse(apiResponse);
        }

        return apiResponse;
    }

    private void handleErrorResponse(ApiResponse response) throws EmailitException {
        String message = extractErrorMessage(response);
        throw EmailitException.factory(
                message,
                response.getStatusCode(),
                response.getBody(),
                response.getJson(),
                response.getHeaders()
        );
    }

    @SuppressWarnings("unchecked")
    private String extractErrorMessage(ApiResponse response) {
        Map<String, Object> json = response.getJson();
        if (json != null) {
            Object error = json.get("error");
            if (error instanceof String) {
                String msg = (String) error;
                Object messageObj = json.get("message");
                if (messageObj != null) {
                    msg += ": " + messageObj;
                }
                return msg;
            }

            if (error instanceof Map) {
                Map<String, Object> errorMap = (Map<String, Object>) error;
                Object messageObj = errorMap.get("message");
                if (messageObj != null) {
                    return messageObj.toString();
                }
            }
        }

        return "API request failed with status " + response.getStatusCode();
    }

    private static String stripTrailingSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
