package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class EmailVerificationCreateParams {

    private final String email;

    private EmailVerificationCreateParams(Builder builder) {
        this.email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (email != null) {
            map.put("email", email);
        }
        return map;
    }

    public static class Builder {
        private String email;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public EmailVerificationCreateParams build() {
            return new EmailVerificationCreateParams(this);
        }
    }
}
