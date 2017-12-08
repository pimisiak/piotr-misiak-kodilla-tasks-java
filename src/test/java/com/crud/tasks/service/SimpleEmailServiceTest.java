package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
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
        // When
        simpleEmailService.send(mail);
        // Then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }
}