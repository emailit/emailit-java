package com.emailit;

import com.emailit.resources.*;
import com.emailit.util.Util;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ResourceTest {

    // EmailitObject tests
    @Test
    void emailitObjectGet() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", "em_123");
        values.put("status", "sent");
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.get("id")).isEqualTo("em_123");
        assertThat(obj.get("status")).isEqualTo("sent");
        assertThat(obj.get("missing")).isNull();
    }

    @Test
    void emailitObjectGetString() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", "test");
        values.put("count", 42);
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.getString("name")).isEqualTo("test");
        assertThat(obj.getString("count")).isEqualTo("42");
        assertThat(obj.getString("missing")).isNull();
    }

    @Test
    void emailitObjectGetBoolean() {
        Map<String, Object> values = new HashMap<>();
        values.put("active", true);
        values.put("disabled", "false");
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.getBoolean("active")).isTrue();
        assertThat(obj.getBoolean("disabled")).isFalse();
        assertThat(obj.getBoolean("missing")).isNull();
    }

    @Test
    void emailitObjectGetLong() {
        Map<String, Object> values = new HashMap<>();
        values.put("count", 100);
        values.put("strNum", "999");
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.getLong("count")).isEqualTo(100L);
        assertThat(obj.getLong("strNum")).isEqualTo(999L);
        assertThat(obj.getLong("missing")).isNull();
    }

    @Test
    void emailitObjectGetList() {
        List<Object> items = Arrays.asList("a", "b", "c");
        Map<String, Object> values = new HashMap<>();
        values.put("items", items);
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.getList("items")).containsExactly("a", "b", "c");
        assertThat(obj.getList("missing")).isNull();
    }

    @Test
    void emailitObjectGetMap() {
        Map<String, Object> nested = new HashMap<>();
        nested.put("key", "value");
        Map<String, Object> values = new HashMap<>();
        values.put("metadata", nested);
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.getMap("metadata")).containsEntry("key", "value");
        assertThat(obj.getMap("missing")).isNull();
    }

    @Test
    void emailitObjectHas() {
        Map<String, Object> values = new HashMap<>();
        values.put("present", "yes");
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.has("present")).isTrue();
        assertThat(obj.has("absent")).isFalse();
    }

    @Test
    void emailitObjectSet() {
        EmailitObject obj = new EmailitObject();
        obj.set("key", "value");
        assertThat(obj.get("key")).isEqualTo("value");
    }

    @Test
    void emailitObjectToMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);
        EmailitObject obj = new EmailitObject(values);

        Map<String, Object> map = obj.toMap();
        assertThat(map).containsEntry("a", 1).containsEntry("b", 2);
        assertThatThrownBy(() -> map.put("c", 3)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void emailitObjectToJson() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", "em_123");
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.toJson()).contains("em_123").contains("id");
    }

    @Test
    void emailitObjectToString() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", "em_123");
        EmailitObject obj = new EmailitObject(values);

        assertThat(obj.toString()).contains("em_123");
    }

    // ApiResource (Email) tests
    @Test
    void apiResourceEmailGetIdGetObjectGetObjectName() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", "em_123");
        values.put("object", "email");
        Email email = new Email(values);

        assertThat(email.getId()).isEqualTo("em_123");
        assertThat(email.getObject()).isEqualTo("email");
        assertThat(email.getObjectName()).isEqualTo("email");
    }

    // Collection tests
    @Test
    void collectionGetData() {
        EmailitObject item1 = new EmailitObject(Map.of("id", "1"));
        EmailitObject item2 = new EmailitObject(Map.of("id", "2"));
        List<EmailitObject> data = Arrays.asList(item1, item2);
        Map<String, Object> values = new HashMap<>();
        values.put("object", "list");

        Collection coll = new Collection(values, data);

        assertThat(coll.getData()).hasSize(2);
        assertThat(coll.getData()).containsExactly(item1, item2);
    }

    @Test
    void collectionSize() {
        List<EmailitObject> data = Arrays.asList(
                new EmailitObject(Map.of("id", "1")),
                new EmailitObject(Map.of("id", "2"))
        );
        Collection coll = new Collection(new HashMap<>(), data);

        assertThat(coll.size()).isEqualTo(2);
    }

    @Test
    void collectionHasMoreTrue() {
        Map<String, Object> values = new HashMap<>();
        values.put("next_page_url", "https://api.example.com/next");
        Collection coll = new Collection(values, Collections.emptyList());

        assertThat(coll.hasMore()).isTrue();
    }

    @Test
    void collectionHasMoreFalse() {
        Collection coll = new Collection(new HashMap<>(), Collections.emptyList());
        assertThat(coll.hasMore()).isFalse();
    }

    @Test
    void collectionIterator() {
        EmailitObject item1 = new EmailitObject(Map.of("id", "1"));
        EmailitObject item2 = new EmailitObject(Map.of("id", "2"));
        List<EmailitObject> data = Arrays.asList(item1, item2);
        Collection coll = new Collection(new HashMap<>(), data);

        List<EmailitObject> iterated = new ArrayList<>();
        for (EmailitObject obj : coll) {
            iterated.add(obj);
        }
        assertThat(iterated).containsExactly(item1, item2);
    }

    // Util.convertToEmailitObject tests
    @Test
    void convertToEmailitObjectReturnsEmailForObjectEmail() {
        Map<String, Object> data = new HashMap<>();
        data.put("object", "email");
        data.put("id", "em_123");

        EmailitObject result = Util.convertToEmailitObject(data);

        assertThat(result).isInstanceOf(Email.class);
        assertThat(((Email) result).getId()).isEqualTo("em_123");
    }

    @Test
    void convertToEmailitObjectReturnsCollectionForData() {
        Map<String, Object> data = new HashMap<>();
        data.put("object", "list");
        data.put("data", Arrays.asList(
                Map.of("object", "email", "id", "em_1"),
                Map.of("object", "email", "id", "em_2")
        ));

        EmailitObject result = Util.convertToEmailitObject(data);

        assertThat(result).isInstanceOf(Collection.class);
        Collection coll = (Collection) result;
        assertThat(coll.size()).isEqualTo(2);
    }

    @Test
    void convertToEmailitObjectReturnsEmailitObjectForUnknown() {
        Map<String, Object> data = new HashMap<>();
        data.put("object", "unknown_type");
        data.put("custom", "value");

        EmailitObject result = Util.convertToEmailitObject(data);

        assertThat(result).isInstanceOf(EmailitObject.class);
        assertThat(result.get("custom")).isEqualTo("value");
    }

    @Test
    void convertToEmailitObjectReturnsNullForNull() {
        assertThat(Util.convertToEmailitObject(null)).isNull();
    }

    // All 12 resource types from ObjectTypes
    @Test
    void all12ResourceTypesCanBeConstructedFromObjectTypes() {
        Map<String, Object> emailData = Map.of("object", "email", "id", "em_1");
        assertThat(Util.convertToEmailitObject(emailData)).isInstanceOf(Email.class);

        Map<String, Object> domainData = Map.of("object", "domain", "id", "dom_1");
        assertThat(Util.convertToEmailitObject(domainData)).isInstanceOf(Domain.class);

        Map<String, Object> apiKeyData = Map.of("object", "api_key", "id", "key_1");
        assertThat(Util.convertToEmailitObject(apiKeyData)).isInstanceOf(ApiKey.class);

        Map<String, Object> audienceData = Map.of("object", "audience", "id", "aud_1");
        assertThat(Util.convertToEmailitObject(audienceData)).isInstanceOf(Audience.class);

        Map<String, Object> subscriberData = Map.of("object", "subscriber", "id", "sub_1");
        assertThat(Util.convertToEmailitObject(subscriberData)).isInstanceOf(Subscriber.class);

        Map<String, Object> templateData = Map.of("object", "template", "id", "tpl_1");
        assertThat(Util.convertToEmailitObject(templateData)).isInstanceOf(Template.class);

        Map<String, Object> suppressionData = Map.of("object", "suppression", "id", "sup_1");
        assertThat(Util.convertToEmailitObject(suppressionData)).isInstanceOf(Suppression.class);

        Map<String, Object> evData = Map.of("object", "email_verification", "id", "ev_1");
        assertThat(Util.convertToEmailitObject(evData)).isInstanceOf(EmailVerification.class);

        Map<String, Object> evlData = Map.of("object", "email_verification_list", "id", "evl_1");
        assertThat(Util.convertToEmailitObject(evlData)).isInstanceOf(EmailVerificationList.class);

        Map<String, Object> webhookData = Map.of("object", "webhook", "id", "wh_1");
        assertThat(Util.convertToEmailitObject(webhookData)).isInstanceOf(Webhook.class);

        Map<String, Object> contactData = Map.of("object", "contact", "id", "con_1");
        assertThat(Util.convertToEmailitObject(contactData)).isInstanceOf(Contact.class);

        Map<String, Object> eventData = Map.of("object", "event", "id", "evt_1");
        assertThat(Util.convertToEmailitObject(eventData)).isInstanceOf(Event.class);
    }

    @Test
    void refreshFromReplacesValues() {
        Map<String, Object> initial = new HashMap<>();
        initial.put("id", "em_1");
        initial.put("status", "draft");
        EmailitObject obj = new EmailitObject(initial);

        Map<String, Object> updated = new HashMap<>();
        updated.put("id", "em_1");
        updated.put("status", "sent");
        obj.refreshFrom(updated);

        assertThat(obj.get("status")).isEqualTo("sent");
    }

    @Test
    void lastResponseGetSet() {
        EmailitObject obj = new EmailitObject();
        assertThat(obj.getLastResponse()).isNull();

        ApiResponse response = new ApiResponse(200, Collections.emptyMap(), "{}");
        obj.setLastResponse(response);
        assertThat(obj.getLastResponse()).isSameAs(response);
    }
}
