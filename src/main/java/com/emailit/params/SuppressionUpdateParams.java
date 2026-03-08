package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class SuppressionUpdateParams {

    private final String type;
    private final String reason;
    private final String keepUntil;

    private SuppressionUpdateParams(Builder builder) {
        this.type = builder.type;
        this.reason = builder.reason;
        this.keepUntil = builder.keepUntil;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (type != null) {
            map.put("type", type);
        }
        if (reason != null) {
            map.put("reason", reason);
        }
        if (keepUntil != null) {
            map.put("keep_until", keepUntil);
        }
        return map;
    }

    public static class Builder {
        private String type;
        private String reason;
        private String keepUntil;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setKeepUntil(String keepUntil) {
            this.keepUntil = keepUntil;
            return this;
        }

        public SuppressionUpdateParams build() {
            return new SuppressionUpdateParams(this);
        }
    }
}
