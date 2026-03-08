package com.emailit;

import java.util.Map;

public abstract class ApiResource extends EmailitObject {

    public ApiResource() {
        super();
    }

    public ApiResource(Map<String, Object> values) {
        super(values);
    }

    public abstract String getObjectName();

    public String getId() {
        return getString("id");
    }

    public String getObject() {
        return getString("object");
    }
}
