package com.emailit.params;

import java.util.*;

public class EmailUpdateParams {

    private final String scheduledAt;

    private EmailUpdateParams(Builder builder) {
        this.scheduledAt = builder.scheduledAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (scheduledAt != null) map.put("scheduled_at", scheduledAt);
        return map;
    }

    public static class Builder {
        private String scheduledAt;

        public Builder setScheduledAt(String scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public EmailUpdateParams build() {
            return new EmailUpdateParams(this);
        }
    }
}
