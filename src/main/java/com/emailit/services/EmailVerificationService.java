package com.emailit.services;

import com.emailit.BaseEmailitClient;
import com.emailit.EmailitObject;
import com.emailit.exception.EmailitException;
import com.emailit.params.EmailVerificationCreateParams;

public class EmailVerificationService extends AbstractService {

    public EmailVerificationService(BaseEmailitClient client) {
        super(client);
    }

    public EmailitObject create(EmailVerificationCreateParams params) throws EmailitException {
        return request("POST", "/v2/email-verifications", params != null ? params.toMap() : null);
    }
}
