package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class SubscriberUpdateParams {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final Map<String, Object> customFields;
    private final Boolean subscribed;

    private SubscriberUpdateParams(Builder builder) {
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.customFields = builder.customFields;
        this.subscribed = builder.subscribed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (email != null) {
            map.put("email", email);
        }
        if (firstName != null) {
            map.put("first_name", firstName);
        }
        if (lastName != null) {
            map.put("last_name", lastName);
        }
        if (customFields != null) {
            map.put("custom_fields", customFields);
        }
        if (subscribed != null) {
            map.put("subscribed", subscribed);
        }
        return map;
    }

    public static class Builder {
        private String email;
        private String firstName;
        private String lastName;
        private Map<String, Object> customFields;
        private Boolean subscribed;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setCustomFields(Map<String, Object> customFields) {
            this.customFields = customFields;
            return this;
        }

        public Builder setSubscribed(Boolean subscribed) {
            this.subscribed = subscribed;
            return this;
        }

        public SubscriberUpdateParams build() {
            return new SubscriberUpdateParams(this);
        }
    }
}
