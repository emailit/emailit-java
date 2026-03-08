package com.emailit.params;

import java.util.*;

public class EmailSendParams {

    private final String from;
    private final Object to;
    private final String subject;
    private final String html;
    private final String text;
    private final String template;
    private final Map<String, Object> variables;
    private final Object cc;
    private final Object bcc;
    private final String replyTo;
    private final List<Map<String, Object>> attachments;
    private final List<String> tags;
    private final Map<String, Object> metadata;
    private final Map<String, Object> tracking;
    private final String scheduledAt;
    private final Map<String, String> headers;

    private EmailSendParams(Builder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.subject = builder.subject;
        this.html = builder.html;
        this.text = builder.text;
        this.template = builder.template;
        this.variables = builder.variables;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
        this.replyTo = builder.replyTo;
        this.attachments = builder.attachments;
        this.tags = builder.tags;
        this.metadata = builder.metadata;
        this.tracking = builder.tracking;
        this.scheduledAt = builder.scheduledAt;
        this.headers = builder.headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (from != null) map.put("from", from);
        if (to != null) map.put("to", to);
        if (subject != null) map.put("subject", subject);
        if (html != null) map.put("html", html);
        if (text != null) map.put("text", text);
        if (template != null) map.put("template", template);
        if (variables != null) map.put("variables", variables);
        if (cc != null) map.put("cc", cc);
        if (bcc != null) map.put("bcc", bcc);
        if (replyTo != null) map.put("reply_to", replyTo);
        if (attachments != null) map.put("attachments", attachments);
        if (tags != null) map.put("tags", tags);
        if (metadata != null) map.put("metadata", metadata);
        if (tracking != null) map.put("tracking", tracking);
        if (scheduledAt != null) map.put("scheduled_at", scheduledAt);
        if (headers != null) map.put("headers", headers);
        return map;
    }

    public static class Builder {
        private String from;
        private Object to;
        private String subject;
        private String html;
        private String text;
        private String template;
        private Map<String, Object> variables;
        private Object cc;
        private Object bcc;
        private String replyTo;
        private List<Map<String, Object>> attachments;
        private List<String> tags;
        private Map<String, Object> metadata;
        private Map<String, Object> tracking;
        private String scheduledAt;
        private Map<String, String> headers;

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setTo(Object to) {
            this.to = to;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setHtml(String html) {
            this.html = html;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTemplate(String template) {
            this.template = template;
            return this;
        }

        public Builder setVariables(Map<String, Object> variables) {
            this.variables = variables;
            return this;
        }

        public Builder setCc(Object cc) {
            this.cc = cc;
            return this;
        }

        public Builder setBcc(Object bcc) {
            this.bcc = bcc;
            return this;
        }

        public Builder setReplyTo(String replyTo) {
            this.replyTo = replyTo;
            return this;
        }

        public Builder setAttachments(List<Map<String, Object>> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Builder setTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder setMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setTracking(Map<String, Object> tracking) {
            this.tracking = tracking;
            return this;
        }

        public Builder setScheduledAt(String scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public EmailSendParams build() {
            return new EmailSendParams(this);
        }
    }
}
