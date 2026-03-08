package com.emailit;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

public class ApiResponse {

    private static final Gson GSON = new Gson();
    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() {}.getType();

    private final int statusCode;
    private final Map<String, String> headers;
    private final String body;
    private final Map<String, Object> json;

    public ApiResponse(int statusCode, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers != null ? headers : Collections.emptyMap();
        this.body = body != null ? body : "";
        this.json = parseJson(this.body);
    }

    private static Map<String, Object> parseJson(String body) {
        if (body == null || body.isEmpty()) {
            return null;
        }
        try {
            Map<String, Object> parsed = GSON.fromJson(body, MAP_TYPE);
            return parsed;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Map<String, Object> getJson() {
        return json;
    }
}
