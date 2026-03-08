package com.emailit.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailVerificationListCreateParams {

    private final String name;
    private final List<String> emails;

    private EmailVerificationListCreateParams(Builder builder) {
        this.name = builder.name;
        this.emails = builder.emails;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put("name", name);
        }
        if (emails != null) {
            map.put("emails", emails);
        }
        return map;
    }

    public static class Builder {
        private String name;
        private List<String> emails;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmails(List<String> emails) {
            this.emails = emails;
            return this;
        }

        public EmailVerificationListCreateParams build() {
            return new EmailVerificationListCreateParams(this);
        }
    }
}
