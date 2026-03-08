package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.SubscriberCreateParams;
import com.emailit.params.SubscriberListParams;
import com.emailit.params.SubscriberUpdateParams;

public class SubscriberService extends AbstractService {

    public SubscriberService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(String audienceId, SubscriberCreateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/audiences/%s/subscribers", audienceId), params != null ? params.toMap() : null);
    }

    public EmailitObject get(String audienceId, String subscriberId) throws EmailitException {
        return request("GET", buildPath("/v2/audiences/%s/subscribers/%s", audienceId, subscriberId), null);
    }

    public EmailitObject update(String audienceId, String subscriberId, SubscriberUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/audiences/%s/subscribers/%s", audienceId, subscriberId), params != null ? params.toMap() : null);
    }

    public Collection list(String audienceId) throws EmailitException {
        return requestCollection("GET", buildPath("/v2/audiences/%s/subscribers", audienceId), null);
    }

    public Collection list(String audienceId, SubscriberListParams params) throws EmailitException {
        return requestCollection("GET", buildPath("/v2/audiences/%s/subscribers", audienceId), params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String audienceId, String subscriberId) throws EmailitException {
        return request("DELETE", buildPath("/v2/audiences/%s/subscribers/%s", audienceId, subscriberId), null);
    }
}
