package com.emailit.exception;

import java.util.Map;

public class EmailitException extends Exception {

    private final int httpStatus;
    private final String httpBody;
    private final Map<String, Object> jsonBody;
    private final Map<String, String> httpHeaders;

    public EmailitException(String message) {
        this(message, 0, "", null, null, null);
    }

    public EmailitException(String message, Throwable cause) {
        this(message, 0, "", null, null, cause);
    }

    public EmailitException(String message, int httpStatus, String httpBody,
                            Map<String, Object> jsonBody, Map<String, String> httpHeaders) {
        this(message, httpStatus, httpBody, jsonBody, httpHeaders, null);
    }

    public EmailitException(String message, int httpStatus, String httpBody,
                            Map<String, Object> jsonBody, Map<String, String> httpHeaders,
                            Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.httpBody = httpBody != null ? httpBody : "";
        this.jsonBody = jsonBody;
        this.httpHeaders = httpHeaders;
    }

    public static EmailitException factory(String message, int httpStatus, String httpBody,
                                           Map<String, Object> jsonBody, Map<String, String> httpHeaders) {
        switch (httpStatus) {
            case 401:
                return new AuthenticationException(message, httpStatus, httpBody, jsonBody, httpHeaders);
            case 429:
                return new RateLimitException(message, httpStatus, httpBody, jsonBody, httpHeaders);
            case 422:
                return new UnprocessableEntityException(message, httpStatus, httpBody, jsonBody, httpHeaders);
            case 400:
            case 404:
                return new InvalidRequestException(message, httpStatus, httpBody, jsonBody, httpHeaders);
            default:
                return new EmailitException(message, httpStatus, httpBody, jsonBody, httpHeaders);
        }
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getHttpBody() {
        return httpBody;
    }

    public Map<String, Object> getJsonBody() {
        return jsonBody;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }
}
