package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class AudienceListParams {

    private final Integer page;
    private final Integer limit;

    private AudienceListParams(Builder builder) {
        this.page = builder.page;
        this.limit = builder.limit;
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
        return map;
    }

    public static class Builder {
        private Integer page;
        private Integer limit;

        public Builder setPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public AudienceListParams build() {
            return new AudienceListParams(this);
        }
    }
}
