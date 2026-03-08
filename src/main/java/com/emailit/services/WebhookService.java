package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.WebhookCreateParams;
import com.emailit.params.WebhookListParams;
import com.emailit.params.WebhookUpdateParams;

public class WebhookService extends AbstractService {

    public WebhookService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(WebhookCreateParams params) throws EmailitException {
        return request("POST", "/v2/webhooks", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/webhooks/%s", id), null);
    }

    public EmailitObject update(String id, WebhookUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/webhooks/%s", id), params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/webhooks", null);
    }

    public Collection list(WebhookListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/webhooks", params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String id) throws EmailitException {
        return request("DELETE", buildPath("/v2/webhooks/%s", id), null);
    }
}
