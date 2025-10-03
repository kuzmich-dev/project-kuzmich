package com.example.notification_service.service;

import com.example.core.dto.UserEventDTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private final JavaMailSender mailSender = mock(JavaMailSender.class);
    private final EmailService emailService = new EmailService(mailSender);

    @Test
    void shouldSendCreateNotification() {
        UserEventDTO dto = new UserEventDTO("user@mail.com", "CREATE");

        emailService.sendNotification(dto);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage message = captor.getValue();
        assertThat(message.getTo()).contains("user@mail.com");
        assertThat(message.getSubject()).contains("успешно создан");
        assertThat(message.getText()).contains("Операция: CREATE");
    }

    @Test
    void shouldSendDeleteNotification() {
        UserEventDTO dto = new UserEventDTO("user@mail.com", "DELETE");

        emailService.sendNotification(dto);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage message = captor.getValue();
        assertThat(message.getSubject()).contains("удалён");
        assertThat(message.getText()).contains("Операция: DELETE");
    }

}