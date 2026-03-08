package com.emailit.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebhookCreateParams {

    private final String name;
    private final String url;
    private final Boolean allEvents;
    private final Boolean enabled;
    private final List<String> events;

    private WebhookCreateParams(Builder builder) {
        this.name = builder.name;
        this.url = builder.url;
        this.allEvents = builder.allEvents;
        this.enabled = builder.enabled;
        this.events = builder.events;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put("name", name);
        }
        if (url != null) {
            map.put("url", url);
        }
        if (allEvents != null) {
            map.put("all_events", allEvents);
        }
        if (enabled != null) {
            map.put("enabled", enabled);
        }
        if (events != null) {
            map.put("events", events);
        }
        return map;
    }

    public static class Builder {
        private String name;
        private String url;
        private Boolean allEvents;
        private Boolean enabled;
        private List<String> events;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setAllEvents(Boolean allEvents) {
            this.allEvents = allEvents;
            return this;
        }

        public Builder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder setEvents(List<String> events) {
            this.events = events;
            return this;
        }

        public WebhookCreateParams build() {
            return new WebhookCreateParams(this);
        }
    }
}
