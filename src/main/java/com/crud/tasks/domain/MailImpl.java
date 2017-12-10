package com.crud.tasks.domain;

import lombok.Getter;

@Getter
public class MailImpl implements Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String toCc;

    private MailImpl(final Builder builder) {
        this.mailTo = builder.mailTo;
        this.subject = builder.subject;
        this.message = builder.message;
        this.toCc = builder.toCc;
    }

    public static class Builder {
        private String mailTo;
        private String subject;
        private String message;
        private String toCc;

        public Builder mailTo(final String mailTo) {
            this.mailTo = mailTo;
            return this;
        }

        public Builder subject(final String subject) {
            this.subject = subject;
            return this;
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        public Builder toCc(final String toCc) {
            this.toCc = toCc;
            return this;
        }

        public MailImpl build() {
            return new MailImpl(this);
        }
    }
}