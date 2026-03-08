package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class ApiKeyCreateParams {

    private final String name;
    private final String scope;
    private final String sendingDomainId;

    private ApiKeyCreateParams(Builder builder) {
        this.name = builder.name;
        this.scope = builder.scope;
        this.sendingDomainId = builder.sendingDomainId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put("name", name);
        }
        if (scope != null) {
            map.put("scope", scope);
        }
        if (sendingDomainId != null) {
            map.put("sending_domain_id", sendingDomainId);
        }
        return map;
    }

    public static class Builder {
        private String name;
        private String scope;
        private String sendingDomainId;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setScope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder setSendingDomainId(String sendingDomainId) {
            this.sendingDomainId = sendingDomainId;
            return this;
        }

        public ApiKeyCreateParams build() {
            return new ApiKeyCreateParams(this);
        }
    }
}
