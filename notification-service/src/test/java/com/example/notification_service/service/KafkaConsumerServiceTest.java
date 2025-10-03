package com.example.notification_service.service;

import com.example.core.dto.UserEventDTO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class KafkaConsumerServiceTest {

    private final EmailService emailService = mock(EmailService.class);
    private final KafkaConsumerService consumerService = new KafkaConsumerService(emailService);

    @Test
    void shouldConsumeEventAndSendNotification() {
        UserEventDTO userEventDTOdto = new UserEventDTO("user@mail.com", "CREATE");

        consumerService.consume(userEventDTOdto);

        verify(emailService, times(1)).sendNotification(userEventDTOdto);
    }

    @Test
    void shouldHandleExceptionGracefully() {
        UserEventDTO userEventDTO = new UserEventDTO("user@mail.com", "CREATE");
        doThrow(new RuntimeException("Mail error")).when(emailService).sendNotification(userEventDTO);

        consumerService.consume(userEventDTO);

        verify(emailService, times(1)).sendNotification(userEventDTO);
    }

}