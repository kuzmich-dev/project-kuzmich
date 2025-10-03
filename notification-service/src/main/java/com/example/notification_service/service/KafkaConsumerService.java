package com.example.notification_service.service;

import com.example.core.dto.UserEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(UserEventDTO event) {
        log.info("Получено событие из Kafka: {}", event);

        try {
            emailService.sendNotification(event);
            log.info("Уведомление успешно отправлено на {}", event.getEmail());
        } catch (Exception e) {
            log.error("Ошибка при обработке события для {}: {}", event.getEmail(), e.getMessage(), e);
        }
    }

}