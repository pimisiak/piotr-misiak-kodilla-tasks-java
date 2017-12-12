package com.crud.tasks.domain;

import java.util.ArrayList;
import java.util.List;

public class CreatedCardMailWithTemplate extends MailWithTemplate {
    private static final String TEMPLATE_HTML = "mail/created-trello-card-mail";

    public CreatedCardMailWithTemplate(final Mail mail) {
        super(mail);
    }

    @Override
    protected void setTemplateVariables() {
        final List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");
        templateVariables.put("application_functionality", functionality);
        templateVariables.put("mailTo", getMailTo());
        templateVariables.put("message", getMessage());
        templateVariables.put("tasks_url", "https://kodilla-tasks.herokuapp.com/");
        templateVariables.put("button", "Visist website");
        templateVariables.put("preview_message", "Trello Card created");
        templateVariables.put("goodbay_message", "Have a nice day.");
        templateVariables.put("show_button", false);
        templateVariables.put("is_friend", true);
    }

    @Override
    public String getTemplateHtml() {
        return TEMPLATE_HTML;
    }
}
