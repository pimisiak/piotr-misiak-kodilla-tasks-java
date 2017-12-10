package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.MailDecorator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailCreatorService {
    @Autowired
    private CompanyConfig companyConfig;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public SimpleMailMessage createMailMessage(final Mail mail) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (StringUtils.isNotBlank(mail.getToCc())) {
            mailMessage.setCc(mail.getToCc());
        }
        return mailMessage;
    }

    public MimeMessagePreparator createMimeMessage(final MailDecorator mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(processMail(mail), true);
        };
    }

    private String processMail(final MailDecorator mail) {
        final Context context = new Context();
        context.setVariables(mail.getTemplateModel());
        context.setVariable("admin", adminConfig);
        context.setVariable("company", companyConfig);
        return templateEngine.process(mail.getTemplateHtml(), context);
    }
}
