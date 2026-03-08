package com.emailit.params;

import java.util.HashMap;
import java.util.Map;

public class TemplateUpdateParams {

    private final String name;
    private final String alias;
    private final String from;
    private final String subject;
    private final String replyTo;
    private final String html;
    private final String text;
    private final String editor;

    private TemplateUpdateParams(Builder builder) {
        this.name = builder.name;
        this.alias = builder.alias;
        this.from = builder.from;
        this.subject = builder.subject;
        this.replyTo = builder.replyTo;
        this.html = builder.html;
        this.text = builder.text;
        this.editor = builder.editor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put("name", name);
        }
        if (alias != null) {
            map.put("alias", alias);
        }
        if (from != null) {
            map.put("from", from);
        }
        if (subject != null) {
            map.put("subject", subject);
        }
        if (replyTo != null) {
            map.put("reply_to", replyTo);
        }
        if (html != null) {
            map.put("html", html);
        }
        if (text != null) {
            map.put("text", text);
        }
        if (editor != null) {
            map.put("editor", editor);
        }
        return map;
    }

    public static class Builder {
        private String name;
        private String alias;
        private String from;
        private String subject;
        private String replyTo;
        private String html;
        private String text;
        private String editor;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setReplyTo(String replyTo) {
            this.replyTo = replyTo;
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

        public Builder setEditor(String editor) {
            this.editor = editor;
            return this;
        }

        public TemplateUpdateParams build() {
            return new TemplateUpdateParams(this);
        }
    }
}
