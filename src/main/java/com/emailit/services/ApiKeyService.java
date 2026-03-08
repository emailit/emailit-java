package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.ApiKeyCreateParams;
import com.emailit.params.ApiKeyListParams;
import com.emailit.params.ApiKeyUpdateParams;

public class ApiKeyService extends AbstractService {

    public ApiKeyService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(ApiKeyCreateParams params) throws EmailitException {
        return request("POST", "/v2/api-keys", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/api-keys/%s", id), null);
    }

    public EmailitObject update(String id, ApiKeyUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/api-keys/%s", id), params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/api-keys", null);
    }

    public Collection list(ApiKeyListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/api-keys", params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String id) throws EmailitException {
        return request("DELETE", buildPath("/v2/api-keys/%s", id), null);
    }
}
