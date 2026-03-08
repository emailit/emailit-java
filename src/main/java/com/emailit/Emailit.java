package com.emailit;

public final class Emailit {

    public static final String VERSION = "2.0.1";

    private Emailit() {
    }

    public static EmailitClient client(String apiKey) {
        return new EmailitClient(apiKey);
    }
}
