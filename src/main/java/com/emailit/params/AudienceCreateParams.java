package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class AudienceCreateParams {

    private final String name;

    private AudienceCreateParams(Builder builder) {
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put("name", name);
        }
        return map;
    }

    public static class Builder {
        private String name;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public AudienceCreateParams build() {
            return new AudienceCreateParams(this);
        }
    }
}
