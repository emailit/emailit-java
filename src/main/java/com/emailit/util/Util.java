package com.emailit.util;

import com.emailit.ApiResource;
import com.emailit.Collection;
import com.emailit.EmailitObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class Util {

    private Util() {
    }

    @SuppressWarnings("unchecked")
    public static EmailitObject convertToEmailitObject(Map<String, Object> data) {
        if (data == null) {
            return null;
        }

        Object objectType = data.get("object");

        if ("list".equals(objectType) || data.containsKey("data")) {
            Object rawData = data.get("data");
            List<EmailitObject> items = new ArrayList<>();
            if (rawData instanceof List) {
                for (Object item : (List<Object>) rawData) {
                    if (item instanceof Map) {
                        EmailitObject converted = convertToEmailitObject((Map<String, Object>) item);
                        if (converted != null) {
                            items.add(converted);
                        }
                    }
                }
            }
            return new Collection(data, items);
        }

        if (objectType instanceof String) {
            Supplier<ApiResource> factory = ObjectTypes.mapping().get(objectType);
            if (factory != null) {
                ApiResource resource = factory.get();
                resource.refreshFrom(data);
                return resource;
            }
        }

        return new EmailitObject(data);
    }
}
