package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(final String message) {
        final Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://kodilla-tasks.herokuapp.com/");
        context.setVariable("button", "Visist website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_details", companyConfig);
        context.setVariable("preview_message", "Trello Card created");
        context.setVariable("goodbay_message", "Have a nice day.");
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
