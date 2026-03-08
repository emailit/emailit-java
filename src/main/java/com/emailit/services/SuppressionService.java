package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.SuppressionCreateParams;
import com.emailit.params.SuppressionListParams;
import com.emailit.params.SuppressionUpdateParams;

public class SuppressionService extends AbstractService {

    public SuppressionService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(SuppressionCreateParams params) throws EmailitException {
        return request("POST", "/v2/suppressions", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/suppressions/%s", id), null);
    }

    public EmailitObject update(String id, SuppressionUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/suppressions/%s", id), params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/suppressions", null);
    }

    public Collection list(SuppressionListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/suppressions", params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String id) throws EmailitException {
        return request("DELETE", buildPath("/v2/suppressions/%s", id), null);
    }
}
