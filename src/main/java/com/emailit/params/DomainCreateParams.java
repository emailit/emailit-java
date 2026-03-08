package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class DomainCreateParams {

    private final String name;
    private final Boolean trackLoads;
    private final Boolean trackClicks;

    private DomainCreateParams(Builder builder) {
        this.name = builder.name;
        this.trackLoads = builder.trackLoads;
        this.trackClicks = builder.trackClicks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put("name", name);
        }
        if (trackLoads != null) {
            map.put("track_loads", trackLoads);
        }
        if (trackClicks != null) {
            map.put("track_clicks", trackClicks);
        }
        return map;
    }

    public static class Builder {
        private String name;
        private Boolean trackLoads;
        private Boolean trackClicks;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTrackLoads(Boolean trackLoads) {
            this.trackLoads = trackLoads;
            return this;
        }

        public Builder setTrackClicks(Boolean trackClicks) {
            this.trackClicks = trackClicks;
            return this;
        }

        public DomainCreateParams build() {
            return new DomainCreateParams(this);
        }
    }
}
