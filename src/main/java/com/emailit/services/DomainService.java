package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.DomainCreateParams;
import com.emailit.params.DomainListParams;
import com.emailit.params.DomainUpdateParams;

public class DomainService extends AbstractService {

    public DomainService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(DomainCreateParams params) throws EmailitException {
        return request("POST", "/v2/domains", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/domains/%s", id), null);
    }

    public EmailitObject verify(String id) throws EmailitException {
        return request("POST", buildPath("/v2/domains/%s/verify", id), null);
    }

    public EmailitObject update(String id, DomainUpdateParams params) throws EmailitException {
        return request("PATCH", buildPath("/v2/domains/%s", id), params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/domains", null);
    }

    public Collection list(DomainListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/domains", params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String id) throws EmailitException {
        return request("DELETE", buildPath("/v2/domains/%s", id), null);
    }
}
