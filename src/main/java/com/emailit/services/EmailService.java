package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.ApiResponse;
import com.emailit.exception.EmailitException;
import com.emailit.params.*;

public class EmailService extends AbstractService {

    public EmailService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject send(EmailSendParams params) throws EmailitException {
        return request("POST", "/v2/emails", params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/emails", null);
    }

    public Collection list(EmailListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/emails", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/emails/%s", id), null);
    }

    public EmailitObject getRaw(String id) throws EmailitException {
        return request("GET", buildPath("/v2/emails/%s/raw", id), null);
    }

    public Collection getAttachments(String id) throws EmailitException {
        return requestCollection("GET", buildPath("/v2/emails/%s/attachments", id), null);
    }

    public EmailitObject getBody(String id) throws EmailitException {
        return request("GET", buildPath("/v2/emails/%s/body", id), null);
    }

    public EmailitObject getMeta(String id) throws EmailitException {
        return request("GET", buildPath("/v2/emails/%s/meta", id), null);
    }

    public EmailitObject update(String id, EmailUpdateParams params) throws EmailitException {
        return request("POST", buildPath("/v2/emails/%s", id), params != null ? params.toMap() : null);
    }

    public EmailitObject cancel(String id) throws EmailitException {
        return request("POST", buildPath("/v2/emails/%s/cancel", id), null);
    }

    public EmailitObject retry(String id) throws EmailitException {
        return request("POST", buildPath("/v2/emails/%s/retry", id), null);
    }
}
