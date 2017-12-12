package com.crud.tasks.service;

import com.crud.tasks.domain.DailyTasksMailWithTemplate;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.MailWithTemplate;
import com.crud.tasks.domain.MailImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {
    @InjectMocks
    private SimpleEmailService simpleEmailService;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MailCreatorService mailCreatorService;

    @Test
    public void shouldSendEmail() {
        // Given
        final Mail mail = new MailImpl.Builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("test@test.com");
        simpleMailMessage.setSubject("Test");
        simpleMailMessage.setText("Test message");
        when(mailCreatorService.createMailMessage(any(Mail.class))).thenReturn(simpleMailMessage);
        // When
        simpleEmailService.send(mail);
        // Then
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void shouldSendEmailWithTemplate() {
        final MailWithTemplate mailWithTemplate = new DailyTasksMailWithTemplate(new MailImpl.Builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .build());
        final MimeMessagePreparator mimeMessagePreparator = ((mimeMessage) -> {});
        when(mailCreatorService.createMimeMessage(any(MailWithTemplate.class))).thenReturn(mimeMessagePreparator);
        // When
        simpleEmailService.send(mailWithTemplate);
        // Then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }
}