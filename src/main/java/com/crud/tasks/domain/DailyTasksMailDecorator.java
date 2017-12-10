package com.crud.tasks.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DailyTasksMailDecorator extends MailDecorator {
    private final String templateHtml = "mail/daily-tasks-mail";
    private final Map<String, Object> template = new HashMap<>();

    public DailyTasksMailDecorator(final Mail mail) {
        super(mail);
        setTemplateModel();
    }

    private void setTemplateModel() {
        template.put("mailTo", getMailTo());
        template.put("message", getMessage());
        template.put("preview_message", "Daily Tasks Mail");
        template.put("goodbay_message", "Have a nice day.");
        template.put("is_friend", false);
    }

    @Override
    public Map<String, Object> getTemplateModel() {
        return Collections.unmodifiableMap(template);
    }

    @Override
    public String getTemplateHtml() {
        return templateHtml;
    }
}
