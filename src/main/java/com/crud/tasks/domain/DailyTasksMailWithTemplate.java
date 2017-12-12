package com.crud.tasks.domain;

public class DailyTasksMailWithTemplate extends MailWithTemplate {
    private static final String TEMPLATE_HTML = "mail/daily-tasks-mail";

    public DailyTasksMailWithTemplate(final Mail mail) {
        super(mail);
    }

    protected void setTemplateVariables() {
        templateVariables.put("mailTo", getMailTo());
        templateVariables.put("message", getMessage());
        templateVariables.put("preview_message", "Daily Tasks Mail");
        templateVariables.put("goodbay_message", "Have a nice day.");
        templateVariables.put("is_friend", false);
    }

    @Override
    public String getTemplateHtml() {
        return TEMPLATE_HTML;
    }
}
