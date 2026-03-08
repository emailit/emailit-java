package com.emailit;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmailitObject {

    private static final Gson GSON = new Gson();

    protected Map<String, Object> values;
    private ApiResponse lastResponse;

    public EmailitObject() {
        this.values = new LinkedHashMap<>();
    }

    public EmailitObject(Map<String, Object> values) {
        this.values = values != null ? new LinkedHashMap<>(values) : new LinkedHashMap<>();
    }

    public Object get(String key) {
        return values.get(key);
    }

    public String getString(String key) {
        Object val = values.get(key);
        return val != null ? val.toString() : null;
    }

    public Boolean getBoolean(String key) {
        Object val = values.get(key);
        if (val instanceof Boolean) {
            return (Boolean) val;
        }
        return val != null ? Boolean.parseBoolean(val.toString()) : null;
    }

    public Long getLong(String key) {
        Object val = values.get(key);
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        if (val instanceof String) {
            try {
                return Long.parseLong((String) val);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public Double getDouble(String key) {
        Object val = values.get(key);
        if (val instanceof Number) {
            return ((Number) val).doubleValue();
        }
        if (val instanceof String) {
            try {
                return Double.parseDouble((String) val);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Object> getList(String key) {
        Object val = values.get(key);
        if (val instanceof List) {
            return (List<Object>) val;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String key) {
        Object val = values.get(key);
        if (val instanceof Map) {
            return (Map<String, Object>) val;
        }
        return null;
    }

    public boolean has(String key) {
        return values.containsKey(key);
    }

    public void set(String key, Object value) {
        values.put(key, value);
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(values);
    }

    public String toJson() {
        return GSON.toJson(values);
    }

    public void refreshFrom(Map<String, Object> newValues) {
        this.values = new LinkedHashMap<>(newValues);
    }

    public ApiResponse getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(ApiResponse response) {
        this.lastResponse = response;
    }

    @Override
    public String toString() {
        return GSON.toJson(values);
    }
}
