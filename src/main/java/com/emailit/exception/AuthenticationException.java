package com.emailit.exception;

import java.util.Map;

public class AuthenticationException extends EmailitException {

    public AuthenticationException(String message, int httpStatus, String httpBody,
                                   Map<String, Object> jsonBody, Map<String, String> httpHeaders) {
        super(message, httpStatus, httpBody, jsonBody, httpHeaders);
    }
}
