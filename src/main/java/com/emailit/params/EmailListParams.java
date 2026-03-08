package com.emailit.params;

import java.util.*;

public class EmailListParams {

    private final Integer page;
    private final Integer limit;

    private EmailListParams(Builder builder) {
        this.page = builder.page;
        this.limit = builder.limit;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (page != null) map.put("page", page);
        if (limit != null) map.put("limit", limit);
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

        public EmailListParams build() {
            return new EmailListParams(this);
        }
    }
}
