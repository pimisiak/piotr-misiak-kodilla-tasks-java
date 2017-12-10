package com.crud.tasks.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatedCardMailDecorator extends MailDecorator {
    private static final String TEMPLATE_HTML = "mail/created-trello-card-mail";
    private final Map<String, Object> templateModel = new HashMap<>();

    public CreatedCardMailDecorator(final Mail mail) {
        super(mail);
        setTemplateModel();
    }

    private void setTemplateModel() {
        final List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");
        templateModel.put("application_functionality", functionality);
        templateModel.put("mailTo", getMailTo());
        templateModel.put("message", getMessage());
        templateModel.put("tasks_url", "https://kodilla-tasks.herokuapp.com/");
        templateModel.put("button", "Visist website");
        templateModel.put("preview_message", "Trello Card created");
        templateModel.put("goodbay_message", "Have a nice day.");
        templateModel.put("show_button", false);
        templateModel.put("is_friend", true);
    }

    @Override
    public Map<String, Object> getTemplateModel() {
        return Collections.unmodifiableMap(templateModel);
    }

    @Override
    public String getTemplateHtml() {
        return TEMPLATE_HTML;
    }
}
