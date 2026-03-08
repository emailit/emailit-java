package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class SubscriberListParams {

    private final Integer page;
    private final Integer limit;
    private final Boolean subscribed;

    private SubscriberListParams(Builder builder) {
        this.page = builder.page;
        this.limit = builder.limit;
        this.subscribed = builder.subscribed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (page != null) {
            map.put("page", page);
        }
        if (limit != null) {
            map.put("limit", limit);
        }
        if (subscribed != null) {
            map.put("subscribed", subscribed);
        }
        return map;
    }

    public static class Builder {
        private Integer page;
        private Integer limit;
        private Boolean subscribed;

        public Builder setPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder setSubscribed(Boolean subscribed) {
            this.subscribed = subscribed;
            return this;
        }

        public SubscriberListParams build() {
            return new SubscriberListParams(this);
        }
    }
}
