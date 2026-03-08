package com.emailit.exception;

import java.util.Map;

public class RateLimitException extends EmailitException {

    public RateLimitException(String message, int httpStatus, String httpBody,
                              Map<String, Object> jsonBody, Map<String, String> httpHeaders) {
        super(message, httpStatus, httpBody, jsonBody, httpHeaders);
    }
}
