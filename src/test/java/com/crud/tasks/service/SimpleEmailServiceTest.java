package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {
    @InjectMocks
    private SimpleEmailService simpleEmailService;
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        // Given
        final Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (StringUtils.isNotBlank(mail.getToCc())) {
            mailMessage.setCc(mail.getToCc());
        }
        // When
        simpleEmailService.send(mail);
        // Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }
}