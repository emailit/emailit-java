package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class DomainUpdateParams {

    private final Boolean trackLoads;
    private final Boolean trackClicks;

    private DomainUpdateParams(Builder builder) {
        this.trackLoads = builder.trackLoads;
        this.trackClicks = builder.trackClicks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (trackLoads != null) {
            map.put("track_loads", trackLoads);
        }
        if (trackClicks != null) {
            map.put("track_clicks", trackClicks);
        }
        return map;
    }

    public static class Builder {
        private Boolean trackLoads;
        private Boolean trackClicks;

        public Builder setTrackLoads(Boolean trackLoads) {
            this.trackLoads = trackLoads;
            return this;
        }

        public Builder setTrackClicks(Boolean trackClicks) {
            this.trackClicks = trackClicks;
            return this;
        }

        public DomainUpdateParams build() {
            return new DomainUpdateParams(this);
        }
    }
}
