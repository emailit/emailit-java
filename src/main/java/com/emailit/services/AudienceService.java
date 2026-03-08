package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.AudienceCreateParams;
import com.emailit.params.AudienceListParams;
import com.emailit.params.AudienceUpdateParams;

public class AudienceService extends AbstractService {

    public AudienceService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(AudienceCreateParams params) throws EmailitException {
        return request("POST", "/v2/audiences", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/audiences/%s", id), null);
    }

    public EmailitObject update(String id, AudienceUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/audiences/%s", id), params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/audiences", null);
    }

    public Collection list(AudienceListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/audiences", params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String id) throws EmailitException {
        return request("DELETE", buildPath("/v2/audiences/%s", id), null);
    }
}
