package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.ContactCreateParams;
import com.emailit.params.ContactListParams;
import com.emailit.params.ContactUpdateParams;

public class ContactService extends AbstractService {

    public ContactService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(ContactCreateParams params) throws EmailitException {
        return request("POST", "/v2/contacts", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/contacts/%s", id), null);
    }

    public EmailitObject update(String id, ContactUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/contacts/%s", id), params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/contacts", null);
    }

    public Collection list(ContactListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/contacts", params != null ? params.toMap() : null);
    }

    public EmailitObject delete(String id) throws EmailitException {
        return request("DELETE", buildPath("/v2/contacts/%s", id), null);
    }
}
