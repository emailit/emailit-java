package com.emailit.services;

import com.emailit.ApiResponse;
import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.util.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public abstract class AbstractService {

    private final BaseEmailitClient client;

    public AbstractService(BaseEmailitClient client) {
        this.client = client;
    }

    protected BaseEmailitClient getClient() {
        return client;
    }

    protected EmailitObject request(String method, String path, Map<String, Object> params) throws EmailitException {
        ApiResponse response = client.request(method, path, params);
        EmailitObject obj = Util.convertToEmailitObject(response.getJson());

        if (obj != null) {
            obj.setLastResponse(response);
        }

        return obj != null ? obj : new EmailitObject();
    }

    protected Collection requestCollection(String method, String path, Map<String, Object> params) throws EmailitException {
        ApiResponse response = client.request(method, path, params);
        EmailitObject obj = Util.convertToEmailitObject(response.getJson());

        if (obj instanceof Collection) {
            obj.setLastResponse(response);
            return (Collection) obj;
        }

        Collection collection = new Collection(response.getJson(), java.util.Collections.emptyList());
        collection.setLastResponse(response);
        return collection;
    }

    protected ApiResponse requestRaw(String method, String path, Map<String, Object> params) throws EmailitException {
        return client.request(method, path, params);
    }

    protected String buildPath(String pattern, String... args) {
        Object[] encoded = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                encoded[i] = URLEncoder.encode(args[i], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                encoded[i] = args[i];
            }
        }
        return String.format(pattern, encoded);
    }
}
