package com.crud.tasks.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class MailWithTemplate implements Mail {
    private final Mail mail;
    protected final Map<String, Object> templateVariables = new HashMap<>();

    public MailWithTemplate(final Mail mail) {
        this.mail = mail;
        setTemplateVariables();
    }

    public abstract String getTemplateHtml();
    protected abstract void setTemplateVariables();

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

    public Map<String, Object> getTemplateVariables() {
        return Collections.unmodifiableMap(templateVariables);
    }
}
