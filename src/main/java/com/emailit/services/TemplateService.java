package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.TemplateCreateParams;
import com.emailit.params.TemplateListParams;
import com.emailit.params.TemplateUpdateParams;

public class TemplateService extends AbstractService {

    public TemplateService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(TemplateCreateParams params) throws EmailitException {
        return request("POST", "/v2/templates", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/templates/%s", id), null);
    }

    public EmailitObject update(String id, TemplateUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/templates/%s", id), params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/templates", null);
    }

    public Collection list(TemplateListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/templates", params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String id) throws EmailitException {
        return request("DELETE", buildPath("/v2/templates/%s", id), null);
    }

    public EmailitObject publish(String id) throws EmailitException {
        return request("POST", buildPath("/v2/templates/%s/publish", id), null);
    }
}
