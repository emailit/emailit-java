package com.emailit.services;

import com.emailit.ApiResponse;
import com.emailit.BaseEmailitClient;
import com.emailit.Collection;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.EmailVerificationListCreateParams;
import com.emailit.params.EmailVerificationListListParams;
import com.emailit.params.EmailVerificationListResultsParams;

public class EmailVerificationListService extends AbstractService {

    public EmailVerificationListService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(EmailVerificationListCreateParams params) throws EmailitException {
        return request("POST", "/v2/email-verification-lists", params != null ? params.toMap() : null);
    }

    public Collection list() throws EmailitException {
        return requestCollection("GET", "/v2/email-verification-lists", null);
    }

    public Collection list(EmailVerificationListListParams params) throws EmailitException {
        return requestCollection("GET", "/v2/email-verification-lists", params != null ? params.toMap() : null);
    }

    public EmailitObject get(String id) throws EmailitException {
        return request("GET", buildPath("/v2/email-verification-lists/%s", id), null);
    }

    public Collection results(String id) throws EmailitException {
        return requestCollection("GET", buildPath("/v2/email-verification-lists/%s/results", id), null);
    }

    public Collection results(String id, EmailVerificationListResultsParams params) throws EmailitException {
        return requestCollection("GET", buildPath("/v2/email-verification-lists/%s/results", id), params != null ? params.toMap() : null);
    }

    public ApiResponse export(String id) throws EmailitException {
        return requestRaw("GET", buildPath("/v2/email-verification-lists/%s/export", id), null);
    }
}
