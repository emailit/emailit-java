package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class AudienceUpdateParams {

    private final String name;

    private AudienceUpdateParams(Builder builder) {
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

        public AudienceUpdateParams build() {
            return new AudienceUpdateParams(this);
        }
    }
}
