package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
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
        final Mail mail = Mail.builder("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        final String toCc = mail.getToCc();
        if (toCc != null) {
            mailMessage.setCc(toCc);
        }
        // When
        simpleEmailService.send(mail);
        // Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }
}