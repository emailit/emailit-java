package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.EventListParams;

public class EventService extends AbstractService {

    public EventService(BaseEmailitClient client) {
        super(client);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/events", null);
    }

    public Collection list(EventListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/events", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/events/%s", id), null);
    }
}
