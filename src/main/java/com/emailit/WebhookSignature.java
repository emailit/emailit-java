package com.emailit;

import com.emailit.events.WebhookEvent;
import com.emailit.exception.EmailitException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public final class WebhookSignature {

    public static final String HEADER_SIGNATURE = "x-emailit-signature";
    public static final String HEADER_TIMESTAMP = "x-emailit-timestamp";
    public static final int DEFAULT_TOLERANCE = 300;

    private static final Gson GSON = new Gson();
    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() {}.getType();

    private WebhookSignature() {
    }

    public static WebhookEvent verify(String rawBody, String signature, String timestamp,
                                      String secret, Integer tolerance) throws EmailitException {
        if (tolerance != null) {
            long age = (System.currentTimeMillis() / 1000) - Long.parseLong(timestamp);
            if (age > tolerance) {
                throw new EmailitException(
                        "Webhook timestamp is too old. The request may be a replay attack.",
                        401, "", null, null);
            }
        }

        String computed = computeSignature(rawBody, timestamp, secret);

        if (!MessageDigest.isEqual(computed.getBytes(StandardCharsets.UTF_8),
                signature.getBytes(StandardCharsets.UTF_8))) {
            throw new EmailitException(
                    "Webhook signature verification failed.",
                    401, "", null, null);
        }

        Map<String, Object> payload = GSON.fromJson(rawBody, MAP_TYPE);
        if (payload == null) {
            throw new EmailitException(
                    "Invalid webhook payload: unable to decode JSON.",
                    400, "", null, null);
        }

        return WebhookEvent.constructFrom(payload);
    }

    public static WebhookEvent verify(String rawBody, String signature, String timestamp,
                                      String secret) throws EmailitException {
        return verify(rawBody, signature, timestamp, secret, DEFAULT_TOLERANCE);
    }

    public static String computeSignature(String rawBody, String timestamp, String secret) {
        try {
            String signedPayload = timestamp + "." + rawBody;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hash = mac.doFinal(signedPayload.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to compute HMAC-SHA256", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
