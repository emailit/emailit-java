# Emailit Java

[![Tests](https://img.shields.io/github/actions/workflow/status/emailit/emailit-java/tests.yml?label=tests&style=for-the-badge&labelColor=111827)](https://github.com/emailit/emailit-java/actions)
[![Maven Central](https://img.shields.io/maven-central/v/com.emailit/emailit-java?style=for-the-badge&labelColor=111827)](https://central.sonatype.com/artifact/com.emailit/emailit-java)
[![License](https://img.shields.io/github/license/emailit/emailit-java?style=for-the-badge&labelColor=111827)](https://github.com/emailit/emailit-java/blob/main/LICENSE)

The official Java SDK for the [Emailit](https://emailit.com) Email API.

## Requirements

- Java 11+

## Installation

**Maven**

```xml
<dependency>
    <groupId>com.emailit</groupId>
    <artifactId>emailit-java</artifactId>
    <version>LATEST</version>
</dependency>
```

**Gradle**

```groovy
implementation 'com.emailit:emailit-java:+'
```

## Getting Started

```java
import com.emailit.*;
import com.emailit.params.*;
import com.emailit.exception.*;

public class Main {
    public static void main(String[] args) {
        EmailitClient emailit = new EmailitClient("your_api_key");

        EmailSendParams params = EmailSendParams.builder()
                .setFrom("hello@yourdomain.com")
                .setTo(Arrays.asList("user@example.com"))
                .setSubject("Hello from Emailit")
                .setHtml("<h1>Welcome!</h1><p>Thanks for signing up.</p>")
                .build();

        try {
            EmailitObject email = emailit.emails().send(params);
            System.out.println(email.getString("id"));     // em_abc123...
            System.out.println(email.getString("status")); // pending
        } catch (EmailitException e) {
            e.printStackTrace();
        }
    }
}
```

All service methods return typed resource objects (`Email`, `Domain`, `Contact`, etc.) that extend `EmailitObject` with convenient accessors like `getString()`, `getLong()`, `getBoolean()`, `getMap()`, and `getList()`.

## Available Services

| Service | Method | Description |
|---------|--------|-------------|
| Emails | `emailit.emails()` | Send, list, get, cancel, retry emails |
| Domains | `emailit.domains()` | Create, verify, list, manage sending domains |
| API Keys | `emailit.apiKeys()` | Create, list, manage API keys |
| Audiences | `emailit.audiences()` | Create, list, manage audiences |
| Subscribers | `emailit.subscribers()` | Add, list, manage subscribers in audiences |
| Templates | `emailit.templates()` | Create, list, publish email templates |
| Suppressions | `emailit.suppressions()` | Create, list, manage suppressed addresses |
| Email Verifications | `emailit.emailVerifications()` | Verify email addresses |
| Email Verification Lists | `emailit.emailVerificationLists()` | Create, list, get results, export |
| Webhooks | `emailit.webhooks()` | Create, list, manage webhooks |
| Contacts | `emailit.contacts()` | Create, list, manage contacts |
| Events | `emailit.events()` | List and retrieve events |

## Usage

### Emails

#### Send an email

```java
EmailSendParams params = EmailSendParams.builder()
        .setFrom("hello@yourdomain.com")
        .setTo(Arrays.asList("user@example.com"))
        .setSubject("Hello from Emailit")
        .setHtml("<h1>Welcome!</h1>")
        .build();

EmailitObject email = emailit.emails().send(params);
System.out.println(email.getString("id"));
System.out.println(email.getString("status"));
```

#### Send with a template

```java
Map<String, Object> variables = new HashMap<>();
variables.put("name", "John Doe");
variables.put("company", "Acme Inc");

EmailSendParams params = EmailSendParams.builder()
        .setFrom("hello@yourdomain.com")
        .setTo("user@example.com")
        .setTemplate("welcome_email")
        .setVariables(variables)
        .build();

emailit.emails().send(params);
```

#### Send with attachments

```java
Map<String, Object> attachment = new HashMap<>();
attachment.put("filename", "invoice.pdf");
attachment.put("content", Base64.getEncoder().encodeToString(fileBytes));
attachment.put("content_type", "application/pdf");

EmailSendParams params = EmailSendParams.builder()
        .setFrom("invoices@yourdomain.com")
        .setTo("customer@example.com")
        .setSubject("Your Invoice #12345")
        .setHtml("<p>Please find your invoice attached.</p>")
        .setAttachments(Arrays.asList(attachment))
        .build();

emailit.emails().send(params);
```

#### Schedule an email

```java
EmailSendParams params = EmailSendParams.builder()
        .setFrom("reminders@yourdomain.com")
        .setTo("user@example.com")
        .setSubject("Appointment Reminder")
        .setHtml("<p>Your appointment is tomorrow at 2 PM.</p>")
        .setScheduledAt("2026-01-10T09:00:00Z")
        .build();

EmailitObject email = emailit.emails().send(params);
System.out.println(email.getString("status"));       // scheduled
System.out.println(email.getString("scheduled_at")); // 2026-01-10T09:00:00Z
```

#### List emails

```java
EmailListParams params = EmailListParams.builder()
        .setPage(1)
        .setLimit(10)
        .build();

Collection emails = emailit.emails().list(params);

for (EmailitObject email : emails) {
    System.out.println(email.getString("id") + " — " + email.getString("status"));
}

if (emails.hasMore()) {
    // fetch next page
}
```

#### Cancel / Retry

```java
emailit.emails().cancel("em_abc123");
emailit.emails().retry("em_abc123");
```

---

### Domains

```java
// Create a domain
DomainCreateParams createParams = DomainCreateParams.builder()
        .setName("example.com")
        .setTrackLoads(true)
        .setTrackClicks(true)
        .build();
EmailitObject domain = emailit.domains().create(createParams);
System.out.println(domain.getString("id"));

// Verify DNS
emailit.domains().verify("sd_123");

// List all domains
Collection domains = emailit.domains().list();

// Get a domain
EmailitObject domain = emailit.domains().get("sd_123");

// Update a domain
DomainUpdateParams updateParams = DomainUpdateParams.builder()
        .setTrackClicks(false)
        .build();
emailit.domains().update("sd_123", updateParams);

// Delete a domain
emailit.domains().delete("sd_123");
```

---

### API Keys

```java
// Create an API key
ApiKeyCreateParams params = ApiKeyCreateParams.builder()
        .setName("Production Key")
        .setScope("full")
        .build();
EmailitObject key = emailit.apiKeys().create(params);
System.out.println(key.getString("key")); // only available on create

// List all API keys
Collection keys = emailit.apiKeys().list();

// Get an API key
EmailitObject key = emailit.apiKeys().get("ak_123");

// Update an API key
ApiKeyUpdateParams updateParams = ApiKeyUpdateParams.builder()
        .setName("Renamed Key")
        .build();
emailit.apiKeys().update("ak_123", updateParams);

// Delete an API key
emailit.apiKeys().delete("ak_123");
```

---

### Audiences

```java
// Create an audience
AudienceCreateParams params = AudienceCreateParams.builder()
        .setName("Newsletter")
        .build();
EmailitObject audience = emailit.audiences().create(params);
System.out.println(audience.getString("id"));
System.out.println(audience.getString("token"));

// List audiences
Collection audiences = emailit.audiences().list();

// Get an audience
EmailitObject audience = emailit.audiences().get("aud_123");

// Update an audience
AudienceUpdateParams updateParams = AudienceUpdateParams.builder()
        .setName("Updated Newsletter")
        .build();
emailit.audiences().update("aud_123", updateParams);

// Delete an audience
emailit.audiences().delete("aud_123");
```

---

### Subscribers

Subscribers belong to an audience, so the audience ID is always the first argument.

```java
// Add a subscriber
SubscriberCreateParams params = SubscriberCreateParams.builder()
        .setEmail("user@example.com")
        .setFirstName("John")
        .setLastName("Doe")
        .build();
emailit.subscribers().create("aud_123", params);

// List subscribers in an audience
Collection subscribers = emailit.subscribers().list("aud_123");

// Get a subscriber
EmailitObject subscriber = emailit.subscribers().get("aud_123", "sub_456");

// Update a subscriber
SubscriberUpdateParams updateParams = SubscriberUpdateParams.builder()
        .setFirstName("Jane")
        .build();
emailit.subscribers().update("aud_123", "sub_456", updateParams);

// Delete a subscriber
emailit.subscribers().delete("aud_123", "sub_456");
```

---

### Templates

```java
// Create a template
TemplateCreateParams params = TemplateCreateParams.builder()
        .setName("Welcome")
        .setSubject("Welcome!")
        .setHtml("<h1>Hi {{name}}</h1>")
        .build();
emailit.templates().create(params);

// List templates
Collection templates = emailit.templates().list();

// Get a template
EmailitObject template = emailit.templates().get("tem_123");

// Update a template
TemplateUpdateParams updateParams = TemplateUpdateParams.builder()
        .setSubject("New Subject")
        .build();
emailit.templates().update("tem_123", updateParams);

// Publish a template
emailit.templates().publish("tem_123");

// Delete a template
emailit.templates().delete("tem_123");
```

---

### Suppressions

```java
// Create a suppression
SuppressionCreateParams params = SuppressionCreateParams.builder()
        .setEmail("spam@example.com")
        .setType("hard_bounce")
        .setReason("Manual suppression")
        .build();
emailit.suppressions().create(params);

// List suppressions
Collection suppressions = emailit.suppressions().list();

// Get a suppression
EmailitObject suppression = emailit.suppressions().get("sup_123");

// Update a suppression
SuppressionUpdateParams updateParams = SuppressionUpdateParams.builder()
        .setReason("Updated")
        .build();
emailit.suppressions().update("sup_123", updateParams);

// Delete a suppression
emailit.suppressions().delete("sup_123");
```

---

### Email Verifications

```java
EmailVerificationCreateParams params = EmailVerificationCreateParams.builder()
        .setEmail("test@example.com")
        .build();
EmailitObject result = emailit.emailVerifications().create(params);

System.out.println(result.getString("status")); // valid
System.out.println(result.getDouble("score"));  // 0.95
System.out.println(result.getString("risk"));   // low
```

---

### Email Verification Lists

```java
// Create a verification list
EmailVerificationListCreateParams params = EmailVerificationListCreateParams.builder()
        .setName("Marketing List Q1")
        .setEmails(Arrays.asList("user1@example.com", "user2@example.com", "user3@example.com"))
        .build();
EmailitObject list = emailit.emailVerificationLists().create(params);
System.out.println(list.getString("id"));     // evl_abc123...
System.out.println(list.getString("status")); // pending

// List all verification lists
Collection lists = emailit.emailVerificationLists().list();

// Get a verification list
EmailitObject list = emailit.emailVerificationLists().get("evl_abc123");

// Get verification results
Collection results = emailit.emailVerificationLists().results("evl_abc123");
for (EmailitObject result : results) {
    System.out.println(result.getString("email") + " — " + result.getString("result"));
}

// Export results as XLSX
ApiResponse response = emailit.emailVerificationLists().export("evl_abc123");
// response.getBody() contains the raw binary content
```

---

### Webhooks

```java
// Create a webhook
WebhookCreateParams params = WebhookCreateParams.builder()
        .setName("My Webhook")
        .setUrl("https://example.com/hook")
        .setAllEvents(true)
        .setEnabled(true)
        .build();
EmailitObject webhook = emailit.webhooks().create(params);
System.out.println(webhook.getString("id"));

// List webhooks
Collection webhooks = emailit.webhooks().list();

// Get a webhook
EmailitObject webhook = emailit.webhooks().get("wh_123");

// Update a webhook
WebhookUpdateParams updateParams = WebhookUpdateParams.builder()
        .setEnabled(false)
        .build();
emailit.webhooks().update("wh_123", updateParams);

// Delete a webhook
emailit.webhooks().delete("wh_123");
```

---

### Contacts

```java
// Create a contact
ContactCreateParams params = ContactCreateParams.builder()
        .setEmail("user@example.com")
        .setFirstName("John")
        .setLastName("Doe")
        .build();
EmailitObject contact = emailit.contacts().create(params);
System.out.println(contact.getString("id"));

// List contacts
Collection contacts = emailit.contacts().list();

// Get a contact
EmailitObject contact = emailit.contacts().get("con_123");

// Update a contact
ContactUpdateParams updateParams = ContactUpdateParams.builder()
        .setFirstName("Jane")
        .build();
emailit.contacts().update("con_123", updateParams);

// Delete a contact
emailit.contacts().delete("con_123");
```

---

### Events

```java
// List events
Collection events = emailit.events().list();

for (EmailitObject event : events) {
    System.out.println(event.getString("type"));
}

// Get an event
EmailitObject event = emailit.events().get("evt_123");
System.out.println(event.getString("type"));
```

## Webhook Events

The SDK provides typed event classes for all Emailit webhook event types under the `com.emailit.events` package, plus a `WebhookSignature` class for verifying webhook request signatures.

### Verifying Webhook Signatures

```java
import com.emailit.WebhookSignature;
import com.emailit.events.*;
import com.emailit.exception.EmailitException;

String rawBody = request.getBody();
String signature = request.getHeader("x-emailit-signature");
String timestamp = request.getHeader("x-emailit-timestamp");
String secret = "your_webhook_signing_secret";

try {
    WebhookEvent event = WebhookSignature.verify(rawBody, signature, timestamp, secret);

    // event is automatically typed based on the event type
    System.out.println(event.getType());    // e.g. "email.delivered"
    System.out.println(event.getEventId()); // e.g. "evt_abc123"

    // Access the event data
    Map<String, Object> data = event.getEventData();

    if (event instanceof EmailDelivered) {
        // Handle delivered email
    }
} catch (EmailitException e) {
    response.setStatus(401);
    System.err.println(e.getMessage());
}
```

You can disable replay protection by passing `null` tolerance, or set a custom tolerance in seconds:

```java
// Skip replay check
WebhookEvent event = WebhookSignature.verify(rawBody, signature, timestamp, secret, null);

// Custom 10-minute tolerance
WebhookEvent event = WebhookSignature.verify(rawBody, signature, timestamp, secret, 600);
```

### Available Event Types

**Emails:** `email.accepted`, `email.scheduled`, `email.delivered`, `email.bounced`, `email.attempted`, `email.failed`, `email.rejected`, `email.suppressed`, `email.received`, `email.complained`, `email.clicked`, `email.loaded`

**Domains:** `domain.created`, `domain.updated`, `domain.deleted`

**Audiences:** `audience.created`, `audience.updated`, `audience.deleted`

**Subscribers:** `subscriber.created`, `subscriber.updated`, `subscriber.deleted`

**Contacts:** `contact.created`, `contact.updated`, `contact.deleted`

**Templates:** `template.created`, `template.updated`, `template.deleted`

**Suppressions:** `suppression.created`, `suppression.updated`, `suppression.deleted`

**Email Verifications:** `email_verification.created`, `email_verification.updated`, `email_verification.deleted`

**Email Verification Lists:** `email_verification_list.created`, `email_verification_list.updated`, `email_verification_list.deleted`

Each event type has a corresponding class under `com.emailit.events` (e.g. `EmailDelivered`, `DomainCreated`). You can use `instanceof` checks for routing:

```java
if (event instanceof EmailDelivered) {
    handleDelivered(event);
} else if (event instanceof EmailBounced) {
    handleBounce(event);
} else if (event instanceof ContactCreated) {
    handleNewContact(event);
} else {
    System.out.println("Unhandled: " + event.getType());
}
```

## Error Handling

The SDK throws typed exceptions for API errors:

```java
import com.emailit.exception.*;

try {
    emailit.emails().send(params);
} catch (AuthenticationException e) {
    // Invalid API key (401)
} catch (InvalidRequestException e) {
    // Bad request or not found (400, 404)
} catch (RateLimitException e) {
    // Too many requests (429)
} catch (UnprocessableEntityException e) {
    // Validation failed (422)
} catch (ApiConnectionException e) {
    // Network error
} catch (EmailitException e) {
    // Any other API error
    System.out.println(e.getHttpStatus());
    System.out.println(e.getHttpBody());
    System.out.println(e.getJsonBody());
}
```

## License

MIT -- see [LICENSE](LICENSE) for details.
