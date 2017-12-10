package com.crud.tasks.domain;

import java.util.Map;

public abstract class MailDecorator implements Mail {
    private final Mail mail;

    public MailDecorator(final Mail mail) {
        this.mail = mail;
    }

    public abstract Map<String, Object> getTemplateModel();
    public abstract String getTemplateHtml();

    @Override
    public String getMailTo() {
        return mail.getMailTo();
    }

    @Override
    public String getSubject() {
        return mail.getSubject();
    }

    @Override
    public String getMessage() {
        return mail.getMessage();
    }

    @Override
    public String getToCc() {
        return mail.getToCc();
    }
}
