package com.emailit.exception;

import java.util.Map;

public class InvalidRequestException extends EmailitException {

    public InvalidRequestException(String message, int httpStatus, String httpBody,
                                   Map<String, Object> jsonBody, Map<String, String> httpHeaders) {
        super(message, httpStatus, httpBody, jsonBody, httpHeaders);
    }
}
