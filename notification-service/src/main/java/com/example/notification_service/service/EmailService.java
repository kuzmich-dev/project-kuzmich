package com.example.notification_service.service;

import com.example.core.dto.UserEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendNotification(UserEventDTO event) {
        String subject = switch (event.getOperation()) {
            case "CREATE" -> "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";
            case "DELETE" -> "Здравствуйте! Ваш аккаунт был удалён.";
            default -> "Уведомление";
        };

        String body = "Операция: " + event.getOperation();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getEmail());
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Письмо отправлено на {} с темой '{}'", event.getEmail(), subject);
        } catch (Exception e) {
            log.error("Ошибка при отправке письма на {}: {}", event.getEmail(), e.getMessage(), e);
            throw e;
        }
    }

}