package com.emailit.exception;

public class ApiConnectionException extends EmailitException {

    public ApiConnectionException(String message) {
        super(message);
    }

    public ApiConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
