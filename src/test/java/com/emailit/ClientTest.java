package com.emailit;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class ClientTest {

    private MockWebServer server;

    @AfterEach
    void tearDown() throws IOException {
        if (server != null) {
            server.shutdown();
        }
    }

    @Test
    void versionIsCorrect() {
        assertThat(Emailit.VERSION).isEqualTo("2.0.1");
    }

    @Test
    void clientFactoryReturnsEmailitClient() {
        EmailitClient client = Emailit.client("sk_test_key");
        assertThat(client).isNotNull();
        assertThat(client).isInstanceOf(EmailitClient.class);
    }

    @Test
    void newEmailitClientWorks() {
        EmailitClient client = new EmailitClient("sk_test_key");
        assertThat(client).isNotNull();
    }

    @Test
    void nullApiKeyThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> new EmailitClient(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("api_key is required");
    }

    @Test
    void emptyApiKeyThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> new EmailitClient(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("api_key is required");
    }

    @Test
    void getApiKeyReturnsTheKey() {
        EmailitClient client = new EmailitClient("sk_my_secret_key");
        assertThat(client.getApiKey()).isEqualTo("sk_my_secret_key");
    }

    @Test
    void getApiBaseReturnsDefaultWhenNotSpecified() {
        EmailitClient client = new EmailitClient("sk_test_key");
        assertThat(client.getApiBase()).isEqualTo(BaseEmailitClient.DEFAULT_API_BASE);
    }

    @Test
    void serviceAccessorsReturnSameInstance() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        assertThat(client.emails()).isSameAs(client.emails());
        assertThat(client.domains()).isSameAs(client.domains());
        assertThat(client.apiKeys()).isSameAs(client.apiKeys());
        assertThat(client.audiences()).isSameAs(client.audiences());
        assertThat(client.subscribers()).isSameAs(client.subscribers());
        assertThat(client.templates()).isSameAs(client.templates());
        assertThat(client.suppressions()).isSameAs(client.suppressions());
        assertThat(client.emailVerifications()).isSameAs(client.emailVerifications());
        assertThat(client.emailVerificationLists()).isSameAs(client.emailVerificationLists());
        assertThat(client.webhooks()).isSameAs(client.webhooks());
        assertThat(client.contacts()).isSameAs(client.contacts());
        assertThat(client.events()).isSameAs(client.events());
    }

    @Test
    void allServiceAccessorsReturnNonNull() throws IOException {
        server = TestHelpers.startMockServer();
        EmailitClient client = TestHelpers.mockClient(server);

        assertThat(client.emails()).isNotNull();
        assertThat(client.domains()).isNotNull();
        assertThat(client.apiKeys()).isNotNull();
        assertThat(client.audiences()).isNotNull();
        assertThat(client.subscribers()).isNotNull();
        assertThat(client.templates()).isNotNull();
        assertThat(client.suppressions()).isNotNull();
        assertThat(client.emailVerifications()).isNotNull();
        assertThat(client.emailVerificationLists()).isNotNull();
        assertThat(client.webhooks()).isNotNull();
        assertThat(client.contacts()).isNotNull();
        assertThat(client.events()).isNotNull();
    }
}
