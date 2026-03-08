package com.emailit;

import com.emailit.services.*;
import okhttp3.OkHttpClient;

public class EmailitClient extends BaseEmailitClient {

    private EmailService emails;
    private DomainService domains;
    private ApiKeyService apiKeys;
    private AudienceService audiences;
    private SubscriberService subscribers;
    private TemplateService templates;
    private SuppressionService suppressions;
    private EmailVerificationService emailVerifications;
    private EmailVerificationListService emailVerificationLists;
    private WebhookService webhooks;
    private ContactService contacts;
    private EventService events;

    public EmailitClient(String apiKey) {
        super(apiKey);
    }

    public EmailitClient(String apiKey, String apiBase, OkHttpClient httpClient) {
        super(apiKey, apiBase, httpClient);
    }

    public EmailService emails() {
        if (emails == null) {
            emails = new EmailService(this);
        }
        return emails;
    }

    public DomainService domains() {
        if (domains == null) {
            domains = new DomainService(this);
        }
        return domains;
    }

    public ApiKeyService apiKeys() {
        if (apiKeys == null) {
            apiKeys = new ApiKeyService(this);
        }
        return apiKeys;
    }

    public AudienceService audiences() {
        if (audiences == null) {
            audiences = new AudienceService(this);
        }
        return audiences;
    }

    public SubscriberService subscribers() {
        if (subscribers == null) {
            subscribers = new SubscriberService(this);
        }
        return subscribers;
    }

    public TemplateService templates() {
        if (templates == null) {
            templates = new TemplateService(this);
        }
        return templates;
    }

    public SuppressionService suppressions() {
        if (suppressions == null) {
            suppressions = new SuppressionService(this);
        }
        return suppressions;
    }

    public EmailVerificationService emailVerifications() {
        if (emailVerifications == null) {
            emailVerifications = new EmailVerificationService(this);
        }
        return emailVerifications;
    }

    public EmailVerificationListService emailVerificationLists() {
        if (emailVerificationLists == null) {
            emailVerificationLists = new EmailVerificationListService(this);
        }
        return emailVerificationLists;
    }

    public WebhookService webhooks() {
        if (webhooks == null) {
            webhooks = new WebhookService(this);
        }
        return webhooks;
    }

    public ContactService contacts() {
        if (contacts == null) {
            contacts = new ContactService(this);
        }
        return contacts;
    }

    public EventService events() {
        if (events == null) {
            events = new EventService(this);
        }
        return events;
    }
}
